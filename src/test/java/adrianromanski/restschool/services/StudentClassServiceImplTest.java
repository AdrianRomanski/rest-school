package adrianromanski.restschool.services;

import adrianromanski.restschool.domain.base_entity.enums.Gender;
import adrianromanski.restschool.domain.base_entity.enums.MaleName;
import adrianromanski.restschool.domain.base_entity.enums.Specialization;
import adrianromanski.restschool.domain.base_entity.group.StudentClass;
import adrianromanski.restschool.domain.base_entity.person.Student;
import adrianromanski.restschool.exceptions.ResourceNotFoundException;
import adrianromanski.restschool.mapper.group.StudentClassMapper;
import adrianromanski.restschool.mapper.person.StudentMapper;
import adrianromanski.restschool.model.base_entity.group.StudentClassDTO;
import adrianromanski.restschool.model.base_entity.person.StudentDTO;
import adrianromanski.restschool.repositories.group.StudentClassRepository;
import adrianromanski.restschool.services.group.student_class.StudentClassService;
import adrianromanski.restschool.services.group.student_class.StudentClassServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static adrianromanski.restschool.domain.base_entity.enums.FemaleName.CHARLOTTE;
import static adrianromanski.restschool.domain.base_entity.enums.Gender.FEMALE;
import static adrianromanski.restschool.domain.base_entity.enums.Gender.MALE;
import static adrianromanski.restschool.domain.base_entity.enums.LastName.*;
import static adrianromanski.restschool.domain.base_entity.enums.MaleName.*;
import static adrianromanski.restschool.domain.base_entity.enums.Specialization.BIOLOGY;
import static adrianromanski.restschool.domain.base_entity.enums.Specialization.PHYSICS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


class StudentClassServiceImplTest {

//    Trying to unblock CircleCi
    public static final long ID = 1L;
    public static final String ROOKIES = "Rookies";

    StudentClassService studentClassService;

    @Mock
    StudentClassRepository studentClassRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        studentClassService = new StudentClassServiceImpl(studentClassRepository, StudentClassMapper.INSTANCE, StudentMapper.INSTANCE);
    }

    private StudentClass createStudentClass(String name, String president, Specialization specialization, Long id) {
        StudentClass studentClass = StudentClass.builder().name(name).president(president).specialization(specialization).build();
        studentClass.setId(id);
        return studentClass;
    }

    private Student createStudent(Long id, String firstName, String lastName, Gender gender) {
        Student student = Student.builder().firstName(firstName).lastName(lastName).gender(gender).build();
        student.setId(id);
        return student;
    }

    private Student createEthan() {
        return createStudent(ID, ETHAN.get(), COOPER.get(), MALE);
    }

    private Student createSebastian() {
        return createStudent(2L, SEBASTIAN.get(), RODRIGUEZ.get(), MALE);
    }

    private Student createCharlotte() {
        return createStudent(3L, CHARLOTTE.get(), HENDERSON.get(), FEMALE);
    }

    private StudentClass createRookies() { // 2 Students
        StudentClass studentClass = createStudentClass(ROOKIES, ETHAN.get(), BIOLOGY, ID);
        studentClass.getStudentList().addAll(Arrays.asList(createEthan(), createCharlotte()));
        return studentClass;
    }

    private StudentClass createGeeks() { // 1 Student
        StudentClass studentClass = createStudentClass("Geeks", SEBASTIAN.get(), PHYSICS, 2L);
        studentClass.getStudentList().add(createSebastian());
        return studentClass;
    }

    private StudentClass createNerds() { return createStudentClass("Nerds", ISAAC.get(), PHYSICS, 3L); }

    private List<StudentClass> createList() { return Arrays.asList(createRookies(), createGeeks(), createNerds()); }

    private StudentClassDTO createRookiesDTO() {
        StudentClassDTO studentClassDTO = StudentClassDTO.builder().name(ROOKIES).president(ETHAN.get()).specialization(BIOLOGY).build();
        studentClassDTO.setId(ID);
        return studentClassDTO;
    }

    @DisplayName("[Happy Path], [Method] = getAllStudentClasses, [Expected] = List containing 3 Student Classes")
    @Test
    void getAllStudentClasses() {
        List<StudentClass> studentClasses = createList();

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

    @DisplayName("[Happy Path], [Method] = getStudentClassByPresident, [Expected] = StudentClassDTO with matching president")
    @Test
    void getStudentClassByPresident() {
        List<StudentClass> studentClasses = createList();

        when(studentClassRepository.findAll()).thenReturn(studentClasses);

        List<StudentClassDTO> returnDTO = studentClassService.getStudentClassByPresident(ETHAN.get());

        assertEquals(returnDTO.size(), 1);
    }

    @DisplayName("[Happy Path], [Method] = getStudentClassesGroupedBySpecialization, [Expected] = Map contains 2 keys")
    @Test
    void getStudentClassesGroupedBySpecialization() {
        List<StudentClass> studentClasses = createList();

        when(studentClassRepository.findAll()).thenReturn(studentClasses);

        Map<Specialization, Map<String, List<StudentClassDTO>>> returnMap = studentClassService.getStudentClassesGroupedBySpecialization();

        assertTrue(returnMap.containsKey(PHYSICS)); // 2 Classes with Physics
        assertTrue(returnMap.containsKey(BIOLOGY));
    }

    @DisplayName("[Happy Path], [Method] = getAllStudentClassForSpecialization, [Expected] = List with 2 Classes")
    @Test
    void getAllStudentClassForSpecialization() {
        List<StudentClass> studentClasses = createList();

        when(studentClassRepository.findAll()).thenReturn(studentClasses);

        List<StudentClassDTO> returnList = studentClassService.getAllStudentClassForSpecialization(PHYSICS);

        assertEquals(returnList.size(), 2);
    }
    @DisplayName("[Happy Path], [Method] = getLargestStudentClass, [Expected] = List with Rookies (2 Students)")
    @Test
    void getLargestStudentClass() {
        List<StudentClass> studentClasses = createList();

        when(studentClassRepository.findAll()).thenReturn(studentClasses);

        List<StudentClassDTO> returnList = studentClassService.getLargestStudentClass();

        assertEquals(returnList.get(0).getName(), ROOKIES);
    }

    @DisplayName("[Happy Path], [Method] = getLargestStudentClass, [Expected] = List with Nerds (0 Students)")
    @Test
    void getSmallestStudentClass() {
        List<StudentClass> studentClasses = createList();

        when(studentClassRepository.findAll()).thenReturn(studentClasses);

        List<StudentClassDTO> returnList = studentClassService.getSmallestStudentClass();

        assertEquals(returnList.get(0).getName(), "Nerds");
    }
    @DisplayName("[Happy Path], [Method] = getAllStudentsForClass, [Expected] = Map with 2 keys every key with one entry")
    @Test
    void getAllStudentsForClassHappyPath() {
        StudentClass studentClass = createRookies();

        when(studentClassRepository.findById(anyLong())).thenReturn(Optional.of(studentClass));

        Map<Gender, List<StudentDTO>> returnMap = studentClassService.getAllStudentsForClass(anyLong());

        assertTrue(returnMap.containsKey(MALE));
        assertTrue(returnMap.containsKey(FEMALE));
        assertEquals(returnMap.get(MALE).size(), 1);
        assertEquals(returnMap.get(FEMALE).size(), 1);
    }

    @DisplayName("[Unhappy Path], [Method] = getAllStudentsForClass, [Reason] = Student Class with id 222 not found")
    @Test
    void getAllStudentsForClassUnHappyPath() {

        Throwable ex = catchThrowable(() -> studentClassService.getAllStudentsForClass(222L));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
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