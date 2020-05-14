package adrianromanski.restschool.mapper;

import adrianromanski.restschool.domain.group.StudentClass;
import adrianromanski.restschool.domain.person.Student;
import adrianromanski.restschool.domain.person.Teacher;
import adrianromanski.restschool.mapper.group.StudentClassMapper;
import adrianromanski.restschool.model.group.StudentClassDTO;
import adrianromanski.restschool.model.person.StudentDTO;
import adrianromanski.restschool.model.person.TeacherDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentClassMapperTest {

    public static final long ID = 1L;
    public static final String GEEKS = "Geeks";
    public static final String OBI_WAN = "Obi Wan";
    public static final String ADRIAN = "Adrian";
    public static final String ROMANSKI = "Romanski";
    public static final String YODA = "Yoda";
    StudentClassMapper studentClassMapper = StudentClassMapper.INSTANCE;


    @Test
    void studentClassToStudentClassDTO() {
        StudentClass studentClass = new StudentClass();
        studentClass.setId(ID);
        studentClass.setName(GEEKS);
        studentClass.setPresident(OBI_WAN);

        Teacher teacher = new Teacher();
        teacher.setFirstName(YODA);

        studentClass.setTeacher(teacher);

        Student student = new Student();
        student.setFirstName(ADRIAN);
        student.setLastName(ROMANSKI);
        student.setId(ID);

        studentClass.getStudentList().add(student);


        StudentClassDTO studentClassDTO = studentClassMapper.StudentClassToStudentClassDTO(studentClass);


        // Checking proper mapping of StudentClass
        assertEquals(studentClassDTO.getName(), GEEKS);
        assertEquals(studentClassDTO.getPresident(), OBI_WAN);
        assertEquals(studentClassDTO.getTeacherDTO().getFirstName(), YODA);

        // Checking proper mapping of Student Object
        assertEquals(studentClassDTO.getStudentDTOList().get(0).getFirstName(), ADRIAN);
        assertEquals(studentClassDTO.getStudentDTOList().get(0).getLastName(), ROMANSKI);
    }

    @Test
    void studentClassDTOToStudentClass() {
        StudentClassDTO studentClassDTO = new StudentClassDTO();
        studentClassDTO.setId(ID);
        studentClassDTO.setName(GEEKS);
        studentClassDTO.setPresident(OBI_WAN);

        TeacherDTO teacherDTO = new TeacherDTO();
        teacherDTO.setFirstName(YODA);

        studentClassDTO.setTeacherDTO(teacherDTO);

        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setFirstName(ADRIAN);
        studentDTO.setLastName(ROMANSKI);
        studentDTO.setId(ID);

        studentClassDTO.getStudentDTOList().add(studentDTO);


        StudentClass studentClass = studentClassMapper.StudentClassDTOToStudentClass(studentClassDTO);


        // Checking proper mapping of StudentClass
        assertEquals(studentClass.getName(), GEEKS);
        assertEquals(studentClass.getPresident(), OBI_WAN);
        assertEquals(studentClass.getTeacher().getFirstName(), YODA);

        // Checking proper mapping of Student Object
        assertEquals(studentClass.getStudentList().get(0).getFirstName(), ADRIAN);
        assertEquals(studentClass.getStudentList().get(0).getLastName(), ROMANSKI);
    }
}