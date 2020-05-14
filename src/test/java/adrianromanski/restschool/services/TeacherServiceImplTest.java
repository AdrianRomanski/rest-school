package adrianromanski.restschool.services;

import adrianromanski.restschool.domain.enums.Subjects;
import adrianromanski.restschool.domain.event.Exam;
import adrianromanski.restschool.domain.group.StudentClass;
import adrianromanski.restschool.domain.person.Student;
import adrianromanski.restschool.domain.person.Teacher;
import adrianromanski.restschool.domain.enums.Gender;
import adrianromanski.restschool.exceptions.ResourceNotFoundException;
import adrianromanski.restschool.mapper.event.ExamMapper;
import adrianromanski.restschool.mapper.person.StudentMapper;
import adrianromanski.restschool.mapper.person.TeacherMapper;
import adrianromanski.restschool.model.event.ExamDTO;
import adrianromanski.restschool.model.person.StudentDTO;
import adrianromanski.restschool.model.person.TeacherDTO;
import adrianromanski.restschool.repositories.event.ExamRepository;
import adrianromanski.restschool.repositories.person.StudentRepository;
import adrianromanski.restschool.repositories.person.TeacherRepository;
import adrianromanski.restschool.services.person.teacher.TeacherService;
import adrianromanski.restschool.services.person.teacher.TeacherServiceImpl;
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

import static adrianromanski.restschool.domain.enums.FemaleName.*;
import static adrianromanski.restschool.domain.enums.Gender.*;
import static adrianromanski.restschool.domain.enums.LastName.*;
import static adrianromanski.restschool.domain.enums.MaleName.*;
import static adrianromanski.restschool.domain.enums.Subjects.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TeacherServiceImplTest {

    public static final long ID = 1L;
    public static final String STUDENT_CLASS_NAME = "Rookies";
    public static final LocalDate NOW = LocalDate.now();

    TeacherService teacherService;

    @Mock
    TeacherRepository teacherRepository;

    @Mock
    StudentRepository studentRepository;

    @Mock
    ExamRepository examRepository;

    Teacher createTeacher(Long id, String firstName, String lastName, Gender gender, Subjects subjects, LocalDate firstDay) {
        Teacher teacher = Teacher.builder().firstName(firstName).lastName(lastName).gender(gender).
                                        firstDay(firstDay).subject(subjects).build();
        teacher.setId(id);
        return teacher;
    }

    TeacherDTO createEthanDTO() {
        TeacherDTO teacherDTO = TeacherDTO.builder().firstName(ETHAN.get()).lastName(COOPER.get()).gender(MALE).
                                        subject(CHEMISTRY).build();
        teacherDTO.setId(ID);
        return teacherDTO;
    }

    Teacher createEthan() {
        Teacher teacher = createTeacher(ID, ETHAN.get(), COOPER.get(), MALE, CHEMISTRY, LocalDate.of(2018, 10, 3));
        StudentClass studentClass = StudentClass.builder().subject(CHEMISTRY).president(ETHAN.get()).name(STUDENT_CLASS_NAME).build();
        studentClass.getStudentList().addAll(Arrays.asList(Student.builder().firstName(SEBASTIAN.get()).build(), Student.builder().firstName(DANIEL.get()).build()));
        teacher.setStudentClass(studentClass);
        studentClass.setTeacher(teacher);
        return teacher;
    }

    Teacher createBenjamin() {
        return createTeacher(2L, BENJAMIN.get(), RODRIGUEZ.get(), MALE, BIOLOGY, LocalDate.of(2017, 10, 3));
    }

    Teacher createAria() {
        return createTeacher(3L, ARIA.get(), WILLIAMS.get(), FEMALE, PHYSICS, LocalDate.of(2017, 10, 3));
    }

    private ExamDTO createExam() {
        return ExamDTO.builder().maxPoints(100L).name("Ethan Exam").build();
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        teacherService = new TeacherServiceImpl(teacherRepository, studentRepository, examRepository,
                                            TeacherMapper.INSTANCE, ExamMapper.INSTANCE, StudentMapper.INSTANCE);
    }

    @DisplayName("[Happy Path], [Method] = getAllTeachers")
    @Test
    void getAllTeachers() {
        List<Teacher> teachers = Arrays.asList(createEthan(), createBenjamin(), createAria());

        when(teacherRepository.findAll()).thenReturn(teachers);

        List<TeacherDTO> returnTeachers = teacherService.getAllTeachers();

        assertEquals(returnTeachers.size(), teachers.size());
     }

    @DisplayName("[Happy Path], [Method] = getTeacherByFirstNameAndLastName")
    @Test
    void getTeacherByFirstNameAndLastName() {
        Teacher teacher = createEthan();

        when(teacherRepository.getTeacherByFirstNameAndLastName(anyString(), anyString())).thenReturn(Optional.of(teacher));

        TeacherDTO returnDTO = teacherService.getTeacherByFirstNameAndLastName(ETHAN.get(), COOPER.get());

        assertEquals(returnDTO.getFirstName(), ETHAN.get());
        assertEquals(returnDTO.getLastName(), COOPER.get());
        assertEquals(returnDTO.getId(), ID);
    }

    @DisplayName("[Happy Path], [Method] = getTeacherByID")
    @Test
    void getTeacherByID() {
        Teacher teacher = createEthan();

        when(teacherRepository.findById(anyLong())).thenReturn(Optional.of(teacher));

        TeacherDTO returnDTO = teacherService.getTeacherByID(ID);

        assertEquals(returnDTO.getFirstName(), ETHAN.get());
        assertEquals(returnDTO.getLastName(), COOPER.get());
        assertEquals(returnDTO.getId(), ID);
    }

    @DisplayName("[Happy Path], [Method] = getTeachersBySpecialization")
    @Test
    void getTeachersBySpecialization() {
        List<Teacher> teachers = Arrays.asList(createEthan(), createBenjamin(), createAria());

        when(teacherRepository.findAll()).thenReturn(teachers);

        Map<Subjects, List<TeacherDTO>> map = teacherService.getTeachersBySpecialization();

        assertEquals(map.size(), 3);
        assertTrue(map.containsKey(BIOLOGY));
        assertTrue(map.containsKey(PHYSICS));
        assertTrue(map.containsKey(CHEMISTRY));
    }

    @DisplayName("[Happy Path], [Method] = getTeachersByYearsOfExperience")
    @Test
    void getTeachersByYearsOfExperience() {
        List<Teacher> teachers = Arrays.asList(createEthan(), createBenjamin(), createAria());

        when(teacherRepository.findAll()).thenReturn(teachers);

        Map<Long, List<TeacherDTO>> map = teacherService.getTeachersByYearsOfExperience();

        assertEquals(map.size(), 2); // Expecting size 2 because -> (2 years exp(2 person)) + (1 year exp(1 person)) = 2
        assertTrue(map.containsKey(1L));
        assertTrue(map.containsKey(2L));
    }

    @DisplayName("[Happy Path], [Method] = addExamForClass")
    @Test
    void addExamForClass() {
        Teacher teacher = createEthan();

        ExamDTO examDTO = createExam();

        when(teacherRepository.findById(anyLong())).thenReturn(Optional.of(teacher));

        ExamDTO returnDTO = teacherService.addExamForClass(1L, examDTO);

        assertEquals(returnDTO.getStudentsDTO().size(), 2);
    }

    @DisplayName("[Unhappy Path], [Method] = addExamForClass")
    @Test
    void addExamForClassUnhappy() {
        ExamDTO examDTO = createExam();

        Throwable ex = catchThrowable(() -> teacherService.addExamForClass(222L,examDTO));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }

    @DisplayName("[Happy Path], [Method] = addCorrectionExamForStudent")
    @Test
    void addCorrectionExamForStudentHappyPath() {
        Teacher teacher = createEthan();
        Student student = Student.builder().firstName(SEBASTIAN.get()).build();
        ExamDTO examDTO = createExam();

        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student));
        when(teacherRepository.findById(anyLong())).thenReturn(Optional.of(teacher));

        ExamDTO returnDTO = teacherService.addCorrectionExamForStudent(1L, 1L, examDTO);

        assertEquals(returnDTO.getStudentsDTO().size(), 1);
        assertEquals(returnDTO.getTeacherDTO().getFirstName(), ETHAN.get());
    }

    @DisplayName("[UnHappy Path], [Method] = addCorrectionExamForStudent, [Reason] = Teacher not found")
    @Test
    void addCorrectionExamForStudentUnHappyPathTeacher() {
        ExamDTO examDTO = createExam();

        Throwable ex = catchThrowable(() -> teacherService.addCorrectionExamForStudent(22L, 22L, examDTO));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }

    @DisplayName("[UnHappy Path], [Method] = addCorrectionExamForStudent, [Reason] = Student not found")
    @Test
    void addCorrectionExamForStudentUnHappyPathStudent() {
        ExamDTO examDTO = createExam();
        Teacher teacher = createEthan();

        when(teacherRepository.findById(anyLong())).thenReturn(Optional.of(teacher));
        Throwable ex = catchThrowable(() -> teacherService.addCorrectionExamForStudent(22L, -5L, examDTO));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }

    @DisplayName("[Happy Path], [Method] = addNewStudentToClass")
    @Test
    void addNewStudentToClassHappyPath() {
        StudentDTO studentDTO = createStudentDTO();
        Teacher teacher = createEthan();

        when(teacherRepository.findById(anyLong())).thenReturn(Optional.of(teacher));

        StudentDTO returnDTO = teacherService.addNewStudentToClass(1L,studentDTO);

        assertEquals(returnDTO.getStudentClassDTO().getName(), STUDENT_CLASS_NAME);
    }

    @DisplayName("[Unhappy Path], [Method] = addNewStudentToClass")
    @Test
    void addNewStudentToClassUnhappyPath() {
        StudentDTO studentDTO = createStudentDTO();

        Throwable ex = catchThrowable(() -> teacherService.addNewStudentToClass(1L, studentDTO));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }

    private StudentDTO createStudentDTO() {
        return StudentDTO.builder().firstName(ETHAN.get()).build();
    }


    @DisplayName("[Happy Path], [Method] = createNewTeacher")
    @Test
    void createNewTeacher() {
        TeacherDTO teacherDTO = createEthanDTO();
        Teacher teacher = createEthan();

        when(teacherRepository.save(any(Teacher.class))).thenReturn(teacher);

        TeacherDTO returnDTO = teacherService.createNewTeacher(teacherDTO);

        assertEquals(returnDTO.getId(), ID);
        assertEquals(returnDTO.getFirstName(), ETHAN.get());
        assertEquals(returnDTO.getLastName(), COOPER.get());
    }

    @DisplayName("[Happy Path], [Method] = moveExamToAnotherDay")
    @Test
    void moveExamToAnotherDayHappyPath() {
        Exam exam = Exam.builder().date(NOW).build();
        exam.setId(1L);
        Teacher teacher = createEthan();
        teacher.getExams().add(exam);

        when(teacherRepository.findById(anyLong())).thenReturn(Optional.of(teacher));

        ExamDTO returnDTO = teacherService.moveExamToAnotherDay(teacher.getId(), 0, LocalDate.of(2020, 10,10));

        assertEquals(returnDTO.getDate(), LocalDate.of(2020, 10,10));
    }

    @DisplayName("[Unhappy Path], [Method] = moveExamToAnotherDay")
    @Test
    void moveExamToAnotherDayUnhappyPath() {

          Throwable ex = catchThrowable(() -> teacherService.moveExamToAnotherDay(0L, 1, LocalDate.of(2020, 10,10)));

          assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }

    @DisplayName("[Happy Path], [Method] = changeClassPresident")
    @Test
    void changeClassPresidentHappyPath() {
         Teacher teacher = createEthan();
         Student student = Student.builder().firstName(ISAAC.get()).lastName(HENDERSON.get()).build();

         when(teacherRepository.findById(anyLong())).thenReturn(Optional.of(teacher));
         when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student));

         TeacherDTO returnDTO = teacherService.changeClassPresident(1L, 1L);

         assertEquals(returnDTO.getStudentClassDTO().getPresident(), "Isaac Henderson");
    }

    @DisplayName("[Unhappy Path], [Method] = changeClassPresident")
    @Test
    void changeClassPresidentUnhappyPathTeacher()  {

        Throwable ex = catchThrowable(() -> teacherService.changeClassPresident(1L, 1L));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }

    @DisplayName("[Unhappy Path], [Method] = changeClassPresident")
    @Test
    void changeClassPresidentUnhappyPathStudent() {
        Teacher teacher = createEthan();

        when(teacherRepository.findById(anyLong())).thenReturn(Optional.of(teacher));

        Throwable ex = catchThrowable(() -> teacherService.changeClassPresident(1L, 1L));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }




    @DisplayName("[Happy Path], [Method] = updateTeacher")
    @Test
    void updateTeacher() {
        TeacherDTO teacherDTO = createEthanDTO();
        Teacher teacher = createEthan();

        when(teacherRepository.findById(ID)).thenReturn(Optional.of(teacher));
        when(teacherRepository.save(any(Teacher.class))).thenReturn(teacher);

        TeacherDTO returnDTO = teacherService.updateTeacher(ID, teacherDTO);

        assertEquals(returnDTO.getId(), ID);
        assertEquals(returnDTO.getFirstName(), ETHAN.get());
        assertEquals(returnDTO.getLastName(), COOPER.get());
    }

    @DisplayName("[Unhappy Path], [Method] = updateTeacher, [Reason] = Teacher with id 222 not found")
    @Test
    void updateTeacherUnHappyPath() {
        TeacherDTO teacherDTO = createEthanDTO();

        Throwable ex = catchThrowable(() -> teacherService.updateTeacher(222L,teacherDTO));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }

    @DisplayName("[Happy Path], [Method] = deleteTeacherById")
    @Test
    void deleteTeacherByIdHappyPath() {
        Teacher teacher = createEthan();

        when(teacherRepository.findById(ID)).thenReturn(Optional.of(teacher));

        teacherService.deleteTeacherById(ID);
   }

    @DisplayName("[Unhappy Path], [Method] = deleteTeacherById, [Reason] = Teacher with id 222 not found")
    @Test
    void deleteTeacherByIDUnHappyPath() {

        Throwable ex = catchThrowable(() -> teacherService.deleteTeacherById(222L));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }

    @DisplayName("[Happy Path], [Method] = removeStudentFromClass")
    @Test
    void removeStudentFromClass() {
      Teacher teacher = createEthan();  // This one comes with Student Class that have 2 students
      Student student = Student.builder().firstName(ISAAC.get()).lastName(HENDERSON.get()).build();
      teacher.getStudentClass().getStudentList().add(student);

      when(teacherRepository.findById(anyLong())).thenReturn(Optional.of(teacher));
      when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student));

      teacherService.removeStudentFromClass(1L, 1L);

      assertEquals(teacher.getStudentClass().getStudentList().size(), 2);   // That's why im excepting 2 here
    }

    @DisplayName("[Unhappy Path], [Method] = deleteTeacherById, [Reason] = Teacher with id 1 not found")
    @Test
    void removeStudentFromClassUnHappyPathTeacher()  {

        Throwable ex = catchThrowable(() -> teacherService.removeStudentFromClass(1L, 1L));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }

    @DisplayName("[Unhappy Path], [Method] = deleteTeacherById, [Reason] = Student with id 1 not found")
    @Test
    void removeStudentFromClassUnHappyPathStudent() {
        Teacher teacher = createEthan();

        when(teacherRepository.findById(anyLong())).thenReturn(Optional.of(teacher));

        Throwable ex = catchThrowable(() -> teacherService.removeStudentFromClass(1L, 1L));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }
}