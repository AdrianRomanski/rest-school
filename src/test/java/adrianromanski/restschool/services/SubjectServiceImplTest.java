package adrianromanski.restschool.services;

import adrianromanski.restschool.domain.enums.Subjects;
import adrianromanski.restschool.domain.base_entity.Subject;
import adrianromanski.restschool.exceptions.ResourceNotFoundException;
import adrianromanski.restschool.mapper.base_entity.SubjectMapper;
import adrianromanski.restschool.model.base_entity.SubjectDTO;
import adrianromanski.restschool.repositories.base_entity.SubjectRepository;
import adrianromanski.restschool.services.base_entity.subject.SubjectService;
import adrianromanski.restschool.services.base_entity.subject.SubjectServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.swing.text.html.Option;
import java.util.*;

import static adrianromanski.restschool.domain.enums.Subjects.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class SubjectServiceImplTest {

    public static final long ID = 2L;
    SubjectService subjectService;

    @Mock
    SubjectRepository subjectRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        subjectService = new SubjectServiceImpl(SubjectMapper.INSTANCE, subjectRepository);
    }

    Subject createSubject(Subjects sub, Long value, Long id) {
        Subject subject = Subject.builder().name(sub).value(value).build();
        subject.setId(id);
        return subject;
    }

    Subject createBiology() {
        return createSubject(BIOLOGY, 10L, 1L);
    }

    Subject createMath() {
        return createSubject(MATHEMATICS, 10L, ID);
    }

    Subject createPhysics() {
        return createSubject(PHYSICS, 1L, 3L);
    }

    List<Subject> createList() {
        return Arrays.asList(createBiology(), createMath(), createPhysics());
    }


    @DisplayName("[Happy Path], [Method] = getAllSubjects")
    @Test
    void getAllSubjects() {
        List<Subject> subjects = createList();

        when(subjectRepository.findAll()).thenReturn(subjects);

        List<SubjectDTO> returnDTO = subjectService.getAllSubjects();

        assertEquals(returnDTO.size(), 3);
    }


    @DisplayName("[Happy Path], [Method] = getSubjectByID")
    @Test
    void getSubjectByIDHappyPath() {
        Subject subject = createMath();

        when(subjectRepository.findById(anyLong())).thenReturn(Optional.of(subject));

        SubjectDTO returnDTO = subjectService.getSubjectByID(1L);

        assertEquals(returnDTO.getName(), MATHEMATICS);
        assertEquals(returnDTO.getId(), ID);
    }


    @DisplayName("[UnHappy Path], [Method] = getSubjectByID")
    @Test
    void getSubjectByIDUnHappyPath() {
        Throwable ex = catchThrowable(() -> subjectService.getSubjectByID(222L));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }


    @DisplayName("[Happy Path], [Method] = getSubjectByName")
    @Test
    void getSubjectByNameHappyPath() {
        Subject subject = createMath();

        when(subjectRepository.findByName(MATHEMATICS)).thenReturn(Optional.of(subject));

        SubjectDTO returnDTO = subjectService.getSubjectByName(MATHEMATICS.toString());

        assertEquals(returnDTO.getName(), MATHEMATICS);
        assertEquals(returnDTO.getId(), ID);
    }


    @DisplayName("[UnHappy Path], [Method] = getSubjectByName")
    @Test
    void getSubjectByNameUnhappyPathSubjectNotInsideDatabase() {
        Throwable ex = catchThrowable(() -> subjectService.getSubjectByName(MATHEMATICS.toString()));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }


    @DisplayName("[UnHappy Path], [Method] = getSubjectByName")
    @Test
    void getSubjectByNameUnhappyPathWrongEnumValue() {
        Throwable ex = catchThrowable(() -> subjectService.getSubjectByName("Pen"));

        assertThat(ex).isInstanceOf(IllegalArgumentException.class); // I should do something with this exception
    }


    @DisplayName("[Happy Path], [Method] = getSubjectsWithLowestValue")
    @Test
    void getSubjectWithLowestValue() {
        List<Subject> subjects = createList();

        when(subjectRepository.findAll()).thenReturn(subjects);

        List<SubjectDTO> returnDTO = subjectService.getSubjectsWithLowestValue();

        assertEquals(returnDTO.size(), 1);
    }


    @DisplayName("[Happy Path], [Method] = getSubjectsWithLowestValue")
    @Test
    void getSubjectsWithFullValue() {
        List<Subject> subjects = createList();

        when(subjectRepository.findAll()).thenReturn(subjects);

        List<SubjectDTO> returnDTO = subjectService.getSubjectsWithFullValue();

        assertEquals(returnDTO.size(), 2);
    }


    @DisplayName("[Happy Path], [Method] = createNewSubject")
    @Test
    void createNewSubject() {
        SubjectDTO subjectDTO = new SubjectDTO();
        subjectDTO.setName(MATHEMATICS);

        Subject subject = createMath();

        when(subjectRepository.save(any(Subject.class))).thenReturn(subject);

        SubjectDTO returnDTO = subjectService.createNewSubject(subjectDTO);

        assertEquals(returnDTO.getName(), MATHEMATICS);
    }


    @DisplayName("[Happy Path], [Method] = updateSubject")
    @Test
    void updateSubjectHappyPath() {
        SubjectDTO subjectDTO = new SubjectDTO();
        subjectDTO.setValue(150L);
        Subject subject = createMath();

        when(subjectRepository.findById(anyLong())).thenReturn(Optional.of(subject));

        SubjectDTO savedDTO = subjectService.updateSubject(ID, subjectDTO);

        assertEquals(savedDTO.getValue(), 150L);
    }


    @DisplayName("[Unhappy Path], [Method] = updateSubject")
    @Test
    void updateSubjectUnHappyPath() {
        SubjectDTO subjectDTO = new SubjectDTO();

        Throwable ex = catchThrowable(() -> subjectService.updateSubject(222L, subjectDTO));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }


    @DisplayName("[Happy Path], [Method] = deleteSubjectByID")
    @Test
    void deleteSubjectByIdHappyPath() {
        Subject subject = createMath();

        when(subjectRepository.findById(anyLong())).thenReturn(Optional.of(subject));

        subjectService.deleteSubjectByID(anyLong());

        verify(subjectRepository, times(1)).delete(subject);
    }


    @DisplayName("[Unhappy Path], [Method] = deleteSubjectByID")
    @Test
    void deleteSubjectByIdUnhappyPath() {
        Throwable ex = catchThrowable(() -> subjectService.deleteSubjectByID(222L));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }
}