package adrianromanski.restschool.controllers;

import adrianromanski.restschool.exceptions.ResourceNotFoundException;
import adrianromanski.restschool.model.StudentDTO;
import adrianromanski.restschool.services.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;


import static org.hamcrest.Matchers.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class StudentControllerTest  extends AbstractRestControllerTest {

    public static final String FIRST_NAME = "Adrian";
    public static final String LAST_NAME = "Romanski";

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

    StudentDTO initStudentDTO() {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setFirstName(FIRST_NAME);
        studentDTO.setLastName(LAST_NAME);
        studentDTO.setId(1L);
        return studentDTO;
    }

    @Test
    void getAllStudents() throws Exception {
        //given
        StudentDTO studentDTO = initStudentDTO();

        StudentDTO studentDTO1 = new StudentDTO();
        studentDTO1.setFirstName("Walter");
        studentDTO1.setLastName("White");
        studentDTO1.setId(2L);

        List<StudentDTO> students = Arrays.asList(studentDTO, studentDTO1);

        when(studentService.getAllStudents()).thenReturn(students);

        mockMvc.perform(get("/students/")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.students", hasSize(2)));
    }

    @Test
    void getStudentByID() throws Exception {
        StudentDTO studentDTO = initStudentDTO();

        when(studentService.getStudentByID(anyLong())).thenReturn(studentDTO);

        mockMvc.perform(get("/students/" + studentDTO.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo(FIRST_NAME)))
                .andExpect(jsonPath("$.lastName", equalTo(LAST_NAME)));
    }

    @Test
    void createNewStudent() throws Exception {
        StudentDTO studentDTO = initStudentDTO();

        StudentDTO returnDTO = new StudentDTO();
        returnDTO.setFirstName(studentDTO.getFirstName());
        returnDTO.setLastName(studentDTO.getLastName());

        when(studentService.createNewStudent(studentDTO)).thenReturn(returnDTO);

        mockMvc.perform(post("/students/")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(studentDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", equalTo(FIRST_NAME)))
                .andExpect(jsonPath("$.lastName", equalTo(LAST_NAME)));
    }

    @Test
    void updateStudent() throws Exception {
        StudentDTO studentDTO = initStudentDTO();

        StudentDTO returnDTO = new StudentDTO();
        returnDTO.setFirstName(studentDTO.getFirstName());
        returnDTO.setLastName("Roman"); // Update value
        returnDTO.setId(studentDTO.getId());

        when(studentService.updateStudent(anyLong(), any(StudentDTO.class))).thenReturn(returnDTO);

        mockMvc.perform(put("/students/" + returnDTO.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(studentDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo(FIRST_NAME)))
                .andExpect(jsonPath("$.lastName", equalTo("Roman")));
    }

    @Test
    void getStudentByFirstAndLastName() throws Exception {
        StudentDTO studentDTO = initStudentDTO();

        when(studentService.getStudentByFirstAndLastName(anyString(), anyString())).thenReturn(studentDTO);

        mockMvc.perform(get("/students/Adrian/Romanski")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(studentDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo(FIRST_NAME)))
                .andExpect(jsonPath("$.lastName", equalTo(LAST_NAME)));
    }

    @Test
    void deleteStudent() throws Exception {
        mockMvc.perform(delete("/students/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(studentService).deleteStudentByID(anyLong());
    }


    @Test
    public void testNotFoundException() throws Exception {

        when(studentService.getStudentByID(anyLong())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get("/students/222")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }}