package adrianromanski.restschool.services;

import adrianromanski.restschool.domain.base_entity.person.Student;
import adrianromanski.restschool.domain.base_entity.enums.Gender;
import adrianromanski.restschool.exceptions.ResourceNotFoundException;
import adrianromanski.restschool.mapper.person.StudentMapper;
import adrianromanski.restschool.model.base_entity.person.StudentDTO;
import adrianromanski.restschool.repositories.person.StudentRepository;
import adrianromanski.restschool.services.person.student.StudentService;
import adrianromanski.restschool.services.person.student.StudentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static adrianromanski.restschool.domain.base_entity.enums.FemaleName.*;
import static adrianromanski.restschool.domain.base_entity.enums.Gender.FEMALE;
import static adrianromanski.restschool.domain.base_entity.enums.Gender.MALE;
import static adrianromanski.restschool.domain.base_entity.enums.LastName.*;
import static adrianromanski.restschool.domain.base_entity.enums.MaleName.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class StudentServiceImplTest {

    public static final Long ID = 1L;

    StudentService studentService;

    @Mock
    StudentRepository studentRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        studentService = new StudentServiceImpl(StudentMapper.INSTANCE, studentRepository);
    }

    private Student createStudent(Long id, String firstName, String lastName, Gender gender) {
        Student student = Student.builder().firstName(firstName).lastName(lastName).gender(gender).build();
        student.setId(id);
        return student;
    }

    private StudentDTO createEthanDTO() {
        StudentDTO studentDTO = StudentDTO.builder().firstName(ETHAN.get()).lastName(COOPER.get()).gender(MALE).build();
        studentDTO.setId(ID);
        return studentDTO;
    }

    private Student createEthan() {
        return createStudent(ID, ETHAN.get(), COOPER.get(), MALE);
    }

    private Student createSebastian() {
        return createStudent(2L, SEBASTIAN.get(), RODRIGUEZ.get(), MALE);
    }

    private Student createCharlotte() {
        return createStudent(3L, CHARLOTTE.get(), HENDERSON.get(), FEMALE);
    }



    private List<Student> getStudents() {
        return Arrays.asList(createEthan(), createSebastian(), createCharlotte());
    }

    @DisplayName("[Happy Path], [Method] = getAllStudents")
    @Test
    void getAllStudents() {
        List<Student> students = getStudents();

        when(studentRepository.findAll()).thenReturn(students);

        List<StudentDTO> studentDTOS = studentService.getAllStudents();

        assertEquals(3, studentDTOS.size());
    }

    @DisplayName("[Happy Path], [Method] = getAllFemaleStudents")
    @Test
    void getAllFemaleStudents() {
        List<Student> students = getStudents();

        when(studentRepository.findAll()).thenReturn(students);

        List<StudentDTO> studentDTOS = studentService.getAllFemaleStudents();

        assertEquals(1, studentDTOS.size());
    }

    @DisplayName("[Happy Path], [Method] = getAllMaleStudents")
    @Test
    void getAllMaleStudents() {
        List<Student> students = getStudents();

        when(studentRepository.findAll()).thenReturn(students);

        List<StudentDTO> studentDTOS = studentService.getAllMaleStudents();

        assertEquals(2, studentDTOS.size());
    }

    @DisplayName("[Happy Path], [Method] = getStudentByFirstAndLastName")
    @Test
    void getStudentByFirstAndLastName() {
        Student student = createEthan();

        when(studentRepository.findByFirstNameAndLastName(anyString(), anyString())).thenReturn(Optional.of(student));

        StudentDTO studentDTO = studentService.getStudentByFirstAndLastName(ETHAN.get(), COOPER.get());

        assertEquals(ETHAN.get(), studentDTO.getFirstName());
        assertEquals(COOPER.get(), studentDTO.getLastName());
        assertEquals(MALE, studentDTO.getGender());
        assertEquals(ID, studentDTO.getId());
    }

    @DisplayName("[Happy Path], [Method] = getStudentById")
    @Test
    void getStudentById() {
        Student student = createEthan();

        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student));

        StudentDTO studentDTO = studentService.getStudentByID(ID);

        assertEquals(ETHAN.get(), studentDTO.getFirstName());
        assertEquals(COOPER.get(), studentDTO.getLastName());
        assertEquals(MALE, studentDTO.getGender());
        assertEquals(ID, studentDTO.getId());
    }

    @DisplayName("[Happy Path], [Method] = createNewStudent")
    @Test
    void createNewStudent() throws Exception {
        StudentDTO studentDTO = createEthanDTO();
        Student savedStudent =  createEthan();

        when(studentRepository.save(any(Student.class))).thenReturn(savedStudent);

        StudentDTO returnDTO = studentService.createNewStudent(studentDTO);

        assertEquals(ETHAN.get(), returnDTO.getFirstName());
        assertEquals(COOPER.get(), returnDTO.getLastName());
        assertEquals(MALE, returnDTO.getGender());
        assertEquals(ID, returnDTO.getId());
    }

    @DisplayName("[Happy Path], [Method] = updateStudent")
    @Test
    void updateStudentHappyPath() {
        StudentDTO studentDTO = createEthanDTO();
        Student savedStudent =  createEthan();

        when(studentRepository.findById(ID)).thenReturn(Optional.of(savedStudent));
        when(studentRepository.save(any(Student.class))).thenReturn(savedStudent);

        StudentDTO returnDTO = studentService.updateStudent(ID, studentDTO);

        assertEquals(ETHAN.get(), returnDTO.getFirstName());
        assertEquals(COOPER.get(), returnDTO.getLastName());
        assertEquals(MALE, returnDTO.getGender());
        assertEquals(ID, returnDTO.getId());
    }

    @DisplayName("[Unhappy Path], [Method] = updateStudent, [Reason] = Student with id 222 not found")
    @Test
    void updateStudentUnHappyPath() {
        StudentDTO studentDTO = createEthanDTO();

            Throwable ex = catchThrowable(() -> studentService.updateStudent(222L,studentDTO));

            assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }

    @DisplayName("[Happy Path], [Method] = deleteStudentByID")
    @Test
    void deleteStudentByID() {
        Student student =  createEthan();

        when(studentRepository.findById(ID)).thenReturn(Optional.of(student));

        studentService.deleteStudentByID(ID);

        verify(studentRepository, times(1)).deleteById(anyLong());
    }

    @DisplayName("[Unhappy Path], [Method] = deleteStudentByID, [Reason] = Student with id 222 not found")
    @Test
    void deleteStudentByIDUnHappyPath() {

        Throwable ex = catchThrowable(() -> studentService.deleteStudentByID(222L));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }

}