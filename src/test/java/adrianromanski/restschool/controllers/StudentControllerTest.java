package adrianromanski.restschool.controllers;

import adrianromanski.restschool.controllers.exception_handler.RestResponseEntityExceptionHandler;
import adrianromanski.restschool.controllers.person.StudentController;
import adrianromanski.restschool.domain.base_entity.person.Student;
import adrianromanski.restschool.domain.base_entity.person.enums.Gender;
import adrianromanski.restschool.exceptions.ResourceNotFoundException;
import adrianromanski.restschool.model.base_entity.person.StudentDTO;
import adrianromanski.restschool.services.person.student.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


import static adrianromanski.restschool.domain.base_entity.person.enums.Gender.FEMALE;
import static adrianromanski.restschool.domain.base_entity.person.enums.Gender.MALE;
import static org.hamcrest.Matchers.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class StudentControllerTest  extends AbstractRestControllerTest {

    public static final Long ID = 1L;
    public static final String FIRST_NAME = "Aaron";
    public static final String LAST_NAME = "Smith";
    public static final String STUDENTS = "/students/";

    @Mock
    StudentService studentService;

    @InjectMocks
    StudentController studentController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(studentController)
                .setControllerAdvice(RestResponseEntityExceptionHandler.class)
                .build();
    }

    StudentDTO createStudentDTO(Long id, String firstName, String lastName, Gender gender) {
        StudentDTO studentDTO = StudentDTO.builder().firstName(firstName).lastName(lastName).gender(gender).build();
        studentDTO.setId(id);
        return studentDTO;
    }

    StudentDTO createAaronSmith() {
        return createStudentDTO(ID,FIRST_NAME, LAST_NAME, MALE);
    }

    StudentDTO createJessiePinkman() {
        return createStudentDTO(2L, "Jessie", "Pinkman", MALE);
    }

    StudentDTO createJessicaParker() {
        return createStudentDTO(3L, "Jessica", "Parker", FEMALE);
    }

    @DisplayName("[GET], [Happy Path], [Method] = getAllStudents, [Expected] = List containing 3 Students")
    @Test
    void getAllStudents() throws Exception {
        List<StudentDTO> students = Arrays.asList(createAaronSmith(), createJessiePinkman(), createJessicaParker());

        when(studentService.getAllStudents()).thenReturn(students);

        mockMvc.perform(get(STUDENTS)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.students", hasSize(3)));
    }

    @DisplayName("[GET], [Happy Path], [Method] = getAllFemaleStudents, [Expected] = List containing 1 Student")
    @Test
    void getAllFemaleStudents() throws Exception {
        List<StudentDTO> students = Collections.singletonList(createJessicaParker());

        when(studentService.getAllFemaleStudents()).thenReturn(students);

        mockMvc.perform(get(STUDENTS + "female")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.students", hasSize(1)));
    }

    @DisplayName("[GET], [Happy Path], [Method] = getAllMaleStudents, [Expected] = List containing 2 Student")
    @Test
    void getAllMaleStudents() throws Exception {
        List<StudentDTO> students = Arrays.asList(createAaronSmith(), createJessiePinkman());

        when(studentService.getAllMaleStudents()).thenReturn(students);

        mockMvc.perform(get(STUDENTS + "male")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.students", hasSize(2)));
    }

    @DisplayName("[GET], [Happy Path], [Method] = getStudentByFirstAndLastName, [Expected] = StudentDTO with matching fields")
    @Test
    void getStudentByFirstAndLastName() throws Exception {
        StudentDTO studentDTO = createAaronSmith();

        when(studentService.getStudentByFirstAndLastName(anyString(), anyString())).thenReturn(studentDTO);

        mockMvc.perform(get(STUDENTS + FIRST_NAME + "/" + LAST_NAME)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(studentDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo(FIRST_NAME)))
                .andExpect(jsonPath("$.lastName", equalTo(LAST_NAME)))
                .andExpect(jsonPath("$.gender", equalTo(MALE.toString())));
    }

    @DisplayName("[GET], [Happy Path], [Method] = getStudentById, [Expected] = StudentDTO with matching fields")
    @Test
    void getStudentByID() throws Exception {
        StudentDTO studentDTO = createAaronSmith();

        when(studentService.getStudentByID(anyLong())).thenReturn(studentDTO);

        mockMvc.perform(get(STUDENTS + ID)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo(FIRST_NAME)))
                .andExpect(jsonPath("$.lastName", equalTo(LAST_NAME)))
                .andExpect(jsonPath("$.gender", equalTo(MALE.toString())));
    }

    @DisplayName("[POST], [Happy Path], [Method] = createNewStudent, [Expected] = StudentDTO  with matching fields")
    @Test
    void createNewStudent() throws Exception {
        StudentDTO returnDTO = createAaronSmith();

        when(studentService.createNewStudent(any(StudentDTO.class))).thenReturn(returnDTO);

        mockMvc.perform(post(STUDENTS)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(returnDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", equalTo(FIRST_NAME)))
                .andExpect(jsonPath("$.lastName", equalTo(LAST_NAME)))
                .andExpect(jsonPath("$.gender", equalTo(MALE.toString())));
    }

    @DisplayName("[PUT], [Happy Path], [Method] = updateStudent, [Expected] = StudentDTO with updated fields")
    @Test
    void updateStudent() throws Exception {
        StudentDTO returnDTO = createAaronSmith();
        returnDTO.setLastName("Roman");

        when(studentService.updateStudent(anyLong(), any(StudentDTO.class))).thenReturn(returnDTO);

        mockMvc.perform(put(STUDENTS + ID)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(returnDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo(FIRST_NAME)))
                .andExpect(jsonPath("$.lastName", equalTo("Roman")))
                .andExpect(jsonPath("$.gender", equalTo(MALE.toString())));
    }

    @DisplayName("[DELETE], [Happy Path], [Method] = deleteStudentByID, [Expected] = studentRepository deleting student")
    @Test
    void deleteStudent() throws Exception {
        mockMvc.perform(delete(STUDENTS + 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(studentService).deleteStudentByID(anyLong());
    }

    @DisplayName("[GET, PUT, DELETE], [Unhappy Path], [Reason] = Student with id not found")
    @Test
    public void testNotFoundException() throws Exception {
        when(studentService.getStudentByID(anyLong())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(STUDENTS + 222)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}