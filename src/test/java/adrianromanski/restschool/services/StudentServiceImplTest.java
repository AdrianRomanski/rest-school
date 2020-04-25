package adrianromanski.restschool.services;

import adrianromanski.restschool.domain.base_entity.person.Student;
import adrianromanski.restschool.domain.base_entity.person.enums.Gender;
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

import static adrianromanski.restschool.domain.base_entity.person.enums.Gender.FEMALE;
import static adrianromanski.restschool.domain.base_entity.person.enums.Gender.MALE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class StudentServiceImplTest {

    public static final Long ID = 1L;
    public static final String FIRST_NAME = "Aaron";
    public static final String LAST_NAME = "Smith";

    StudentService studentService;

    @Mock
    StudentRepository studentRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        studentService = new StudentServiceImpl(StudentMapper.INSTANCE, studentRepository);
    }

    Student createStudent(Long id, String firstName, String lastName, Gender gender) {
        Student student = Student.builder().firstName(firstName).lastName(lastName).gender(gender).build();
        student.setId(id);
        return student;
    }

    Student createAaronSmith() {
        return createStudent(ID,FIRST_NAME, LAST_NAME, MALE);
    }

    Student createJessiePinkman() {
        return createStudent(2L, "Jessie", "Pinkman", MALE);
    }

    Student createJessicaParker() {
        return createStudent(3L, "Jessica", "Parker", FEMALE);
    }

    private StudentDTO createAaronSmithDTO() {
        StudentDTO studentDTO = StudentDTO.builder().firstName(FIRST_NAME).lastName(LAST_NAME).gender(MALE).build();
        studentDTO.setId(ID);
        return studentDTO;
    }

    @DisplayName("[Happy Path], [Method] = getAllStudents, [Expected] = List containing 3 Students")
    @Test
    void getAllStudents() {
        List<Student> students = Arrays.asList(createAaronSmith(), createJessiePinkman(), createJessicaParker());

        when(studentRepository.findAll()).thenReturn(students);

        List<StudentDTO> studentDTOS = studentService.getAllStudents();

        assertEquals(3, studentDTOS.size());
    }

    @DisplayName("[Happy Path], [Method] = getAllFemaleStudents, [Expected] = List containing 1 Student")
    @Test
    void getAllFemaleStudents() {
        List<Student> students = Arrays.asList(createAaronSmith(), createJessiePinkman(), createJessicaParker());

        when(studentRepository.findAll()).thenReturn(students);

        List<StudentDTO> studentDTOS = studentService.getAllFemaleStudents();

        assertEquals(1, studentDTOS.size());
    }

    @DisplayName("[Happy Path], [Method] = getAllMaleStudents, [Expected] = List containing 2 Students")
    @Test
    void getAllMaleStudents() {
        List<Student> students = Arrays.asList(createAaronSmith(), createJessiePinkman(), createJessicaParker());

        when(studentRepository.findAll()).thenReturn(students);

        List<StudentDTO> studentDTOS = studentService.getAllMaleStudents();

        assertEquals(2, studentDTOS.size());
    }

    @DisplayName("[Happy Path], [Method] = getStudentByFirstAndLastName, [Expected] = StudentDTO with matching fields")
    @Test
    void getStudentByFirstAndLastName() {
        Student student = createAaronSmith();

        when(studentRepository.findByFirstNameAndLastName(anyString(), anyString())).thenReturn(Optional.of(student));

        StudentDTO studentDTO = studentService.getStudentByFirstAndLastName(FIRST_NAME, LAST_NAME);

        assertEquals(FIRST_NAME, studentDTO.getFirstName());
        assertEquals(LAST_NAME, studentDTO.getLastName());
        assertEquals(MALE, studentDTO.getGender());
        assertEquals(ID, studentDTO.getId());
    }

    @DisplayName("[Happy Path], [Method] = getStudentById, [Expected] = StudentDTO with matching fields")
    @Test
    void getStudentById() {
        Student student = createAaronSmith();

        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student));

        StudentDTO studentDTO = studentService.getStudentByID(ID);

        assertEquals(FIRST_NAME, studentDTO.getFirstName());
        assertEquals(LAST_NAME, studentDTO.getLastName());
        assertEquals(MALE, studentDTO.getGender());
        assertEquals(ID, studentDTO.getId());
    }

    @DisplayName("[Happy Path], [Method] = createNewStudent, [Expected] = StudentDTO  with matching fields")
    @Test
    void createNewStudent() throws Exception {
        StudentDTO studentDTO = createAaronSmithDTO();
        Student savedStudent =  createAaronSmith();

        when(studentRepository.save(any(Student.class))).thenReturn(savedStudent);

        StudentDTO returnDTO = studentService.createNewStudent(studentDTO);

        assertEquals(FIRST_NAME, returnDTO.getFirstName());
        assertEquals(LAST_NAME, returnDTO.getLastName());
        assertEquals(MALE, returnDTO.getGender());
        assertEquals(ID, returnDTO.getId());
    }

    @DisplayName("[Happy Path], [Method] = updateStudent, [Expected] = StudentDTO with updated fields")
    @Test
    void updateStudentHappyPath() {
        StudentDTO studentDTO = createAaronSmithDTO();
        Student savedStudent =  createAaronSmith();

        when(studentRepository.findById(ID)).thenReturn(Optional.of(savedStudent));
        when(studentRepository.save(any(Student.class))).thenReturn(savedStudent);

        StudentDTO returnDTO = studentService.updateStudent(ID, studentDTO);

        assertEquals(FIRST_NAME, returnDTO.getFirstName());
        assertEquals(LAST_NAME, returnDTO.getLastName());
        assertEquals(MALE, returnDTO.getGender());
        assertEquals(ID, returnDTO.getId());
    }

    @DisplayName("[Unhappy Path], [Method] = updateStudent, [Reason] = Student with id 222 not found")
    @Test
    void updateStudentUnHappyPath() {
        StudentDTO studentDTO = createAaronSmithDTO();

            Throwable ex = catchThrowable(() -> studentService.updateStudent(222L,studentDTO));

            assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }

    @DisplayName("[Happy Path], [Method] = deleteStudentByID, [Expected] = studentRepository deleting student")
    @Test
    void deleteStudentByID() {
        Student student =  createAaronSmith();

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