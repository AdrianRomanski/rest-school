package adrianromanski.restschool.services;

import adrianromanski.restschool.domain.base_entity.person.Teacher;
import adrianromanski.restschool.mapper.person.TeacherMapper;
import adrianromanski.restschool.model.base_entity.person.TeacherDTO;
import adrianromanski.restschool.repositories.person.TeacherRepository;
import adrianromanski.restschool.services.person.teacher.TeacherService;
import adrianromanski.restschool.services.person.teacher.TeacherServiceImpl;
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

class TeacherServiceImplTest {

    public static final String FIRST_NAME = "Walter";
    public static final String LAST_NAME = "White";
    public static final long ID = 1L;
    TeacherService teacherService;

    @Mock
    TeacherRepository teacherRepository;

    Teacher initTeacher() {
        Teacher teacher = new Teacher();
        teacher.setFirstName(FIRST_NAME);
        teacher.setLastName(LAST_NAME);
        teacher.setId(ID);
        return teacher;
    }


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        teacherService = new TeacherServiceImpl(teacherRepository, TeacherMapper.INSTANCE);
    }

    @Test
    void getAllTeachers() {
        List<Teacher> teachers = Arrays.asList(new Teacher(), new Teacher(), new Teacher());

        when(teacherRepository.findAll()).thenReturn(teachers);

        List<TeacherDTO> returnTeachers = teacherService.getAllTeachers();

        assertEquals(returnTeachers.size(), teachers.size());
     }

    @Test
    void getTeacherByFirstNameAndLastName() {
        Teacher teacher = initTeacher();

        when(teacherRepository.getTeacherByFirstNameAndLastName(FIRST_NAME, LAST_NAME)).thenReturn(teacher);

        TeacherDTO returnDTO = teacherService.getTeacherByFirstNameAndLastName(FIRST_NAME, LAST_NAME);

        assertEquals(returnDTO.getFirstName(), FIRST_NAME);
        assertEquals(returnDTO.getLastName(), LAST_NAME);
        assertEquals(returnDTO.getId(), ID);
    }

    @Test
    void getTeacherByID() {
        Teacher teacher = initTeacher();

        when(teacherRepository.findById(ID)).thenReturn(Optional.of(teacher));

        TeacherDTO returnDTO = teacherService.getTeacherByID(ID);

        assertEquals(returnDTO.getFirstName(), FIRST_NAME);
        assertEquals(returnDTO.getLastName(), LAST_NAME);
        assertEquals(returnDTO.getId(), ID);
    }

    @Test
    void createNewTeacher() {
        TeacherDTO teacherDTO = new TeacherDTO();
        teacherDTO.setFirstName(FIRST_NAME);
        teacherDTO.setLastName(LAST_NAME);
        teacherDTO.setId(ID);

        Teacher savedTeacher = new Teacher();
        savedTeacher.setId(teacherDTO.getId());
        savedTeacher.setFirstName(teacherDTO.getFirstName());
        savedTeacher.setLastName(teacherDTO.getLastName());

        when(teacherRepository.save(any(Teacher.class))).thenReturn(savedTeacher);

        TeacherDTO savedDTO = teacherService.createNewTeacher(teacherDTO);

        assertEquals(savedDTO.getId(), ID);
        assertEquals(savedDTO.getFirstName(), FIRST_NAME);
        assertEquals(savedDTO.getLastName(), LAST_NAME);
    }

    @Test
    void updateTeacher() {
        TeacherDTO teacherDTO = new TeacherDTO();
        teacherDTO.setFirstName(FIRST_NAME);
        teacherDTO.setLastName(LAST_NAME);
        teacherDTO.setId(ID);

        Teacher savedTeacher = new Teacher();
        savedTeacher.setId(teacherDTO.getId());
        savedTeacher.setFirstName(teacherDTO.getFirstName());
        savedTeacher.setLastName(teacherDTO.getLastName());

        when(teacherRepository.save(any(Teacher.class))).thenReturn(savedTeacher);

        TeacherDTO savedDTO = teacherService.updateTeacher(ID, teacherDTO);

        assertEquals(savedDTO.getId(), ID);
        assertEquals(savedDTO.getFirstName(), FIRST_NAME);
        assertEquals(savedDTO.getLastName(), LAST_NAME);
    }

    @Test
    void deleteTeacherById() {
        teacherRepository.deleteById(ID);
        verify(teacherRepository, times(1)).deleteById(ID);

   }
}