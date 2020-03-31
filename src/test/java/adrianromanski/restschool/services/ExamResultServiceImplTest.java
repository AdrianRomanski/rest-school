package adrianromanski.restschool.services;

import adrianromanski.restschool.domain.base_entity.event.ExamResult;
import adrianromanski.restschool.mapper.ExamResultMapper;
import adrianromanski.restschool.model.base_entity.event.ExamResultDTO;
import adrianromanski.restschool.repositories.ExamResultRepository;
import adrianromanski.restschool.services.exam_result.ExamResultService;
import adrianromanski.restschool.services.exam_result.ExamResultServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class ExamResultServiceImplTest {


    public static final int SCORE = 60;
    public static final long ID = 1L;

    @Mock
    ExamResultRepository examResultRepository;

    ExamResultService examResultService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        examResultService = new ExamResultServiceImpl(examResultRepository, ExamResultMapper.INSTANCE);
    }

    @Test
    void getAllExamResults() {
        List<ExamResult> resultList = Arrays.asList(new ExamResult(), new ExamResult(), new ExamResult());

        when(examResultRepository.findAll()).thenReturn(resultList);

        List<ExamResultDTO> examResultDTOS = examResultService.getAllExamResults();

        assertEquals(examResultDTOS.size(), resultList.size());

    }

    @Test
    void getExamResultByID() {
        ExamResult examResult = new ExamResult();
        examResult.setScore(SCORE);
        examResult.setId(ID);

        when(examResultRepository.findById(anyLong())).thenReturn(Optional.of(examResult));

        ExamResultDTO returnDTO = examResultService.getExamResultByID(ID);

        assertEquals(returnDTO.getScore(), examResult.getScore());
        assertEquals(returnDTO.getId(), examResult.getId());
    }

    @Test
    void createExamResult() {
        ExamResultDTO examResultDTO = new ExamResultDTO();
        examResultDTO.setId(ID);
        examResultDTO.setScore(SCORE);

        ExamResult savedExamResult = new ExamResult();
        savedExamResult.setScore(examResultDTO.getScore());
        savedExamResult.setId(examResultDTO.getId());


        when(examResultRepository.save(any(ExamResult.class))).thenReturn(savedExamResult);

        ExamResultDTO savedDTO = examResultService.createExamResult(examResultDTO);

        assertEquals(savedDTO.getId(), examResultDTO.getId());
        assertEquals(savedDTO.getScore(), examResultDTO.getScore());
    }

    @Test
    void updateExam() {
        ExamResultDTO examResultDTO = new ExamResultDTO();
        examResultDTO.setId(ID);
        examResultDTO.setScore(SCORE);

        ExamResult updatedExamResult = new ExamResult();
        updatedExamResult.setScore(examResultDTO.getScore());
        updatedExamResult.setId(examResultDTO.getId());

        when(examResultRepository.save(any(ExamResult.class))).thenReturn(updatedExamResult);

        ExamResultDTO updatedDTO = examResultService.updateExam(ID, examResultDTO);

        assertEquals(updatedDTO.getScore(), SCORE);
        assertEquals(updatedDTO.getId(), ID);
    }

    @Test
    void deleteExamResultByID() {
        examResultRepository.deleteById(ID);
        verify(examResultRepository, times(1)).deleteById(ID);
    }
}