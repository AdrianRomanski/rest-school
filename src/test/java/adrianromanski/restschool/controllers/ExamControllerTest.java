package adrianromanski.restschool.controllers;

import adrianromanski.restschool.controllers.event.ExamController;
import adrianromanski.restschool.controllers.exception_handler.RestResponseEntityExceptionHandler;
import adrianromanski.restschool.exceptions.ResourceNotFoundException;
import adrianromanski.restschool.model.base_entity.event.ExamDTO;
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

        when(examService.getExamById(ID)).thenReturn(examDTO);

        mockMvc.perform(get(EXAMS + ID)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(examDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(NAME)))
                .andExpect(jsonPath("$.maxPoints", equalTo(100)))
                .andExpect(jsonPath("$.id", equalTo(1)));
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