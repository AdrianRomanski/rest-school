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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class StudentServiceImplTest {

    public static final Long ID = 1L;
    public static final String FIRST_NAME = "Adrian";
    public static final String LAST_NAME = "Romanski";

    StudentService studentService;


    @Mock
    StudentRepository studentRepository;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        studentService = new StudentServiceImpl(StudentMapper.INSTANCE, studentRepository);
    }

    @Test
    void getAllStudents() {

        //given
        List<Student> students = Arrays.asList(new Student(), new Student(), new Student());

        when(studentRepository.findAll()).thenReturn(students);

        //when
        List<StudentDTO> studentDTOS = studentService.getAllStudents();

        //then
        assertEquals(3, studentDTOS.size());
    }

    @Test
    void getStudentByFirstAndLastName() {

        //Given
        Student student = new Student();
        student.setFirstName(FIRST_NAME);
        student.setLastName(LAST_NAME);

        when(studentRepository.findByFirstNameAndLastName(anyString(), anyString())).thenReturn(student);

        //when
        StudentDTO studentDTO = studentService.getStudentByFirstAndLastName(FIRST_NAME, LAST_NAME);

        //then
        assertEquals(FIRST_NAME, studentDTO.getFirstName());
        assertEquals(LAST_NAME, studentDTO.getLastName());
    }

    @Test
    void getStudentById() {
        Student student = new Student();
        student.setFirstName(FIRST_NAME);
        student.setLastName(LAST_NAME);
        student.setId(ID);

        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student));

        //when
        StudentDTO studentDTO = studentService.getStudentByID(ID);

        assertEquals(FIRST_NAME, studentDTO.getFirstName());
        assertEquals(LAST_NAME, studentDTO.getLastName());
    }


    @Test
    void createNewStudent() throws Exception {
        //given
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setFirstName(FIRST_NAME);
        studentDTO.setLastName(LAST_NAME);

        Student savedStudent = new Student();
        savedStudent.setFirstName(studentDTO.getFirstName());
        savedStudent.setLastName(studentDTO.getLastName());
        savedStudent.setId(ID);

        when(studentRepository.save(any(Student.class))).thenReturn(savedStudent);

        //when
        StudentDTO savedDTO = studentService.createNewStudent(studentDTO);

        //then
        assertEquals(studentDTO.getFirstName(), savedDTO.getFirstName());
        assertEquals(studentDTO.getLastName(), savedDTO.getLastName());
    }

    @Test
    void updateStudent() {
        //given
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setFirstName(FIRST_NAME);
        studentDTO.setLastName(LAST_NAME);

        Student savedStudent = new Student();
        savedStudent.setFirstName(studentDTO.getFirstName());
        savedStudent.setLastName(studentDTO.getLastName());
        savedStudent.setId(ID);

        when(studentRepository.save(any(Student.class))).thenReturn(savedStudent);

        //when
        StudentDTO savedDTO = studentService.updateStudent(ID, studentDTO);

        //then
        assertEquals(studentDTO.getFirstName(), savedDTO.getFirstName());
        assertEquals(studentDTO.getLastName(), savedDTO.getLastName());
    }

    @Test
    void deleteStudentByID() {
        studentRepository.deleteById(ID);
        verify(studentRepository, times(1)).deleteById(anyLong());
    }
}