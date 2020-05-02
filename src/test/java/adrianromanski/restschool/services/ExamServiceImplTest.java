package adrianromanski.restschool.services;

import adrianromanski.restschool.domain.base_entity.Subject;
import adrianromanski.restschool.domain.base_entity.event.Exam;
import adrianromanski.restschool.domain.base_entity.person.Student;
import adrianromanski.restschool.domain.base_entity.person.Teacher;
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
import java.util.Map;
import java.util.Optional;

import static adrianromanski.restschool.domain.base_entity.enums.LastName.*;
import static adrianromanski.restschool.domain.base_entity.enums.MaleName.*;
import static adrianromanski.restschool.domain.base_entity.enums.Subjects.BIOLOGY;
import static adrianromanski.restschool.domain.base_entity.enums.Subjects.MATHEMATICS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class ExamServiceImplTest {

    private final String NAME = "Final Biology Exam";
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

    ExamDTO initBiologyExamDTO() {
        ExamDTO examDTO = ExamDTO.builder().date(DATE).name(NAME).maxPoints(POINTS).build();
        examDTO.setId(ID);
        return examDTO;
    }

    Exam initBiologyExam() {
        Exam exam = Exam.builder().date(DATE).name(NAME).maxPoints(POINTS).build();
        exam.setSubject(Subject.builder().name(BIOLOGY).build());
        exam.setTeacher(Teacher.builder().firstName(ETHAN.get()).lastName(HENDERSON.get()).build());
        exam.setStudents(Arrays.asList(new Student(), new Student()));
        exam.setId(ID);
        return exam;
    }

    Exam initMathExam() {
        Exam exam = Exam.builder().date(DATE).name("Fast Test").maxPoints(80L).build();
        exam.setSubject(Subject.builder().name(MATHEMATICS).build());
        exam.setTeacher(Teacher.builder().firstName(SEBASTIAN.get()).lastName(SMITH.get()).build());
        exam.setStudents(Arrays.asList(new Student(), new Student(), new Student()));
        exam.setId(2L);
        return exam;
    }

    private List<Exam> getExamList() {
        return Arrays.asList(initBiologyExam(), initBiologyExam(), initMathExam());
    }

    @DisplayName("[Happy Path], [Method] = getAllExams, [Expected] = List containing 3 Exams")
    @Test
    void getAllExams() {
        List<Exam> exams = getExamList();

        when(examRepository.findAll()).thenReturn(exams);

        List<ExamDTO> returnDTO = examService.getAllExams();

        assertEquals(returnDTO.size(), 3);
    }


    @DisplayName("[Happy Path], [Method] = getExamById, [Expected] = ExamDTO with matching fields")
    @Test
    void getExamById() {
        Exam exam = initBiologyExam();

        when(examRepository.findById(anyLong())).thenReturn(Optional.of(exam));

        ExamDTO returnDTO = examService.getExamById(ID);

        assertEquals(returnDTO.getId(), ID);
        assertEquals(returnDTO.getName(), NAME);
        assertEquals(returnDTO.getMaxPoints(), POINTS);
    }


    @DisplayName("[Happy Path], [Method] = getExamByName, [Expected] = ExamDTO with matching fields")
    @Test
    void getExamByName() {
        Exam exam = initBiologyExam();

        when(examRepository.getByName(anyString())).thenReturn(Optional.of(exam));

        ExamDTO returnDTO = examService.getExamByName(anyString());

        assertEquals(returnDTO.getId(), ID);
        assertEquals(returnDTO.getName(), NAME);
        assertEquals(returnDTO.getMaxPoints(), POINTS);
    }


    @DisplayName("[Happy Path], [Method] = getAllExamsForTeacher, [Expected] = List with 2 exams")
    @Test
    void getAllExamsForTeacher() {
        List<Exam> exams = getExamList(); // 1st Teacher with 2 Exams, 2nd with 1 Exam

        when(examRepository.findAll()).thenReturn(exams);

        List<ExamDTO> returnDTO = examService.getAllExamsForTeacher(ETHAN.get(), HENDERSON.get());

        assertEquals(returnDTO.size(), 2);
    }


    @DisplayName("[Happy Path], [Method] = getExamsForSubject, [Expected] = Map with a key BIOLOGY and 2 Values")
    @Test
    void getExamsForSubject() {
        List<Exam> exams = getExamList(); // 2 Biology, 1 Math

        when(examRepository.findAll()).thenReturn(exams);

        Map<String, List<ExamDTO>> returnMap = examService.getExamsForSubject(BIOLOGY); // <Biology, List<ExamDTO>>

        assertTrue(returnMap.containsKey(BIOLOGY.name()));
        assertEquals(returnMap.get(BIOLOGY.name()).size(), 2);
        assertEquals(returnMap.size(), 1);
    }

    @DisplayName("[Happy Path], [Method] = getAllExamsBySubjectsAndTeachers, [Expected] = Map with 2 keys BIOLOGY and MATHEMATICS")
    @Test
    void getAllExamsBySubjectsAndTeachers() {
        List<Exam> exams = getExamList(); // 2 Biology - 1 Teacher, 1 Math  - 1 Teacher

        when(examRepository.findAll()).thenReturn(exams);

        Map<String, Map<String, List<ExamDTO>>> returnMap = examService.getAllExamsBySubjectsAndTeachers();

        assertTrue(returnMap.containsKey(BIOLOGY.name()));
        assertTrue(returnMap.containsKey(MATHEMATICS.name()));
        assertEquals(returnMap.get(BIOLOGY.name()).size(), 1); // Its grouped by teacher that's why i except size of one
        assertEquals(returnMap.get(MATHEMATICS.name()).size(), 1);
        // Checking if Subjects are grouped correct by lastName + firstName of teacher
        assertEquals(returnMap.get(BIOLOGY.name()).get(HENDERSON.get() + " " + ETHAN.get()).size(), 2); // 2 Exams of  Biology
        assertEquals(returnMap.get(MATHEMATICS.name()).get(SMITH.get() + " " + SEBASTIAN.get()).size(), 1); // 1 Exam of  Math
    }

    @DisplayName("[Happy Path], [Method] = getAllExamsByStudentsAndSubjects, [Expected] = Map with 2 keys - '2', '3'")
    @Test
    void getAllExamsByStudentsAndSubjects() {
        List<Exam> exams = getExamList(); // 1st key - 2 Students, 2nd key - 3 students

        when(examRepository.findAll()).thenReturn(exams);

        Map<Integer, Map<String, List<ExamDTO>>> returnMap = examService.getAllExamsByStudentsAndSubjects();

        assertTrue(returnMap.containsKey(2));
        assertTrue(returnMap.containsKey(3));
        assertEquals(returnMap.get(2).get(BIOLOGY.name()).size(), 2); // Map<2, Map<Biology, List<ExamDTO>(2)>>
        assertEquals(returnMap.get(3).get(MATHEMATICS.name()).size(), 1); // Map<3, Map<MATHEMATICS, List<ExamDTO>(1)>>
    }


    @DisplayName("[Happy Path], [Method] = createNewExam, [Expected] = ExamDTO with matching fields")
    @Test
    void createNewExam() {
        ExamDTO examDTO = initBiologyExamDTO();
        Exam exam = initBiologyExam();

        when(examRepository.save(any(Exam.class))).thenReturn(exam);

        ExamDTO returnDTO = examService.createNewExam(examDTO);

        assertEquals(returnDTO.getDate(), DATE);
        assertEquals(returnDTO.getName(), NAME);
        assertEquals(returnDTO.getMaxPoints(), POINTS);
    }

    @DisplayName("[Happy Path], [Method] = updateExam, [Expected] = ExamDTO with updated fields")
    @Test
    void updateExamHappyPath() {
        ExamDTO examDTO = initBiologyExamDTO();
        Exam exam = initBiologyExam();
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
        ExamDTO examDTO = initBiologyExamDTO();

        Throwable ex = catchThrowable(() -> examService.updateExam(222L, examDTO));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }

    @DisplayName("[Happy Path], [Method] = deleteExamById, [Expected] = Repository deleting object")
    @Test
    void deleteExamByIdHappyPath() {
        Exam exam = initBiologyExam();

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