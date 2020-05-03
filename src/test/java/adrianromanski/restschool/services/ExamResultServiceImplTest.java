package adrianromanski.restschool.services;

import adrianromanski.restschool.domain.base_entity.event.ExamResult;
import adrianromanski.restschool.exceptions.ResourceNotFoundException;
import adrianromanski.restschool.mapper.event.ExamResultMapper;
import adrianromanski.restschool.model.base_entity.event.ExamResultDTO;
import adrianromanski.restschool.repositories.event.ExamResultRepository;
import adrianromanski.restschool.services.event.exam_result.ExamResultService;
import adrianromanski.restschool.services.event.exam_result.ExamResultServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static adrianromanski.restschool.domain.base_entity.enums.Subjects.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
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

    private ExamResult initExamResult() {
        ExamResult examResult = ExamResult.builder().name(BIOLOGY.get()).date(LocalDate.now()).score(SCORE).build();
        examResult.setId(ID);
        return examResult;
    }

    private ExamResultDTO initExamResultDTO() {
        ExamResultDTO examResultDTO = ExamResultDTO.builder().name(BIOLOGY.get()).date(LocalDate.now()).score(SCORE).build();
        examResultDTO.setId(ID);
        return examResultDTO;
    }

    @DisplayName("[Happy Path], [Method] = getAllExams")
    @Test
    void getAllExamResults() {
        List<ExamResult> resultList = Arrays.asList(initExamResult(), initExamResult(), initExamResult());

        when(examResultRepository.findAll()).thenReturn(resultList);

        List<ExamResultDTO> returnDTO = examResultService.getAllExamResults();

        assertEquals(returnDTO.size(), 3);
    }

    @DisplayName("[Happy Path], [Method] = getExamResultByID")
    @Test
    void getExamResultByID() {
        ExamResult examResult = initExamResult();

        when(examResultRepository.findById(anyLong())).thenReturn(Optional.of(examResult));

        ExamResultDTO returnDTO = examResultService.getExamResultByID(ID);

        assertEquals(returnDTO.getId(), ID);
        assertEquals(returnDTO.getName(), BIOLOGY.get());
    }

    @DisplayName("[Happy Path], [Method] = createExamResult")
    @Test
    void createExamResult() {
        ExamResult examResult = initExamResult();
        ExamResultDTO examResultDTO = initExamResultDTO();

        when(examResultRepository.save(any(ExamResult.class))).thenReturn(examResult);

        ExamResultDTO returnDTO = examResultService.createExamResult(examResultDTO);

        assertEquals(returnDTO.getId(), ID);
        assertEquals(returnDTO.getScore(), SCORE);
    }

    @DisplayName("[Happy Path], [Method] = updateExamResult")
    @Test
    void updateExamResult() {
        ExamResultDTO examResultDTO = initExamResultDTO();
        examResultDTO.setName("Updated");
        ExamResult examResult = initExamResult();

        when(examResultRepository.findById(anyLong())).thenReturn(Optional.of(examResult));
        when(examResultRepository.save(any(ExamResult.class))).thenReturn(examResult);

        ExamResultDTO returnDTO = examResultService.updateExamResult(ID, examResultDTO);

        assertEquals(returnDTO.getName(), "Updated");
        assertEquals(returnDTO.getId(), ID);
        assertEquals(returnDTO.getScore(), SCORE);
    }

    @DisplayName("[Unhappy Path], [Method] = updateExamResult")
    @Test
    void updateExamResultUnHappyPath() {
        ExamResultDTO examResultDTO = initExamResultDTO();

        Throwable ex = catchThrowable(() -> examResultService.updateExamResult(222L, examResultDTO));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }

    @DisplayName("[Happy Path], [Method] = deleteExamResultByID")
    @Test
    void deleteExamResultByIDHappyPath() {
        ExamResult examResult = initExamResult();

        when(examResultRepository.findById(anyLong())).thenReturn(Optional.of(examResult));

        examResultService.deleteExamResultByID(anyLong());

        verify(examResultRepository, times(1)).deleteById(anyLong());
    }

    @DisplayName("[Unhappy Path], [Method] = deleteExamResultByID")
    @Test
    void deleteExamResultByIDUnHappyPath() {

        Throwable ex = catchThrowable(() -> examResultService.deleteExamResultByID(222L));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }
}