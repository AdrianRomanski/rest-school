package adrianromanski.restschool.services;

import adrianromanski.restschool.domain.base_entity.group.StudentClass;
import adrianromanski.restschool.domain.base_entity.person.Teacher;
import adrianromanski.restschool.mapper.group.StudentClassMapper;
import adrianromanski.restschool.mapper.person.TeacherMapper;
import adrianromanski.restschool.model.base_entity.group.StudentClassDTO;
import adrianromanski.restschool.model.base_entity.person.TeacherDTO;
import adrianromanski.restschool.repositories.group.StudentClassRepository;
import adrianromanski.restschool.services.group.student_class.StudentClassService;
import adrianromanski.restschool.services.group.student_class.StudentClassServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


class StudentClassServiceImplTest {


    public static final long ID = 1L;
    public static final String ROOKIES = "Rookies";
    public static final String WALTER = "Walter";
    public static final String WHITE = "White";
    StudentClassService studentClassService;

    @Mock
    StudentClassRepository studentClassRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        studentClassService = new StudentClassServiceImpl(studentClassRepository,
                                                            StudentClassMapper.INSTANCE, TeacherMapper.INSTANCE);
    }

    private StudentClass getStudentClass() {
        StudentClass studentClass = new StudentClass();
        studentClass.setId(ID);
        studentClass.setName(ROOKIES);
        return studentClass;
    }

    private StudentClassDTO getStudentClassDTO() {
        StudentClassDTO studentClassDTO = new StudentClassDTO();
        studentClassDTO.setId(ID);
        studentClassDTO.setName(ROOKIES);
        return studentClassDTO;
    }

        @Test
        void getAllStudentClasses() {
        List<StudentClass> studentClasses = Arrays.asList(new StudentClass(), new StudentClass(), new StudentClass());

        when(studentClassRepository.findAll()).thenReturn(studentClasses);

        List<StudentClassDTO> studentClassDTOS = studentClassService.getAllStudentClasses();

        assertEquals(studentClassDTOS.size(), studentClasses.size());
    }

    @Test
    void getStudentClassByID() {
        StudentClass studentClass = getStudentClass();

        when(studentClassRepository.findById(ID)).thenReturn(Optional.of(studentClass));

        StudentClassDTO studentClassDTO = studentClassService.getStudentClassByID(ID);

        assertEquals(studentClassDTO.getName(), ROOKIES);
        assertEquals(studentClassDTO.getId(), ID);
    }

    @Test
    void createNewStudentClass() {
       StudentClassDTO studentClassDTO = getStudentClassDTO();
       StudentClass savedStudent = getStudentClass();

       when(studentClassRepository.save(any(StudentClass.class))).thenReturn(savedStudent);

       StudentClassDTO returnDTO = studentClassService.createNewStudentClass(studentClassDTO);

       assertEquals(returnDTO.getName(), ROOKIES);
       assertEquals(returnDTO.getId(), ID);
    }

    @Test
    void updateStudentClass() {
        StudentClassDTO studentClassDTO = getStudentClassDTO();
        StudentClass updatedStudent = getStudentClass();

        when(studentClassRepository.save(any(StudentClass.class))).thenReturn(updatedStudent);

        StudentClassDTO returnDTO = studentClassService.updateStudentClass(ID, studentClassDTO);

        assertEquals(returnDTO.getName(), ROOKIES);
        assertEquals(returnDTO.getId(), ID);
    }

    @Test
    void getStudentClassTeacherPositiveScenario() {
        StudentClass studentClass = getStudentClass();
        Teacher teacher = new Teacher();
        teacher.setFirstName(WALTER);
        teacher.setLastName(WHITE);
        studentClass.setTeacher(teacher);

        StudentClassDTO studentClassDTO = getStudentClassDTO();
        TeacherDTO teacherDTO = new TeacherDTO();
        teacherDTO.setFirstName(WALTER);
        teacherDTO.setLastName(WHITE);
        studentClassDTO.setTeacherDTO(teacherDTO);

        when(studentClassRepository.getTeacher()).thenReturn(Optional.of(teacher));

        TeacherDTO returnTeacherDTO = studentClassService.getStudentClassTeacher(studentClassDTO);

        assertEquals(returnTeacherDTO.getFirstName(), WALTER);
        assertEquals(returnTeacherDTO.getLastName(), WHITE);
    }

    @Test
    void deleteStudentClassById() {
        studentClassRepository.deleteById(ID);
        verify(studentClassRepository, times(1)).deleteById(ID);
    }
}