package adrianromanski.restschool.controllers;

import adrianromanski.restschool.controllers.exception_handler.RestResponseEntityExceptionHandler;
import adrianromanski.restschool.controllers.group.StudentClassController;
import adrianromanski.restschool.domain.base_entity.enums.MaleName;
import adrianromanski.restschool.domain.base_entity.group.StudentClass;
import adrianromanski.restschool.exceptions.ResourceNotFoundException;
import adrianromanski.restschool.model.base_entity.group.StudentClassDTO;
import adrianromanski.restschool.model.base_entity.person.TeacherDTO;
import adrianromanski.restschool.services.group.student_class.StudentClassService;
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
import java.util.List;

import static adrianromanski.restschool.domain.base_entity.enums.MaleName.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class StudentClassControllerTest extends AbstractRestControllerTest {

    public static final String NAME = "First year";
    public static final long ID = 1L;
    public static final String STUDENT_CLASS = "/student-class/";

    @Mock
    StudentClassService studentClassService;

    @InjectMocks
    StudentClassController studentClassController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(studentClassController)
                                .setControllerAdvice(RestResponseEntityExceptionHandler.class)
                                .build();
    }

    StudentClassDTO initStudentClassDTO() {
        StudentClassDTO studentClassDTO = StudentClassDTO.builder().name(NAME).president(ISAAC.get()).build();
        studentClassDTO.setId(ID);
        return studentClassDTO;
    }

    @DisplayName("[GET], [Happy Path], [Method] = getAllStudentClasses, [Expected] = List containing 3 Student Classes")
    @Test
    void getAllStudentClasses() throws Exception {
        List<StudentClassDTO> studentClassDTOList = Arrays.asList(new StudentClassDTO(), new StudentClassDTO());

        when(studentClassService.getAllStudentClasses()).thenReturn(studentClassDTOList);

        mockMvc.perform(get(STUDENT_CLASS)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(studentClassDTOList)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.studentClasses", hasSize(2)));
    }

    @DisplayName("[GET], [Happy Path], [Method] = getStudentClassByID, [Expected] = StudentClassDTO with matching fields")
    @Test
    void getStudentClassByID() throws Exception {
        StudentClassDTO studentClassDTO = initStudentClassDTO();

        when(studentClassService.getStudentClassByID(ID)).thenReturn(studentClassDTO);

        mockMvc.perform(get(STUDENT_CLASS + studentClassDTO.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(studentClassDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(NAME)))
                .andExpect(jsonPath("$.president", equalTo(ISAAC.get())));
    }

    @DisplayName("[POST], [Happy Path], [Method] = createNewStudentClass, [Expected] = StudentClassDTO  with matching fields")
    @Test
    void createNewStudentClass() throws Exception {
        StudentClassDTO studentClassDTO = initStudentClassDTO();

        when(studentClassService.createNewStudentClass(any(StudentClassDTO.class))).thenReturn(studentClassDTO);

        mockMvc.perform(post(STUDENT_CLASS)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(studentClassDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo(NAME)))
                .andExpect(jsonPath("$.president", equalTo(ISAAC.get())));
    }

    @DisplayName("[PUT], [Happy Path], [Method] = updateStudentClass, [Expected] = StudentClassDTO with updated fields")
    @Test
    void updateStudentClass() throws Exception {
        StudentClassDTO studentClassDTO = initStudentClassDTO();

        when(studentClassService.updateStudentClass(anyLong(), any(StudentClassDTO.class))).thenReturn(studentClassDTO);

        mockMvc.perform(put(STUDENT_CLASS + ID)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(studentClassDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(NAME)))
                .andExpect(jsonPath("$.president", equalTo(ISAAC.get())));
    }

    @DisplayName("[DELETE], [Happy Path], [Method] = deleteStudentClassById, [Expected] = studentClassService deleting")
    @Test
    void deleteStudentClassById() throws Exception {

        mockMvc.perform(delete(STUDENT_CLASS + 2)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(studentClassService).deleteStudentClassById(anyLong());
    }

    @DisplayName("[GET, PUT, DELETE], [Unhappy Path], [Reason] = Student Class with id 222 not found")
    @Test
    public void testNotFoundException() throws Exception {

        when(studentClassService.getStudentClassByID(anyLong())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(STUDENT_CLASS + "444")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
