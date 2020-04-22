package adrianromanski.restschool.controllers;

import adrianromanski.restschool.controllers.exception_handler.RestResponseEntityExceptionHandler;
import adrianromanski.restschool.controllers.group.StudentClassController;
import adrianromanski.restschool.exceptions.ResourceNotFoundException;
import adrianromanski.restschool.model.base_entity.group.StudentClassDTO;
import adrianromanski.restschool.model.base_entity.person.TeacherDTO;
import adrianromanski.restschool.services.group.student_class.StudentClassService;
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
    public static final String JESSIE_PINKMAN = "Jessie Pinkman";
    public static final String STUDENT_CLASS = "/student-class/";
    public static final String WALTER = "Walter";
    public static final String WHITE = "White";
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
        StudentClassDTO studentClassDTO = new StudentClassDTO();
        studentClassDTO.setName(NAME);
        studentClassDTO.setId(ID);
        studentClassDTO.setPresident(JESSIE_PINKMAN);
        return studentClassDTO;
    }

    @Test
    void getAllStudentClasses() throws Exception {

        List<StudentClassDTO> studentClassDTOList = Arrays.asList(new StudentClassDTO(), new StudentClassDTO());

        when(studentClassService.getAllStudentClasses()).thenReturn(studentClassDTOList);

        mockMvc.perform(get(STUDENT_CLASS + "list")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(studentClassDTOList)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.studentClasses", hasSize(2)));
    }

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
                .andExpect(jsonPath("$.president", equalTo(JESSIE_PINKMAN)));
    }

    @Test
    void createNewStudentClass() throws Exception {
        StudentClassDTO studentClassDTO = initStudentClassDTO();

        StudentClassDTO returnDTO = new StudentClassDTO();
        returnDTO.setPresident(studentClassDTO.getPresident());
        returnDTO.setName(studentClassDTO.getName());
        returnDTO.setId(studentClassDTO.getId());

        when(studentClassService.createNewStudentClass(studentClassDTO)).thenReturn(returnDTO);

        mockMvc.perform(post(STUDENT_CLASS)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(studentClassDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo(NAME)))
                .andExpect(jsonPath("$.president", equalTo(JESSIE_PINKMAN)));
    }

    @Test
    void updateStudentClass() throws Exception {
        StudentClassDTO studentClassDTO = initStudentClassDTO();

        StudentClassDTO returnDTO = new StudentClassDTO();
        returnDTO.setPresident(studentClassDTO.getPresident());
        returnDTO.setName(studentClassDTO.getName());
        returnDTO.setId(studentClassDTO.getId());

        when(studentClassService.updateStudentClass(ID, studentClassDTO)).thenReturn(returnDTO);

        mockMvc.perform(put(STUDENT_CLASS + studentClassDTO.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(studentClassDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(NAME)))
                .andExpect(jsonPath("$.president", equalTo(JESSIE_PINKMAN)));
    }

    @Test
    void getStudentClassTeacher() throws Exception {
        StudentClassDTO studentClassDTO = initStudentClassDTO();
        TeacherDTO teacherDTO = new TeacherDTO();
        teacherDTO.setFirstName(WALTER);
        teacherDTO.setLastName(WHITE);
        teacherDTO.setId(ID);
        studentClassDTO.setTeacherDTO(teacherDTO);

        when(studentClassService.getStudentClassTeacher(studentClassDTO)).thenReturn(teacherDTO);

        mockMvc.perform(get(STUDENT_CLASS + "teacher")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(studentClassDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo(WALTER)))
                .andExpect(jsonPath("$.lastName", equalTo(WHITE)));
    }

    @Test
    void deleteStudentClassById() throws Exception {
        mockMvc.perform(delete(STUDENT_CLASS + 2)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(studentClassService).deleteStudentClassById(anyLong());
    }

    @Test
    public void testNotFoundException() throws Exception {

        when(studentClassService.getStudentClassByID(anyLong())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(STUDENT_CLASS + "444")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
