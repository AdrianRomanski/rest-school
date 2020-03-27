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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class SubjectServiceImplTest {

    public static final String MATHEMATICS = "Mathematics";
    public static final Long ID = 1L;
    SubjectService subjectService;

    @Mock
    SubjectRepository subjectRepository;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        subjectService = new SubjectServiceImpl(SubjectMapper.INSTANCE, subjectRepository);
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

        Subject savedSubject = new Subject();
        savedSubject.setName(subjectDTO.getName());
        savedSubject.setId(ID);

        when(subjectRepository.save(any(Subject.class))).thenReturn(savedSubject);

        //when
        SubjectDTO savedDTO = subjectService.updateSubject(ID, subjectDTO);

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
        SubjectDTO subjectDTO = subjectService.getSubjectByName(MATHEMATICS);

        //then
        assertEquals(subjectDTO.getName(), subject.getName());
        assertEquals(subjectDTO.getId(), subject.getId());

    }

    @Test
    void getSubjectsWithFullValue() {
        //Given
        List<Subject> subjects = Arrays.asList(Subject.builder().value(10L).build(), Subject.builder().value(10L).build(),
                                                Subject.builder().value(1L).build());

        when(subjectRepository.findAll()).thenReturn(subjects);

        //When
        List<SubjectDTO> subjectDTOS = subjectService.getSubjectsWithFullValue();

        //Then
        assertEquals(2, subjectDTOS.size());
    }

    @Test
    void getSubjectWithLowestValue() {
        //Given
        List<Subject> subjects = Arrays.asList(Subject.builder().value(10L).build(), Subject.builder().value(1L).build(),
                Subject.builder().value(1L).build());

        when(subjectRepository.findAll()).thenReturn(subjects);

        //when
        List<SubjectDTO> subjectDTOS = subjectService.getSubjectsWithLowestValue();

        //then
        assertEquals(2, subjectDTOS.size());
    }

    @Test
    void getMostPopularSubject() {
        //given
        Subject subject1 = new Subject();
        subject1.setName("Math");


        Student adrian = Student.builder().firstName("Adrian").build();
        Student adrian1 = Student.builder().firstName("Adrian1").build();
        Student adrian2 = Student.builder().firstName("Adrian2").build();

        subject1.addStudent(adrian);
        subject1.addStudent(adrian1);
        subject1.addStudent(adrian2);

        Subject subject3 = new Subject();
        subject3.setName("Biology");

        Student wojtek = Student.builder().firstName("Wojtek").build();
        subject3.addStudent(wojtek);

        List<Subject> subjects = Arrays.asList(subject1, subject3);

        when(subjectRepository.findAll()).thenReturn(subjects);

        //when
        SubjectDTO subjectDTO = subjectService.getMostPopularSubject();

        assertEquals(subjectDTO.getName(), subject1.getName());

    }
}