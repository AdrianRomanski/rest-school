package adrianromanski.restschool.services;

import adrianromanski.restschool.domain.base_entity.person.Teacher;
import adrianromanski.restschool.domain.base_entity.person.enums.Gender;
import adrianromanski.restschool.exceptions.ResourceNotFoundException;
import adrianromanski.restschool.mapper.person.TeacherMapper;
import adrianromanski.restschool.model.base_entity.person.TeacherDTO;
import adrianromanski.restschool.repositories.person.TeacherRepository;
import adrianromanski.restschool.services.person.teacher.TeacherService;
import adrianromanski.restschool.services.person.teacher.TeacherServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static adrianromanski.restschool.domain.base_entity.person.enums.Gender.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
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

    Teacher createTeacher(Long id, String firstName, String lastName, Gender gender) {
        Teacher teacher = Teacher.builder().firstName(firstName).lastName(lastName).gender(gender).build();
        teacher.setId(id);
        return teacher;
    }

    Teacher createWalter() {
        return createTeacher(ID, FIRST_NAME, LAST_NAME, MALE);
    }

    TeacherDTO createWalterDTO() {
        TeacherDTO teacherDTO = TeacherDTO.builder().firstName(FIRST_NAME).lastName(LAST_NAME).gender(MALE).build();
        teacherDTO.setId(ID);
        return teacherDTO;
    }

    Teacher createPeter() {
        return createTeacher(2L, "Peter", "Parker", MALE);
    }

    Teacher createMarie() {
        return createTeacher(3L, "Marie", "Curie", FEMALE);
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        teacherService = new TeacherServiceImpl(teacherRepository, TeacherMapper.INSTANCE);
    }

    @DisplayName("[Happy Path], [Method] = getAllTeachers, [Expected] = List containing 3 Teachers")
    @Test
    void getAllTeachers() {
        List<Teacher> teachers = Arrays.asList(createWalter(), createPeter(), createMarie());

        when(teacherRepository.findAll()).thenReturn(teachers);

        List<TeacherDTO> returnTeachers = teacherService.getAllTeachers();

        assertEquals(returnTeachers.size(), teachers.size());
     }

    @DisplayName("[Happy Path], [Method] = getTeacherByFirstNameAndLastName, [Expected] = TeacherDTO with matching fields")
    @Test
    void getTeacherByFirstNameAndLastName() {
        Teacher teacher = createWalter();

        when(teacherRepository.getTeacherByFirstNameAndLastName(anyString(), anyString())).thenReturn(Optional.of(teacher));

        TeacherDTO returnDTO = teacherService.getTeacherByFirstNameAndLastName(FIRST_NAME, LAST_NAME);

        assertEquals(returnDTO.getFirstName(), FIRST_NAME);
        assertEquals(returnDTO.getLastName(), LAST_NAME);
        assertEquals(returnDTO.getId(), ID);
    }

    @DisplayName("[Happy Path], [Method] = getTeacherByID, [Expected] = TeacherDTO with matching fields")
    @Test
    void getTeacherByID() {
        Teacher teacher = createWalter();

        when(teacherRepository.findById(anyLong())).thenReturn(Optional.of(teacher));

        TeacherDTO returnDTO = teacherService.getTeacherByID(ID);

        assertEquals(returnDTO.getFirstName(), FIRST_NAME);
        assertEquals(returnDTO.getLastName(), LAST_NAME);
        assertEquals(returnDTO.getId(), ID);
    }

    @DisplayName("[Happy Path], [Method] = createNewTeacher, [Expected] = TeacherDTO with matching fields")
    @Test
    void createNewTeacher() {
        TeacherDTO teacherDTO = createWalterDTO();
        Teacher teacher = createWalter();

        when(teacherRepository.save(any(Teacher.class))).thenReturn(teacher);

        TeacherDTO returnDTO = teacherService.createNewTeacher(teacherDTO);

        assertEquals(returnDTO.getId(), ID);
        assertEquals(returnDTO.getFirstName(), FIRST_NAME);
        assertEquals(returnDTO.getLastName(), LAST_NAME);
    }

    @DisplayName("[Happy Path], [Method] = updateTeacher, [Expected] = TeacherDTO with updated fields")
    @Test
    void updateTeacher() {
        TeacherDTO teacherDTO = createWalterDTO();
        Teacher teacher = createWalter();

        when(teacherRepository.findById(ID)).thenReturn(Optional.of(teacher));
        when(teacherRepository.save(any(Teacher.class))).thenReturn(teacher);

        TeacherDTO returnDTO = teacherService.updateTeacher(ID, teacherDTO);

        assertEquals(returnDTO.getId(), ID);
        assertEquals(returnDTO.getFirstName(), FIRST_NAME);
        assertEquals(returnDTO.getLastName(), LAST_NAME);
    }

    @DisplayName("[Unhappy Path], [Method] = updateTeacher, [Reason] = Teacher with id 222 not found")
    @Test
    void updateTeacherUnHappyPath() {
        TeacherDTO teacherDTO = createWalterDTO();

        Throwable ex = catchThrowable(() -> teacherService.updateTeacher(222L,teacherDTO));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }

    @DisplayName("[Happy Path], [Method] = deleteTeacherById, [Expected] = teacherRepository deleting student")
    @Test
    void deleteTeacherByIdHappyPath() {
        Teacher teacher = createWalter();

        when(teacherRepository.findById(ID)).thenReturn(Optional.of(teacher));

        teacherService.deleteTeacherById(ID);

        verify(teacherRepository, times(1)).deleteById(ID);
   }

    @DisplayName("[Unhappy Path], [Method] = deleteTeacherById, [Reason] = Teacher with id 222 not found")
    @Test
    void deleteTeacherByIDUnHappyPath() {

        Throwable ex = catchThrowable(() -> teacherService.deleteTeacherById(222L));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }
}