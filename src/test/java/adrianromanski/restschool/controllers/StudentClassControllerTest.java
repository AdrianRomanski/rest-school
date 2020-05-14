package adrianromanski.restschool.controllers;

import adrianromanski.restschool.controllers.exception_handler.RestResponseEntityExceptionHandler;
import adrianromanski.restschool.controllers.group.StudentClassController;
import adrianromanski.restschool.domain.enums.Gender;
import adrianromanski.restschool.domain.enums.Subjects;
import adrianromanski.restschool.exceptions.ResourceNotFoundException;
import adrianromanski.restschool.model.group.StudentClassDTO;
import adrianromanski.restschool.model.person.StudentDTO;
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

import java.util.*;

import static adrianromanski.restschool.domain.enums.Gender.*;
import static adrianromanski.restschool.domain.enums.MaleName.*;
import static adrianromanski.restschool.domain.enums.Subjects.BIOLOGY;
import static java.util.Collections.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
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

    private List<StudentDTO> initStudentsList() {
        return Arrays.asList(new StudentDTO(), new StudentDTO());
    }

    private List<StudentClassDTO> initStudentClassList() {
        return Arrays.asList(initStudentClassDTO(), initStudentClassDTO());
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

    @DisplayName("[GET], [Happy Path], [Method] = getStudentClassesGroupedBySpecialization, [Expected] = Map contains 1 key with 2 students")
    @Test
    void getStudentClassesBySpecialization() throws Exception {
        Map<Subjects, Map<String, List<StudentClassDTO>>> map = new HashMap<>();
        Map<String, List<StudentClassDTO>> insideMap = new HashMap<>();
        insideMap.put("Rookies", initStudentClassList());
        map.put(BIOLOGY, insideMap);

        when(studentClassService.getStudentClassesGroupedBySpecialization()).thenReturn(map);

        mockMvc.perform(get(STUDENT_CLASS + "specializations")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(map)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.BIOLOGY.Rookies", hasSize(2)));
    }


    @DisplayName("[GET], [Happy Path], [Method] = getAllStudentClassForSpecialization, [Expected] = List with 1 Class and 2 Students")
    @Test
    void getAllStudentClassForSpecialization() throws Exception {
        List<StudentClassDTO> list = new ArrayList<>();
        StudentClassDTO studentClass = initStudentClassDTO();
        studentClass.getStudentDTOList().addAll(initStudentsList());
        list.add(studentClass);

        when(studentClassService.getAllStudentClassForSpecialization(BIOLOGY)).thenReturn(list);

        mockMvc.perform(get(STUDENT_CLASS + "specialization/BIOLOGY")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(list)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id",equalTo(1)))
                .andExpect(jsonPath("$.[0].studentDTOList",hasSize(2)));
    }

    @DisplayName("[GET], [Happy Path], [Method] = getAllStudentsForClass, [Expected] = Map with 2 keys")
    @Test
    void getAllStudentsForClass() throws Exception {
        Map<Gender, List<StudentDTO>> map = new HashMap<>();
        List<StudentDTO> list = initStudentsList();
        map.put(MALE, list);
        map.put(FEMALE, singletonList(new StudentDTO()));

        when(studentClassService.getAllStudentsForClass(anyLong())).thenReturn(map);

        mockMvc.perform(get(STUDENT_CLASS + "ID-1/students")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(map)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.MALE", hasSize(2)))
                .andExpect(jsonPath("$.FEMALE", hasSize(1)));
    }


    @DisplayName("[GET], [Happy Path], [Method] = getLargestStudentClass, [Expected] = List with 2 students")
    @Test
    void getLargestStudentClass() throws Exception {
        List<StudentClassDTO> list = initStudentClassList();

        when(studentClassService.getLargestStudentClass()).thenReturn(list);

        mockMvc.perform(get(STUDENT_CLASS + "largest")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(list)))
                .andExpect(status().isOk());
    }

    @DisplayName("[GET], [Happy Path], [Method] = getSmallestStudentClass, [Expected] = List with 0 students")
    @Test
    void getSmallestStudentClass() throws Exception {
        List<StudentClassDTO> list = new ArrayList<>();

        when(studentClassService.getSmallestStudentClass()).thenReturn(list);

        mockMvc.perform(get(STUDENT_CLASS + "smallest")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(list)))
                .andExpect(status().isOk());
    }

    @DisplayName("[GET], [Happy Path], [Method] = getStudentClassByPresident, [Expected] = List with 1 Student Class")
    @Test
    void getStudentClassByPresident() throws Exception {
        List<StudentClassDTO> list = singletonList(initStudentClassDTO());

        when(studentClassService.getStudentClassByPresident(anyString())).thenReturn(list);

        mockMvc.perform(get(STUDENT_CLASS + "president-isaac")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(list)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].name", equalTo(NAME)));
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
