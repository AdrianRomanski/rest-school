package adrianromanski.restschool.services;

import adrianromanski.restschool.domain.base_entity.Subject;
import adrianromanski.restschool.domain.enums.Subjects;
import adrianromanski.restschool.domain.event.Exam;
import adrianromanski.restschool.domain.event.ExamResult;
import adrianromanski.restschool.domain.person.Teacher;
import adrianromanski.restschool.exceptions.ResourceNotFoundException;
import adrianromanski.restschool.mapper.event.ExamResultMapper;
import adrianromanski.restschool.model.event.ExamResultDTO;
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
import java.util.Map;
import java.util.Optional;

import static adrianromanski.restschool.domain.enums.LastName.*;
import static adrianromanski.restschool.domain.enums.MaleName.*;
import static adrianromanski.restschool.domain.enums.Subjects.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;


class ExamResultServiceImplTest {

    public static final int SCORE = 60;
    public static final long ID = 1L;
    public static final LocalDate DATE = LocalDate.now();

    @Mock
    ExamResultRepository examResultRepository;

    ExamResultService examResultService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        examResultService = new ExamResultServiceImpl(examResultRepository, ExamResultMapper.INSTANCE);
    }

    private ExamResult initExamResult(int score, String subjectName) {
        Exam exam = Exam.builder().maxPoints(100L).build();
        exam.setSubject(Subject.builder().name(Subjects.valueOf(subjectName.toUpperCase())).build());
        exam.setTeacher(Teacher.builder().firstName(ETHAN.get()).lastName(HENDERSON.get()).build());
        ExamResult examResult = ExamResult.builder().name(subjectName).date(DATE).exam(exam).build();
        examResult.setScore(score);
        examResult.setId(ID);
        return examResult;
    }

    private ExamResult initBiology() {
        return initExamResult(60, BIOLOGY.get());
    }

    private ExamResultDTO initExamResultDTO() {
        ExamResultDTO examResultDTO = ExamResultDTO.builder().name(BIOLOGY.get()).date(LocalDate.now()).score(SCORE).build();
        examResultDTO.setId(ID);
        return examResultDTO;
    }

    /**
     * @return Exam results with grades  F - Biology, E - Biology, D - Mathematics, C - Mathematics
     */
    private List<ExamResult> initResults() {
        return Arrays.asList(initExamResult(15, BIOLOGY.get()), initExamResult(40, BIOLOGY.get()),
                             initExamResult(50, MATHEMATICS.get()), initExamResult(60,MATHEMATICS.get()));
    }


    @DisplayName("[Happy Path], [Method] = getAllExams")
    @Test
    void getAllExamResults() {
        List<ExamResult> resultList = initResults();

        when(examResultRepository.findAll()).thenReturn(resultList);

        List<ExamResultDTO> returnDTO = examResultService.getAllExamResults();

        assertEquals(returnDTO.size(), 4);
    }


    @DisplayName("[Happy Path], [Method] = getExamResultByID")
    @Test
    void getExamResultByIDHappyPath() {
        ExamResult examResult = initBiology();

        when(examResultRepository.findById(anyLong())).thenReturn(Optional.of(examResult));

        ExamResultDTO returnDTO = examResultService.getExamResultByID(ID);

        assertEquals(returnDTO.getId(), ID);
        assertEquals(returnDTO.getName(), BIOLOGY.get());
    }

    
    @DisplayName("[Unhappy Path], [Method] = getExamResultByID")
    @Test
    void getExamResultByIDUnHappyPath() {
        Throwable ex = catchThrowable(() -> examResultService.getExamResultByID(222L));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }


    @DisplayName("[Happy Path], [Method] = getAllPassedExamResults")
    @Test
    void getAllPassedExamResults() {
        List<ExamResult> results = initResults();

        when(examResultRepository.findAll()).thenReturn(results);

        List<ExamResultDTO> returnDTO = examResultService.getAllPassedExamResults();

        assertEquals(returnDTO.size(), 3);
    }


    @DisplayName("[Happy Path], [Method] = getAllPassedExamResults")
    @Test
    void getAllNotPassedExamResults() {
        List<ExamResult> results = initResults();

        when(examResultRepository.findAll()).thenReturn(results);

        List<ExamResultDTO> returnDTO = examResultService.getAllNotPassedExamResults();

        assertEquals(returnDTO.size(), 1);
    }


    @DisplayName("[Happy Path], [Method] = getAllPassedForSubject")
    @Test
    void getAllPassedForSubject() {
        List<ExamResult> results = initResults();

        when(examResultRepository.findAll()).thenReturn(results);

        List<ExamResultDTO> returnDTO = examResultService.getAllPassedForSubject("Biology");

        assertEquals(returnDTO.size(), 1);
    }


    @DisplayName("[Happy Path], [Method] = getAllNotPassedForSubject")
    @Test
    void getAllNotPassedForSubject() {
        List<ExamResult> results = initResults();

        when(examResultRepository.findAll()).thenReturn(results);

        List<ExamResultDTO> returnDTO = examResultService.getAllNotPassedForSubject("Biology");

        assertEquals(returnDTO.size(), 1);
    }


    @DisplayName("[Happy Path], [Method] = getResultsGroupedByGradeAndName")
    @Test
    void getResultsGroupedByGradeAndName() {
        List<ExamResult> results = initResults();

        when(examResultRepository.findAll()).thenReturn(results);

        Map<String, Map<String, List<ExamResultDTO>>> returnDTO = examResultService.getResultsGroupedByGradeAndName();

        assertTrue(returnDTO.containsKey("F"));
        assertTrue(returnDTO.containsKey("E"));
        assertTrue(returnDTO.containsKey("D"));
        assertTrue(returnDTO.containsKey("C"));
        assertEquals(returnDTO.get("F").get(BIOLOGY.get()).size(), 1);
    }


    @DisplayName("[Happy Path], [Method] = getResultGroupedByDateAndGrade")
    @Test
    void getResultGroupedByDateAndGrade() {
        List<ExamResult> results = initResults();

        when(examResultRepository.findAll()).thenReturn(results);

        Map<LocalDate, Map<String, List<ExamResultDTO>>> returnDTO = examResultService.getResultGroupedByDateAndGrade();

        assertTrue(returnDTO.containsKey(DATE));
        assertEquals(returnDTO.get(DATE).get("F").size(), 1);
        assertEquals(returnDTO.get(DATE).get("E").size(), 1);
        assertEquals(returnDTO.get(DATE).get("D").size(), 1);
        assertEquals(returnDTO.get(DATE).get("C").size(), 1);
    }


    @DisplayName("[Happy Path], [Method] = createExamResult")
    @Test
    void createExamResult() {
        ExamResult examResult = initBiology();
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
        ExamResult examResult = initBiology();

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
        ExamResult examResult = initBiology();

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