package adrianromanski.restschool.services;

import adrianromanski.restschool.domain.base_entity.event.Exam;
import adrianromanski.restschool.exceptions.ResourceNotFoundException;
import adrianromanski.restschool.mapper.event.ExamMapper;
import adrianromanski.restschool.model.base_entity.event.ExamDTO;
import adrianromanski.restschool.repositories.event.ExamRepository;
import adrianromanski.restschool.services.event.exam.ExamService;
import adrianromanski.restschool.services.event.exam.ExamServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class ExamServiceImplTest {

    private final String NAME = "Final Math Exam";
    private final LocalDate DATE = LocalDate.now();
    private final Long POINTS = 100L;
    private final Long ID = 1L;

    ExamService examService;

    @Mock
    ExamRepository examRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        examService = new ExamServiceImpl(ExamMapper.INSTANCE, examRepository);
    }

    ExamDTO initExamDTO() {
        ExamDTO examDTO = ExamDTO.builder().date(DATE).name(NAME).maxPoints(POINTS).build();
        examDTO.setId(ID);
        return examDTO;
    }

    Exam initExam() {
        Exam exam = Exam.builder().date(DATE).name(NAME).maxPoints(POINTS).build();
        exam.setId(ID);
        return exam;
    }

    @DisplayName("[Happy Path], [Method] = getAllExams, [Expected] = List containing 3 Exams")
    @Test
    void getAllExams() {
        List<Exam> exams = Arrays.asList(initExam(), initExam(), initExam());

        when(examRepository.findAll()).thenReturn(exams);

        List<ExamDTO> returnDTO = examService.getAllExams();

        assertEquals(returnDTO.size(), 3);
    }

    @DisplayName("[Happy Path], [Method] = getExamById, [Expected] = ExamDTO with matching fields")
    @Test
    void getExamById() {
        Exam exam = initExam();

        when(examRepository.findById(anyLong())).thenReturn(Optional.of(exam));

        ExamDTO returnDTO = examService.getExamById(ID);

        assertEquals(returnDTO.getId(), ID);
        assertEquals(returnDTO.getName(), NAME);
    }

    @DisplayName("[Happy Path], [Method] = createNewExam, [Expected] = ExamDTO with matching fields")
    @Test
    void createNewExam() {
        ExamDTO examDTO = initExamDTO();
        Exam exam = initExam();

        when(examRepository.save(any(Exam.class))).thenReturn(exam);

        ExamDTO returnDTO = examService.createNewExam(examDTO);

        assertEquals(returnDTO.getDate(), DATE);
        assertEquals(returnDTO.getName(), NAME);
        assertEquals(returnDTO.getMaxPoints(), POINTS);
    }

    @DisplayName("[Happy Path], [Method] = updateExam, [Expected] = ExamDTO with updated fields")
    @Test
    void updateExamHappyPath() {
        ExamDTO examDTO = initExamDTO();
        Exam exam = initExam();
        exam.setMaxPoints(50L); // Changing max points

        when(examRepository.findById(anyLong())).thenReturn(Optional.of(exam));
        when(examRepository.save(any(Exam.class))).thenReturn(exam);

        ExamDTO returnDTO = examService.updateExam(ID, examDTO);

        assertEquals(returnDTO.getName(), NAME);
        assertEquals(returnDTO.getId(), ID);
        assertEquals(returnDTO.getMaxPoints(), 50); // updated points
    }

    @DisplayName("[Unhappy Path], [Method] = updateExam, [Reason] = Exam with id 222 not found")
    @Test
    void updateExamUnHappyPath() {
        ExamDTO examDTO = initExamDTO();

        Throwable ex = catchThrowable(() -> examService.updateExam(222L, examDTO));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }

    @DisplayName("[Happy Path], [Method] = deleteExamById, [Expected] = Repository deleting object")
    @Test
    void deleteExamByIdHappyPath() {
        Exam exam = initExam();

        when(examRepository.findById(anyLong())).thenReturn(Optional.of(exam));

        examService.deleteExamById(anyLong());

        verify(examRepository, times(1)).deleteById(anyLong());
    }

    @DisplayName("[Unhappy Path], [Method] = deleteExamById, [Reason] = Exam with id 222 not found")
    @Test
    void deleteExamByIdUnHappyPath() {

        Throwable ex = catchThrowable(() -> examService.deleteExamById(222L));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }

}