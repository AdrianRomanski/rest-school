package adrianromanski.restschool.controllers;

import adrianromanski.restschool.controllers.event.ExamController;
import adrianromanski.restschool.controllers.exception_handler.RestResponseEntityExceptionHandler;
import adrianromanski.restschool.exceptions.ResourceNotFoundException;
import adrianromanski.restschool.model.event.ExamDTO;
import adrianromanski.restschool.services.event.exam.ExamService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static adrianromanski.restschool.controllers.AbstractRestControllerTest.asJsonString;
import static adrianromanski.restschool.domain.enums.MaleName.*;
import static adrianromanski.restschool.domain.enums.Subjects.*;
import static org.hamcrest.Matchers.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ExamControllerTest {

    public static final long ID = 1L;
    public static final String EXAMS = "/exams/";
    private final String NAME = "Final Math Exam";

    @Mock
    ExamService examService;

    @InjectMocks
    ExamController examController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(examController)
                .setControllerAdvice(RestResponseEntityExceptionHandler.class)
                .build();
    }

    ExamDTO initMath() {
        ExamDTO examDTO = ExamDTO.builder().name(NAME).maxPoints(100L).build();
        examDTO.setId(ID);
        return examDTO;
    }

    ExamDTO initBiology() {
        ExamDTO examDTO = ExamDTO.builder().name("Final Biology").maxPoints(60L).build();
        examDTO.setId(2L);
        return examDTO;
    }

    @DisplayName("[GET], [Happy Path], [Method] = getAllExams, [Expected] = List containing 2 Exams")
    @Test
    void getAllExams() throws Exception {
        List<ExamDTO> examDTOList = Arrays.asList(initMath(), initBiology());

        when(examService.getAllExams()).thenReturn(examDTOList);

        mockMvc.perform(get(EXAMS)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(examDTOList)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.examDTOList", hasSize(2)));
    }

    @DisplayName("[GET], [Happy Path], [Method] = getExamById, [Expected] = ExamDTO with matching fields")
    @Test
    void getExamById() throws Exception {
        ExamDTO examDTO = initMath();

        when(examService.getExamById(anyLong())).thenReturn(examDTO);

        mockMvc.perform(get(EXAMS + ID)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(examDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(NAME)))
                .andExpect(jsonPath("$.maxPoints", equalTo(100)))
                .andExpect(jsonPath("$.id", equalTo(1)));
    }

    @DisplayName("[GET], [Happy Path], [Method] = getExamByName, [Expected] = ExamDTO with matching fields")
    @Test
    void getExamByName() throws Exception {
        ExamDTO examDTO = initMath();

        when(examService.getExamByName(anyString())).thenReturn(examDTO);

        mockMvc.perform(get(EXAMS + "name-" + NAME)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(examDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(NAME)))
                .andExpect(jsonPath("$.maxPoints", equalTo(100)))
                .andExpect(jsonPath("$.id", equalTo(1)));
    }


    @DisplayName("[GET], [Happy Path], [Method] = getAllExamsForTeacher, [Expected] = List with 2 exams")
    @Test
    void getAllExamsForTeacher() throws Exception {
        List<ExamDTO> exams = Arrays.asList(initMath(), initBiology());

        when(examService.getAllExamsForTeacher(anyString(), anyString())).thenReturn(exams);

        mockMvc.perform(get(EXAMS + "teacher-Walter/White")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(exams)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }


    @DisplayName("[GET], [Happy Path], [Method] = getExamsForSubject, [Expected] = Map with one key and 2 values")
    @Test
    void getExamsForSubject() throws Exception {
        Map<String, List<ExamDTO>> map = new HashMap<>();
        map.put(MATHEMATICS.name(), Arrays.asList(initMath(), initMath()));

        when(examService.getExamsForSubject(MATHEMATICS)).thenReturn(map);

        mockMvc.perform(get(EXAMS + "subject-MATHEMATICS")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(map)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.MATHEMATICS", hasSize(2)));
    }

    @DisplayName("[GET], [Happy Path], [Method] = getAllExamsBySubjectsAndTeachers, [Expected] = Map<Mathematics, Map<Ethan, List<examMath x2>")
    @Test
    void getAllExamsBySubjectsAndTeachers() throws Exception {
        Map<String, Map<String, List<ExamDTO>>> map = new HashMap<>();
        Map<String, List<ExamDTO>> nestedMap = new HashMap<>();
        nestedMap.put(ETHAN.get(), Arrays.asList(initMath(), initMath()));
        map.put(MATHEMATICS.get(), nestedMap);

        when(examService.getAllExamsBySubjectsAndTeachers()).thenReturn(map);

        mockMvc.perform(get(EXAMS + "grouped/subjects-teachers")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(map)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Mathematics.Ethan", hasSize(2)));
    }

    @DisplayName("[GET], [Happy Path], [Method] = getAllExamsByStudentsAndSubjects, [Expected] = Map<3, Map<Biology, List<examBiology x2>")
    @Test
    void getAllExamsByStudentsAndSubjects() throws Exception {
        Map<Integer, Map<String, List<ExamDTO>>> map = new HashMap<>();
        Map<String, List<ExamDTO>> nestedMap = new HashMap<>();
        nestedMap.put(BIOLOGY.get(), Arrays.asList(initBiology(), initBiology()));
        map.put(3, nestedMap);

        when(examService.getAllExamsByStudentsAndSubjects()).thenReturn(map);

        mockMvc.perform(get(EXAMS + "grouped/students-subjects")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(map)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.3.Biology", hasSize(2)));
    }

    @DisplayName("[POST], [Happy Path], [Method] = createNewExam, [Expected] = ExamDTO with matching fields")
    @Test
    void createNewExam() throws Exception {
        ExamDTO examDTO = initMath();

        when(examService.createNewExam(any(ExamDTO.class))).thenReturn(examDTO);

        mockMvc.perform(post(EXAMS)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(examDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo(NAME)))
                .andExpect(jsonPath("$.maxPoints", equalTo(100)));
    }

    @DisplayName("[PUT], [Happy Path], [Method] = updateExam, [Expected] = ExamDTO with updated fields")
    @Test
    void updateExam() throws Exception {
        ExamDTO examDTO = initMath();
        examDTO.setName("A simple exam"); //Updating name

        when(examService.updateExam(anyLong(), any(ExamDTO.class))).thenReturn(examDTO);

        mockMvc.perform(put(EXAMS + examDTO.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(examDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("A simple exam"))) // Updated name
                .andExpect(jsonPath("$.maxPoints", equalTo(100)))
                .andExpect(jsonPath("$.id", equalTo(1)));
    }

    @DisplayName("[DELETE], [Happy Path], [Method] = deleteExamById, [Expected] = Service deleting object")
    @Test
    void deleteExamById() throws Exception {
        mockMvc.perform(delete(EXAMS + ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(examService).deleteExamById(anyLong());
    }

    @DisplayName("[GET, PUT, DELETE], [UnHappy Path], [Reason] = Exam with id 222 not found")
    @Test
    public void testNotFoundException() throws Exception {

        when(examService.getExamById(anyLong())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(EXAMS + 222)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}