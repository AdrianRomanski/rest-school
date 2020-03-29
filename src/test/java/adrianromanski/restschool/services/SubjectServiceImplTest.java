package adrianromanski.restschool.services;

import adrianromanski.restschool.domain.Student;
import adrianromanski.restschool.domain.Subject;
import adrianromanski.restschool.mapper.SubjectMapper;
import adrianromanski.restschool.model.StudentDTO;
import adrianromanski.restschool.model.SubjectDTO;
import adrianromanski.restschool.repositories.SubjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.stream;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class SubjectServiceImplTest {

    public static final String BIOLOGY = "Biology";
    public static final String MATH = "Math";
    public static final String PHYSICAL_EDUCATION = "Physical education";

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
        subject.setName(MATH);
        subject.setValue(10L);
        subject.setId(2L);
        return subject;
    }

    Subject initPE() {
        Subject subject = new Subject();
        subject.setName(PHYSICAL_EDUCATION);
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
        subjectDTO.setName(MATH);

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
        subjectDTO.setName(MATH);
        subjectDTO.setId(1L);

        Subject savedSubject = new Subject();
        savedSubject.setName(subjectDTO.getName());
        savedSubject.setId(subjectDTO.getId());

        when(subjectRepository.save(any(Subject.class))).thenReturn(savedSubject);

        //when
        SubjectDTO savedDTO = subjectService.updateSubject(1L, subjectDTO);

        //then
        assertEquals(savedDTO.getName(), MATH);
    }

    @Test
    void getSubjectByID() {
        //given
        Subject subject = new Subject();
        subject.setName(MATH);
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
        subject.setName(MATH);
        subject.setId(1L);

        when(subjectRepository.findByName(anyString())).thenReturn(subject);

        //when
        SubjectDTO subjectDTO = subjectService.getSubjectByName(MATH);

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
        subject1.setName("Math");


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
        subject3.setName("Biology");

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