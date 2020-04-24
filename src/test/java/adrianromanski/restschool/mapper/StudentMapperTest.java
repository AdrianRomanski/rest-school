package adrianromanski.restschool.mapper;

import adrianromanski.restschool.domain.base_entity.person.Student;
import adrianromanski.restschool.domain.base_entity.person.enums.Gender;
import adrianromanski.restschool.mapper.person.StudentMapper;
import adrianromanski.restschool.model.base_entity.person.StudentDTO;
import org.junit.jupiter.api.Test;

import static adrianromanski.restschool.domain.base_entity.person.enums.Gender.MALE;
import static org.junit.jupiter.api.Assertions.*;

class StudentMapperTest {

    public static final Long ID = 1L;
    public static final String FIRST_NAME = "Adrian";
    public static final String LAST_NAME = "Romanski";


    StudentMapper studentMapper = StudentMapper.INSTANCE;

    @Test
    public void studentToStudentDTO() {
        //given

        Student student = new Student();
        student.setFirstName(FIRST_NAME);
        student.setLastName(LAST_NAME);
        student.setId(ID);
        student.setGender(MALE);

        //when
        StudentDTO studentDTO = studentMapper.studentToStudentDTO(student);

        assertEquals(ID, studentDTO.getId());
        assertEquals(FIRST_NAME, studentDTO.getFirstName());
        assertEquals(LAST_NAME, studentDTO.getLastName());
        assertEquals(MALE, studentDTO.getGender());
    }

    @Test
    public void studentDTOToStudent() {
        //given

        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setFirstName(FIRST_NAME);
        studentDTO.setLastName(LAST_NAME);
        studentDTO.setId(ID);
        studentDTO.setGender(MALE);

        //when
        Student student = studentMapper.studentDTOToStudent(studentDTO);

        assertEquals(ID, student.getId());
        assertEquals(FIRST_NAME, student.getFirstName());
        assertEquals(LAST_NAME, student.getLastName());
        assertEquals(MALE, student.getGender());
    }

}