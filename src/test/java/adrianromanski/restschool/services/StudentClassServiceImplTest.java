package adrianromanski.restschool.services;

import adrianromanski.restschool.domain.base_entity.group.StudentClass;
import adrianromanski.restschool.exceptions.ResourceNotFoundException;
import adrianromanski.restschool.mapper.group.StudentClassMapper;
import adrianromanski.restschool.model.base_entity.group.StudentClassDTO;
import adrianromanski.restschool.repositories.group.StudentClassRepository;
import adrianromanski.restschool.services.group.student_class.StudentClassService;
import adrianromanski.restschool.services.group.student_class.StudentClassServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


class StudentClassServiceImplTest {

    public static final long ID = 1L;
    public static final String ROOKIES = "Rookies";

    StudentClassService studentClassService;

    @Mock
    StudentClassRepository studentClassRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        studentClassService = new StudentClassServiceImpl(studentClassRepository, StudentClassMapper.INSTANCE);
    }

    private StudentClass createRookies() {
        StudentClass studentClass = StudentClass.builder().name(ROOKIES).build();
        studentClass.setId(ID);
        return studentClass;
    }

    private StudentClassDTO createRookiesDTO() {
        StudentClassDTO studentClassDTO = StudentClassDTO.builder().name(ROOKIES).build();
        studentClassDTO.setId(ID);
        return studentClassDTO;
    }

    @DisplayName("[Happy Path], [Method] = getAllStudentClasses, [Expected] = List containing 3 Student Classes")
    @Test
    void getAllStudentClasses() {
        List<StudentClass> studentClasses = Arrays.asList(createRookies(), createRookies(), createRookies());

        when(studentClassRepository.findAll()).thenReturn(studentClasses);

        List<StudentClassDTO> studentClassDTOS = studentClassService.getAllStudentClasses();

        assertEquals(studentClassDTOS.size(), 3);
    }

    @DisplayName("[Happy Path], [Method] = getStudentClassByID, [Expected] = StudentClassDTO with matching fields")
    @Test
    void getStudentClassByID() {
        StudentClass studentClass = createRookies();

        when(studentClassRepository.findById(ID)).thenReturn(Optional.of(studentClass));

        StudentClassDTO studentClassDTO = studentClassService.getStudentClassByID(ID);

        assertEquals(studentClassDTO.getName(), ROOKIES);
        assertEquals(studentClassDTO.getId(), ID);
    }

    @DisplayName("[Happy Path], [Method] = createNewStudentClass, [Expected] = StudentClassDTO with matching fields")
    @Test
    void createNewStudentClass() {
       StudentClassDTO studentClassDTO = createRookiesDTO();
       StudentClass savedStudent = createRookies();

       when(studentClassRepository.save(any(StudentClass.class))).thenReturn(savedStudent);

       StudentClassDTO returnDTO = studentClassService.createNewStudentClass(studentClassDTO);

       assertEquals(returnDTO.getName(), ROOKIES);
       assertEquals(returnDTO.getId(), ID);
    }

    @DisplayName("[Happy Path], [Method] = updateStudentClass, [Expected] = StudentClassDTO with updated fields")
    @Test
    void updateStudentClassHappyPath() {
        StudentClassDTO studentClassDTO = createRookiesDTO();
        StudentClass updatedStudent = createRookies();

        when(studentClassRepository.findById(ID)).thenReturn(Optional.of(updatedStudent));
        when(studentClassRepository.save(any(StudentClass.class))).thenReturn(updatedStudent);

        StudentClassDTO returnDTO = studentClassService.updateStudentClass(ID, studentClassDTO);

        assertEquals(returnDTO.getName(), ROOKIES);
        assertEquals(returnDTO.getId(), ID);
    }

    @DisplayName("[Unhappy Path], [Method] = updateStudentClass, [Reason] = Student Class with id 222 not found")
    @Test
    void updateStudentClassUnHappyPath() {
        StudentClassDTO studentClassDTO = createRookiesDTO();

        Throwable ex = catchThrowable(() -> studentClassService.updateStudentClass(222L, studentClassDTO));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }

    @DisplayName("[Happy Path], [Method] = deleteStudentClassById, [Expected] = studentClassRepository deleting")
    @Test
    void deleteStudentClassByIdHappyPath() {
        StudentClass studentClass = createRookies();

        when(studentClassRepository.findById(ID)).thenReturn(Optional.of(studentClass));

        studentClassService.deleteStudentClassById(ID);

        verify(studentClassRepository, times(1)).deleteById(ID);
    }

    @DisplayName("[Unhappy Path], [Method] = deleteStudentClassById, [Reason] = Student Class with id 222 not found")
    @Test
    void deleteStudentClassByIdUnHappyPath() {

        Throwable ex = catchThrowable(() -> studentClassService.deleteStudentClassById(222L));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }
}