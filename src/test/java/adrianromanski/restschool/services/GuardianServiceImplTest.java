package adrianromanski.restschool.services;

import adrianromanski.restschool.domain.base_entity.person.Guardian;
import adrianromanski.restschool.domain.base_entity.person.Student;
import adrianromanski.restschool.domain.base_entity.person.enums.Gender;
import adrianromanski.restschool.exceptions.ResourceNotFoundException;
import adrianromanski.restschool.mapper.person.GuardianMapper;
import adrianromanski.restschool.mapper.person.StudentMapper;
import adrianromanski.restschool.model.base_entity.person.GuardianDTO;
import adrianromanski.restschool.model.base_entity.person.StudentDTO;
import adrianromanski.restschool.repositories.person.GuardianRepository;
import adrianromanski.restschool.repositories.person.StudentRepository;
import adrianromanski.restschool.services.person.guardian.GuardianService;
import adrianromanski.restschool.services.person.guardian.GuardianServiceImpl;
import org.junit.jupiter.api.*;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static adrianromanski.restschool.domain.base_entity.person.enums.Gender.FEMALE;
import static adrianromanski.restschool.domain.base_entity.person.enums.Gender.MALE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class GuardianServiceImplTest {

    public static final String FIRST_NAME = "Bruce";
    public static final String LAST_NAME = "Wayne";
    public static final String EMAIL = "WayneEnterprise@Gotham.com";
    public static final String NUMBER = "543-352-332";
    public static final long ID = 1L;

    GuardianService guardianService;

    @Mock
    GuardianRepository guardianRepository;

    @Mock
    StudentRepository studentRepository;

    @BeforeEach
    void beforeAll() {
        MockitoAnnotations.initMocks(this);

        guardianService = new GuardianServiceImpl(GuardianMapper.INSTANCE, StudentMapper.INSTANCE,
                                                                    guardianRepository, studentRepository);
    }

    GuardianDTO createBruceWayneDTO() {
        GuardianDTO guardianDTO = GuardianDTO.builder().firstName(FIRST_NAME).lastName(LAST_NAME)
                                                    .email(EMAIL).telephoneNumber(NUMBER).build();
        guardianDTO.setId(ID);
        return guardianDTO;
    }

    Guardian createBruceWayne() {
        Guardian guardian = Guardian.builder().firstName(FIRST_NAME).lastName(LAST_NAME)
                                            .email(EMAIL).telephoneNumber(NUMBER).dateOfBirth(LocalDate.of(1992, 11, 3)).build();
        Student jessiePinkman = createJessiePinkman();
        Student jessicaParker = createJessicaParker();
        guardian.getStudents().addAll(Arrays.asList(jessiePinkman, jessicaParker));
        jessiePinkman.setGuardian(guardian);
        jessicaParker.setGuardian(guardian);
        guardian.setId(ID);
        return guardian;
    }

    Student createStudent(Long id, String firstName, String lastName, Gender gender) {
        Student student = Student.builder().firstName(firstName).lastName(lastName).gender(gender).build();
        student.setId(id);
        return student;
    }

    Student createJessiePinkman() {
        return createStudent(2L, "Jessie", "Pinkman", MALE);
    }

    Student createJessicaParker() {
        return createStudent(3L, "Jessica", "Parker", FEMALE);
    }

    @DisplayName("[Happy Path], [Method] = getAllGuardians, [Expected] = List containing 3 Guardians")
    @Test
    void getAllLegalGuardians() {
        List<Guardian> legalGuardians = Arrays.asList(createBruceWayne(), createBruceWayne(), createBruceWayne());

        when(guardianRepository.findAll()).thenReturn(legalGuardians);

        List<GuardianDTO> legalGuardiansDTO = guardianService.getAllGuardians();

        assertEquals(legalGuardians.size(), legalGuardiansDTO.size());
    }

    @DisplayName("[Happy Path], [Method] = getGuardianByID, [Expected] = GuardianDTO with matching fields")
    @Test
    void getGuardianByID() {
        Guardian legalGuardian = createBruceWayne();

        when(guardianRepository.findById(ID)).thenReturn(Optional.of(legalGuardian));

        GuardianDTO legalGuardianDTO = guardianService.getGuardianByID(ID);

        assertEquals(legalGuardianDTO.getFirstName(), FIRST_NAME);
        assertEquals(legalGuardianDTO.getLastName(), LAST_NAME);
        assertEquals(legalGuardianDTO.getTelephoneNumber(), NUMBER);
        assertEquals(legalGuardianDTO.getEmail(), EMAIL);
        assertEquals(legalGuardianDTO.getId(), ID);

    }
    @DisplayName("[Happy Path], [Method] = getGuardianByFirstAndLastName, [Expected] = GuardianDTO with matching fields")
    @Test
    void getLegalGuardianByFirstAndLastName() {
        Guardian legalGuardian = createBruceWayne();

        when(guardianRepository.getGuardianByFirstNameAndLastName(FIRST_NAME, LAST_NAME))
                .thenReturn(Optional.of(legalGuardian));

        GuardianDTO legalGuardianDTO = guardianService.getGuardianByFirstAndLastName(FIRST_NAME, LAST_NAME);

        assertEquals(legalGuardianDTO.getFirstName(), FIRST_NAME);
        assertEquals(legalGuardianDTO.getLastName(), LAST_NAME);
        assertEquals(legalGuardianDTO.getTelephoneNumber(), NUMBER);
        assertEquals(legalGuardianDTO.getEmail(), EMAIL);
        assertEquals(legalGuardianDTO.getId(), ID);
    }

    @DisplayName("[Happy Path], [Method] = getGuardiansByAge, [Expected] = Map<27, List<2 x GuardianDTO>>")
    @Test
    void getGuardiansByAge() {
        List<Guardian> guardians = Arrays.asList(createBruceWayne(), createBruceWayne());

        when(guardianRepository.findAll()).thenReturn(guardians);

        Map<Long, List<GuardianDTO>> guardiansByAge = guardianService.getGuardiansByAge();

        assertEquals(guardiansByAge.size(), 1);
        assertTrue(guardiansByAge.containsKey(27L));
    }

    @DisplayName("[Happy Path], [Method] = getAllStudentsForGuardian, [Expected] = List<2 x StudentDTO>")
    @Test
    void getAllStudentsForGuardian() {
        Guardian guardian = createBruceWayne();

        when(studentRepository.findAll()).thenReturn(guardian.getStudents());

        List<StudentDTO> studentsDTO = guardianService.getAllStudentsForGuardian(guardian.getId());

        assertEquals(studentsDTO.size(), 2);
        assertEquals(studentsDTO.get(0).getFirstName(), "Jessie");
        assertEquals(studentsDTO.get(1).getFirstName(), "Jessica");
    }

    @DisplayName("[Happy Path], [Method] = createNewGuardian, [Expected] = GuardianDTO with matching fields")
    @Test
    void createNewLegalGuardian() {
        Guardian guardian = createBruceWayne();

        GuardianDTO guardianDTO = createBruceWayneDTO();

        when(guardianRepository.findById(anyLong())).thenReturn(Optional.of(guardian));
        when(guardianRepository.save(any(Guardian.class))).thenReturn(guardian);

        GuardianDTO returnDTO = guardianService.createNewGuardian(guardianDTO);

        assertEquals(returnDTO.getFirstName(), FIRST_NAME);
        assertEquals(returnDTO.getLastName(), LAST_NAME);
        assertEquals(returnDTO.getTelephoneNumber(), NUMBER);
        assertEquals(returnDTO.getEmail(), EMAIL);
        assertEquals(returnDTO.getId(), ID);
    }

    @DisplayName("[Happy Path], [Method] = updateTeacher, [Expected] = TeacherDTO with updated fields")
    @Test
    void updateLegalGuardianHappyPath() {
        Guardian guardian = createBruceWayne();

        GuardianDTO guardianDTO = createBruceWayneDTO();
        guardianDTO.setFirstName("Updated");

        when(guardianRepository.findById(anyLong())).thenReturn(Optional.of(guardian));
        when(guardianRepository.save(any(Guardian.class))).thenReturn(guardian);

        GuardianDTO returnDTO = guardianService.updateGuardian(guardianDTO, ID);

        assertEquals(returnDTO.getFirstName(), "Updated");
        assertEquals(returnDTO.getLastName(), LAST_NAME);
        assertEquals(returnDTO.getTelephoneNumber(), NUMBER);
        assertEquals(returnDTO.getEmail(), EMAIL);
        assertEquals(returnDTO.getId(), ID);
    }

    @DisplayName("[Unhappy Path], [Method] = updateGuardian, [Reason] = Guardian with id 222 not found")
    @Test
    void updateLegalGuardianUnhappyPath() {
        GuardianDTO guardianDTO = createBruceWayneDTO();

        Throwable ex = catchThrowable(() -> guardianService.updateGuardian(guardianDTO, 222L));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }

    @DisplayName("[Happy Path], [Method] = deleteGuardianByID, [Expected] = guardianRepository deleting student")
    @Test
    void deleteLegalGuardianByIDHappyPath() {
        Guardian guardian = createBruceWayne();

        when(guardianRepository.findById(anyLong())).thenReturn(Optional.of(guardian));
        guardianService.deleteGuardianByID(guardian.getId());

        verify(guardianRepository, times(1)).deleteById(ID);
    }

    @DisplayName("[Unhappy Path], [Method] = deleteGuardianByID, [Reason] = Guardian with id 222 not found")
    @Test
    void deleteLegalGuardianByIDUnhappyPath() {
        Throwable ex = catchThrowable(() -> guardianService.deleteGuardianByID(222L));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }
}

