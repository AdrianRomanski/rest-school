package adrianromanski.restschool.services;

import adrianromanski.restschool.domain.base_entity.person.Student;
import adrianromanski.restschool.mapper.person.StudentMapper;
import adrianromanski.restschool.model.base_entity.person.StudentDTO;
import adrianromanski.restschool.repositories.person.StudentRepository;
import adrianromanski.restschool.services.person.student.StudentService;
import adrianromanski.restschool.services.person.student.StudentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static adrianromanski.restschool.domain.base_entity.person.enums.Gender.FEMALE;
import static adrianromanski.restschool.domain.base_entity.person.enums.Gender.MALE;
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

    Student initStudent1() {
        Student student = Student.builder().firstName(FIRST_NAME).lastName(LAST_NAME).gender(MALE).build();
        student.setId(ID);
        return student;
    }

    Student initStudent2() {
        Student student = Student.builder().firstName("Jessie").lastName("Pinkman").gender(MALE).build();
        student.setId(2L);
        return student;
    }

    Student initStudent3() {
        Student student = Student.builder().firstName("Jessica").lastName("Parker").gender(FEMALE).build();
        student.setId(3L);
        return student;
    }

    private StudentDTO initStudentDTO() {
        StudentDTO studentDTO = StudentDTO.builder().firstName(FIRST_NAME).lastName(LAST_NAME).gender(MALE).build();
        studentDTO.setId(ID);
        return studentDTO;
    }

    @Test
    void getAllStudents() {
        //given
        List<Student> students = Arrays.asList(initStudent1(), initStudent2(), initStudent3());

        when(studentRepository.findAll()).thenReturn(students);

        //when
        List<StudentDTO> studentDTOS = studentService.getAllStudents();

        //then
        assertEquals(3, studentDTOS.size());
    }

    @Test
    void getAllFemaleStudents() {
        //given
        List<Student> students = Arrays.asList(initStudent1(), initStudent2(), initStudent3());

        when(studentRepository.findAll()).thenReturn(students);

        //when
        List<StudentDTO> studentDTOS = studentService.getAllFemaleStudents();

        //then
        assertEquals(1, studentDTOS.size());
    }

    @Test
    void getAllMaleStudents() {
        //given
        List<Student> students = Arrays.asList(initStudent1(), initStudent2(), initStudent3());

        when(studentRepository.findAll()).thenReturn(students);

        //when
        List<StudentDTO> studentDTOS = studentService.getAllMaleStudents();

        //then
        assertEquals(2, studentDTOS.size());
    }

    @Test
    void getStudentByFirstAndLastName() {
        //Given
        Student student = initStudent1();

        when(studentRepository.findByFirstNameAndLastName(anyString(), anyString())).thenReturn(Optional.of(student));

        //when
        StudentDTO studentDTO = studentService.getStudentByFirstAndLastName(FIRST_NAME, LAST_NAME);

        //then
        assertEquals(FIRST_NAME, studentDTO.getFirstName());
        assertEquals(LAST_NAME, studentDTO.getLastName());
        assertEquals(MALE, studentDTO.getGender());
        assertEquals(ID, studentDTO.getId());
    }

    @Test
    void getStudentById() {
        //given
       Student student = initStudent1();

        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student));

        //when
        StudentDTO studentDTO = studentService.getStudentByID(ID);

        //then
        assertEquals(FIRST_NAME, studentDTO.getFirstName());
        assertEquals(LAST_NAME, studentDTO.getLastName());
        assertEquals(MALE, studentDTO.getGender());
        assertEquals(ID, studentDTO.getId());
    }

    @Test
    void createNewStudent() throws Exception {
        //given
        StudentDTO studentDTO = initStudentDTO();
        Student savedStudent = initStudent1();

        when(studentRepository.save(any(Student.class))).thenReturn(savedStudent);

        //when
        StudentDTO returnDTO = studentService.createNewStudent(studentDTO);

        //then
        assertEquals(FIRST_NAME, returnDTO.getFirstName());
        assertEquals(LAST_NAME, returnDTO.getLastName());
        assertEquals(MALE, returnDTO.getGender());
        assertEquals(ID, returnDTO.getId());
    }

    @Test
    void updateStudent() {
        //given
        StudentDTO studentDTO = initStudentDTO();
        Student savedStudent = initStudent1();

        when(studentRepository.findById(ID)).thenReturn(Optional.of(savedStudent));

        when(studentRepository.save(any(Student.class))).thenReturn(savedStudent);

        //when
        StudentDTO returnDTO = studentService.updateStudent(ID, studentDTO);

        //then
        assertEquals(FIRST_NAME, returnDTO.getFirstName());
        assertEquals(LAST_NAME, returnDTO.getLastName());
        assertEquals(MALE, returnDTO.getGender());
        assertEquals(ID, returnDTO.getId());
    }

    @Test
    void deleteStudentByID() {
        //given
        Student student = initStudent1();

        when(studentRepository.findById(ID)).thenReturn(Optional.of(student));

        //when
        studentService.deleteStudentByID(ID);

        verify(studentRepository, times(1)).deleteById(anyLong());
    }
}