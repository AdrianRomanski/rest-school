package adrianromanski.restschool.controllers;

import adrianromanski.restschool.controllers.event.ExamResultController;
import adrianromanski.restschool.controllers.exception_handler.RestResponseEntityExceptionHandler;
import adrianromanski.restschool.exceptions.ResourceNotFoundException;
import adrianromanski.restschool.model.base_entity.event.ExamResultDTO;
import adrianromanski.restschool.services.event.exam_result.ExamResultService;
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

import static adrianromanski.restschool.controllers.AbstractRestControllerTest.asJsonString;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ExamResultControllerTest {

    public static final String NAME = "Biology Exam Result";
    public static final long ID = 2L;
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

    @Test
    void getAllExamResults() throws Exception {
        List<ExamResultDTO> examResultDTOList = Arrays.asList(initBiologyExam(), initMathExam());

        when(examResultService.getAllExamResults()).thenReturn(examResultDTOList);

        mockMvc.perform(get("/exam-results/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(examResultDTOList)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.examResultDTOList", hasSize(2)));
    }

    @Test
    void getExamResultByID() throws Exception {
        ExamResultDTO biology = initBiologyExam();

        when(examResultService.getExamResultByID(anyLong())).thenReturn(biology);

        mockMvc.perform(get("/exam-results/" + ID)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(biology)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(NAME)));
    }

    @Test
    void createExamResult() throws Exception {
        ExamResultDTO biology = initBiologyExam();

        ExamResultDTO returnBiology = new ExamResultDTO();
        returnBiology.setName(biology.getName());
        returnBiology.setId(biology.getId());

        when(examResultService.createExamResult(biology)).thenReturn(returnBiology);

        mockMvc.perform(post("/exam-results/")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(returnBiology)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo(NAME)));
    }

    @Test
    void updateExamResult() throws Exception {
        ExamResultDTO biology = initBiologyExam();

        ExamResultDTO returnBiology = new ExamResultDTO();
        returnBiology.setName(biology.getName());
        returnBiology.setId(biology.getId());

        when(examResultService.updateExamResult(ID, biology)).thenReturn(returnBiology);

        mockMvc.perform(put("/exam-results/" + ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(biology)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(NAME)));
    }

    @Test
    void deleteExamResultByID() throws Exception {

        mockMvc.perform(delete("/exam-results/" + ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(examResultService).deleteExamResultByID(ID);
    }

    @Test
    public void testNotFoundException() throws Exception {

        when(examResultService.getExamResultByID(anyLong())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get("/exam-results/222")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}