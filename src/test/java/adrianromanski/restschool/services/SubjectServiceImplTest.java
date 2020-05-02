package adrianromanski.restschool.services;

import adrianromanski.restschool.domain.base_entity.person.Student;
import adrianromanski.restschool.domain.base_entity.Subject;
import adrianromanski.restschool.mapper.base_entity.SubjectMapper;
import adrianromanski.restschool.model.base_entity.SubjectDTO;
import adrianromanski.restschool.repositories.base_entity.SubjectRepository;
import adrianromanski.restschool.services.base_entity.subject.SubjectService;
import adrianromanski.restschool.services.base_entity.subject.SubjectServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static adrianromanski.restschool.domain.base_entity.enums.Subjects.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class SubjectServiceImplTest {


    SubjectService subjectService;

    @Mock
    SubjectRepository subjectRepository;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        subjectService = new SubjectServiceImpl(SubjectMapper.INSTANCE, subjectRepository);
    }

    Subject initBiology() {
        Subject subject = new Subject();
        subject.setName(BIOLOGY);
        subject.setValue(10L);
        subject.setId(1L);
        return subject;
    }

    Subject initMath() {
        Subject subject = new Subject();
        subject.setName(MATHEMATICS);
        subject.setValue(10L);
        subject.setId(2L);
        return subject;
    }

    Subject initPE() {
        Subject subject = new Subject();
        subject.setName(PHYSICS);
        subject.setValue(1L);
        subject.setId(3L);
        return subject;
    }

    @Test
    void getAllSubjects() {
        //given
        List<Subject> subjects = Arrays.asList(new Subject(), new Subject(), new Subject());

        when(subjectRepository.findAll()).thenReturn(subjects);

        //when
        List<SubjectDTO> subjectDTOS = subjectService.getAllSubjects();

        //then
        assertEquals(3, subjectDTOS.size());
    }

    @Test
    void createNewSubject() {
        //given
        SubjectDTO subjectDTO = new SubjectDTO();
        subjectDTO.setName(MATHEMATICS);

        Subject savedSubject = new Subject();
        savedSubject.setName(subjectDTO.getName());

        when(subjectRepository.save(any(Subject.class))).thenReturn(savedSubject);

        //when

        SubjectDTO savedDTO = subjectService.createNewSubject(subjectDTO);

        assertEquals(savedDTO.getName(), subjectDTO.getName());
    }

    @Test
    void updateSubject() {
        //given
        SubjectDTO subjectDTO = new SubjectDTO();
        subjectDTO.setName(MATHEMATICS);
        subjectDTO.setId(1L);

        Subject savedSubject = new Subject();
        savedSubject.setName(subjectDTO.getName());
        savedSubject.setId(subjectDTO.getId());

        when(subjectRepository.save(any(Subject.class))).thenReturn(savedSubject);

        //when
        SubjectDTO savedDTO = subjectService.updateSubject(1L, subjectDTO);

        //then
        assertEquals(savedDTO.getName(), MATHEMATICS);
    }

    @Test
    void getSubjectByID() {
        //given
        Subject subject = new Subject();
        subject.setName(MATHEMATICS);
        subject.setId(1L);

        when(subjectRepository.findById(anyLong())).thenReturn(Optional.of(subject));

        //when
        SubjectDTO subjectDTO = subjectService.getSubjectByID(1L);

        //then
        assertEquals(subjectDTO.getName(), subject.getName());
        assertEquals(subjectDTO.getId(), subject.getId());
    }

    @Test
    void getSubjectByName() {
        //Given
        Subject subject = new Subject();
        subject.setName(MATHEMATICS);
        subject.setId(1L);

        when(subjectRepository.findByName(anyString())).thenReturn(subject);

        //when
        SubjectDTO subjectDTO = subjectService.getSubjectByName(MATHEMATICS.toString());

        //then
        assertEquals(subjectDTO.getName(), subject.getName());
        assertEquals(subjectDTO.getId(), subject.getId());

    }

    @Test
    void getSubjectsWithFullValue() {
        //Given
        List<Subject> subjects = Arrays.asList(initBiology(), initMath(), initPE());

        when(subjectRepository.findAll()).thenReturn(subjects);

        //When
        List<SubjectDTO> subjectDTOS = subjectService.getSubjectsWithFullValue();

        //Then
        assertEquals(2, subjectDTOS.size());
    }

    @Test
    void getSubjectWithLowestValue() {
        //Given
        List<Subject> subjects = Arrays.asList(initBiology(), initMath(), initPE());

        when(subjectRepository.findAll()).thenReturn(subjects);

        //when
        List<SubjectDTO> subjectDTOS = subjectService.getSubjectsWithLowestValue();

        //then
        assertEquals(1, subjectDTOS.size());
    }

    @Test
    void getMostPopularSubject() {
        //given
        Subject subject1 = new Subject();
        subject1.setName(MATHEMATICS);


        Student adrian = new Student();
        adrian.setFirstName("Adrian");
        Student adrian1 = new Student();
        adrian.setFirstName("Adrian1");
        Student adrian2 = new Student();
        adrian.setFirstName("Adrian2");

        subject1.addStudent(adrian);
        subject1.addStudent(adrian1);
        subject1.addStudent(adrian2);

        Subject subject3 = new Subject();
        subject3.setName(BIOLOGY);

        Student wojtek = new Student();
        wojtek.setFirstName("Wojtek");
        subject3.addStudent(wojtek);

        List<Subject> subjects = Arrays.asList(subject1, subject3);

        when(subjectRepository.findAll()).thenReturn(subjects);

        //when
        SubjectDTO subjectDTO = subjectService.getMostPopularSubject();

        assertEquals(subjectDTO.getName(), subject1.getName());

    }
}