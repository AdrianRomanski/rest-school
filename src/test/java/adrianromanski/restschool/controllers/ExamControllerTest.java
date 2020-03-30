package adrianromanski.restschool.controllers;

import adrianromanski.restschool.exceptions.ResourceNotFoundException;
import adrianromanski.restschool.model.base_entity.event.ExamDTO;
import adrianromanski.restschool.services.exam.ExamService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ExamControllerTest {

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
        ExamDTO examDTO = new ExamDTO();
        examDTO.setName(NAME);
        examDTO.setMaxPoints(100L);
        examDTO.setId(1L);
        return examDTO;
    }

    ExamDTO initBiology() {
        ExamDTO examDTO = new ExamDTO();
        examDTO.setName("Final Biology");
        examDTO.setMaxPoints(60L);
        examDTO.setId(2L);
        return examDTO;
    }

    @Test
    void getAllExams() throws Exception {
        List<ExamDTO> examDTOList = Arrays.asList(initMath(), initBiology());

        when(examService.getAllExams()).thenReturn(examDTOList);

        mockMvc.perform(get("/exams/")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(examDTOList)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.examDTOList", hasSize(2)));
    }

    @Test
    void createNewExam() throws Exception {
        ExamDTO examDTO = initMath();

        ExamDTO returnDTO = new ExamDTO();
        returnDTO.setMaxPoints(examDTO.getMaxPoints());
        returnDTO.setName(examDTO.getName());

        when(examService.createNewExam(examDTO)).thenReturn(returnDTO);

        mockMvc.perform(post("/exams/")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(examDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo(NAME)))
                .andExpect(jsonPath("$.maxPoints", equalTo(100)));
    }

    @Test
    void updateExam() throws Exception {
        ExamDTO examDTO = initMath();

        ExamDTO returnDTO = new ExamDTO();
        returnDTO.setMaxPoints(examDTO.getMaxPoints());
        returnDTO.setName("A simple exam"); //Updating name
        returnDTO.setId(examDTO.getId());

        when(examService.updateExam(examDTO.getId(), examDTO)).thenReturn(returnDTO);

        mockMvc.perform(put("/exams/" + examDTO.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(examDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("A simple exam")))
                .andExpect(jsonPath("$.maxPoints", equalTo(100)))
                .andExpect(jsonPath("$.id", equalTo(1)));
    }

    @Test
    void getExamById() throws Exception {
        ExamDTO examDTO = initMath();

        when(examService.getExamById(examDTO.getId())).thenReturn(examDTO);

        mockMvc.perform(get("/exams/" + examDTO.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(examDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(NAME)))
                .andExpect(jsonPath("$.maxPoints", equalTo(100)))
                .andExpect(jsonPath("$.id", equalTo(1)));
    }

    @Test
    void deleteExamById() throws Exception {
        mockMvc.perform(delete("/exams/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(examService).deleteExamById(anyLong());

    }


    @Test
    public void testNotFoundException() throws Exception {

        when(examService.getExamById(anyLong())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get("/exams/222")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
