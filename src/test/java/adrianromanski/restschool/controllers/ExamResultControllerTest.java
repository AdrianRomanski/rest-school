package adrianromanski.restschool.controllers;

import adrianromanski.restschool.controllers.event.ExamResultController;
import adrianromanski.restschool.controllers.exception_handler.RestResponseEntityExceptionHandler;
import adrianromanski.restschool.exceptions.ResourceNotFoundException;
import adrianromanski.restschool.model.event.ExamResultDTO;
import adrianromanski.restschool.services.event.exam_result.ExamResultService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static adrianromanski.restschool.controllers.AbstractRestControllerTest.asJsonString;


import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ExamResultControllerTest {

    public static final String NAME = "Biology Exam Result";
    public static final long ID = 2L;
    public static final String EXAM_RESULTS = "/exam-results/";
    public static final LocalDate NOW = LocalDate.now();
    @Mock
    ExamResultService examResultService;

    @InjectMocks
    ExamResultController examResultController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(examResultController)
                .setControllerAdvice(RestResponseEntityExceptionHandler.class)
                .build();
    }

    ExamResultDTO initMathExam() {
        ExamResultDTO examResultDTO = new ExamResultDTO();
        examResultDTO.setName("Math Exam Result");
        examResultDTO.setId(1L);
        return examResultDTO;
    }

    ExamResultDTO initBiologyExam() {
        ExamResultDTO examResultDTO = new ExamResultDTO();
        examResultDTO.setName(NAME);
        examResultDTO.setId(ID);
        return examResultDTO;
    }

    @DisplayName("[GET], [Happy Path], [Method] = getAllExams")
    @Test
    void getAllExamResults() throws Exception {
        List<ExamResultDTO> examResultDTOList = Arrays.asList(initBiologyExam(), initMathExam());

        when(examResultService.getAllExamResults()).thenReturn(examResultDTOList);

        mockMvc.perform(get(EXAM_RESULTS)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(examResultDTOList)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.examResultDTOList", hasSize(2)));
    }

    @DisplayName("[GET], [Happy Path], [Method] = getExamResultByID")
    @Test
    void getExamResultByID() throws Exception {
        ExamResultDTO biology = initBiologyExam();

        when(examResultService.getExamResultByID(anyLong())).thenReturn(biology);

        mockMvc.perform(get(EXAM_RESULTS + ID)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(biology)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(NAME)));
    }

    @DisplayName("[GET], [Happy Path], [Method] = getAllPassedExamResults")
    @Test
    void getAllPassedExamResults() throws Exception {
        List<ExamResultDTO> exams = Arrays.asList(initBiologyExam(), initMathExam());

        when(examResultService.getAllPassedExamResults()).thenReturn(exams);

        mockMvc.perform(get(EXAM_RESULTS + "passed")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(exams)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @DisplayName("[GET], [Happy Path], [Method] = getAllNotPassedExamResults")
    @Test
    void getAllNotPassedExamResults() throws Exception {
        List<ExamResultDTO> exams = Arrays.asList(initBiologyExam(), initMathExam());

        when(examResultService.getAllNotPassedExamResults()).thenReturn(exams);

        mockMvc.perform(get(EXAM_RESULTS + "failed")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(exams)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @DisplayName("[GET], [Happy Path], [Method] = getAllPassedForSubject")
    @Test
    void getAllPassedForSubject() throws Exception {
        List<ExamResultDTO> exams = Arrays.asList(initBiologyExam(), initMathExam());

        when(examResultService.getAllPassedForSubject(anyString())).thenReturn(exams);

        mockMvc.perform(get(EXAM_RESULTS + "subject-BIOLOGY/passed")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(exams)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @DisplayName("[GET], [Happy Path], [Method] = getAllNotPassedForSubject")
    @Test
    void getAllNotPassedForSubject() throws Exception {
        List<ExamResultDTO> exams = Arrays.asList(initBiologyExam(), initMathExam());

        when(examResultService.getAllNotPassedForSubject(anyString())).thenReturn(exams);

        mockMvc.perform(get(EXAM_RESULTS + "subject-BIOLOGY/failed")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(exams)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @DisplayName("[GET], [Happy Path], [Method] = getResultsGroupedByGradeAndName")
    @Test
    void getResultsGroupedByGradeAndName() throws Exception {
        Map<String, Map<String, List<ExamResultDTO>>> map = new HashMap<>();
        Map<String, List<ExamResultDTO>> nestedMap = new HashMap<>();

        nestedMap.put("Walter", Arrays.asList(new ExamResultDTO(), new ExamResultDTO()));
        map.put("A", nestedMap);

        when(examResultService.getResultsGroupedByGradeAndName()).thenReturn(map);

        mockMvc.perform(get(EXAM_RESULTS + "groupedBy/grade-name")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(map)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.A.Walter", hasSize(2)));
    }

    @DisplayName("[GET], [Happy Path], [Method] = getResultGroupedByDateAndGrade")
    @Test
    void getResultGroupedByDateAndGrade() throws Exception {
        Map<LocalDate, Map<String, List<ExamResultDTO>>> map = new HashMap<>();
        Map<String, List<ExamResultDTO>> nestedMap = new HashMap<>();

        nestedMap.put("A", Arrays.asList(new ExamResultDTO(), new ExamResultDTO()));
        map.put(NOW, nestedMap);

        when(examResultService.getResultGroupedByDateAndGrade()).thenReturn(map);

        mockMvc.perform(get(EXAM_RESULTS + "groupedBy/date-grade")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(map)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$." + NOW + ".A", hasSize(2)));
    }

    @DisplayName("[POST], [Happy Path], [Method] = createExamResult")
    @Test
    void createExamResult() throws Exception {
        ExamResultDTO biology = initBiologyExam();

        when(examResultService.createExamResult(any(ExamResultDTO.class))).thenReturn(biology);

        mockMvc.perform(post(EXAM_RESULTS)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(biology)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo(NAME)));
    }

    @DisplayName("[PUT], [Happy Path], [Method] = createExamResult")
    @Test
    void updateExamResult() throws Exception {
        ExamResultDTO biology = initBiologyExam();

        when(examResultService.updateExamResult(anyLong(), any(ExamResultDTO.class))).thenReturn(biology);

        mockMvc.perform(put(EXAM_RESULTS + ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(biology)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(NAME)));
    }

    @DisplayName("[DELETE], [Happy Path], [Method] = deleteExamResultByID")
    @Test
    void deleteExamResultByID() throws Exception {
        mockMvc.perform(delete(EXAM_RESULTS + ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(examResultService).deleteExamResultByID(ID);
    }

    @DisplayName("[DELETE, GET, PUT], [Unhappy PAth], [Exception] = ResourceNotFoundException")
    @Test
    public void testNotFoundException() throws Exception {
        when(examResultService.getExamResultByID(anyLong())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get("/exam-results/222")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}