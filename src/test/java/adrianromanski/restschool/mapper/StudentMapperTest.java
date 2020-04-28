package adrianromanski.restschool.mapper;

import adrianromanski.restschool.domain.base_entity.person.Student;
import adrianromanski.restschool.mapper.person.StudentMapper;
import adrianromanski.restschool.model.base_entity.person.StudentDTO;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static adrianromanski.restschool.domain.base_entity.enums.Gender.MALE;
import static org.junit.jupiter.api.Assertions.*;

class StudentMapperTest {

    public static final Long ID = 1L;
    public static final String FIRST_NAME = "Adrian";
    public static final String LAST_NAME = "Romanski";
    public static final long AGE = 27L;


    StudentMapper studentMapper = StudentMapper.INSTANCE;

    @Test
    public void studentToStudentDTO() {
        //given

        Student student = Student.builder().firstName(FIRST_NAME).lastName(LAST_NAME).gender(MALE).dateOfBirth(LocalDate.of(1992, 11, 3)).build();
        student.setId(ID);

        //when
        StudentDTO studentDTO = studentMapper.studentToStudentDTO(student);

        assertEquals(ID, studentDTO.getId());
        assertEquals(FIRST_NAME, studentDTO.getFirstName());
        assertEquals(LAST_NAME, studentDTO.getLastName());
        assertEquals(MALE, studentDTO.getGender());
        assertEquals(AGE, studentDTO.getAge());
    }

    @Test
    public void studentDTOToStudent() {
        //given
        StudentDTO studentDTO = StudentDTO.builder().firstName(FIRST_NAME).lastName(LAST_NAME).gender(MALE).dateOfBirth(LocalDate.of(1992, 11, 3)).build();
        studentDTO.setId(ID);

        //when
        Student student = studentMapper.studentDTOToStudent(studentDTO);

        assertEquals(ID, student.getId());
        assertEquals(FIRST_NAME, student.getFirstName());
        assertEquals(LAST_NAME, student.getLastName());
        assertEquals(MALE, student.getGender());
        assertEquals(AGE, student.getAge());
    }
}