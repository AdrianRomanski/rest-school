package adrianromanski.restschool.mapper;

import adrianromanski.restschool.domain.Student;
import adrianromanski.restschool.model.StudentDTO;
import org.junit.jupiter.api.Test;

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

        //when
        StudentDTO studentDTO = studentMapper.studentToStudentDTO(student);

        assertEquals(ID, student.getId());
        assertEquals(FIRST_NAME, studentDTO.getFirstName());
        assertEquals(LAST_NAME, studentDTO.getLastName());
    }

}