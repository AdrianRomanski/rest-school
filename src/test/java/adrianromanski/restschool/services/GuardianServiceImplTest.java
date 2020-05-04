package adrianromanski.restschool.services;

import adrianromanski.restschool.domain.base_entity.person.Guardian;
import adrianromanski.restschool.domain.base_entity.person.Student;
import adrianromanski.restschool.domain.base_entity.enums.Gender;
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

import static adrianromanski.restschool.domain.base_entity.enums.FemaleName.ARIA;
import static adrianromanski.restschool.domain.base_entity.enums.FemaleName.CHARLOTTE;
import static adrianromanski.restschool.domain.base_entity.enums.Gender.FEMALE;
import static adrianromanski.restschool.domain.base_entity.enums.Gender.MALE;
import static adrianromanski.restschool.domain.base_entity.enums.LastName.*;
import static adrianromanski.restschool.domain.base_entity.enums.MaleName.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class GuardianServiceImplTest {

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

    GuardianDTO createEthanDTO() {
        GuardianDTO guardianDTO = GuardianDTO.builder().firstName(ETHAN.get()).lastName(HENDERSON.get())
                                                    .email(EMAIL).telephoneNumber(NUMBER).build();
        guardianDTO.setId(ID);
        return guardianDTO;
    }

    Guardian createEthan() {
        Guardian guardian = Guardian.builder().firstName(ETHAN.get()).lastName(HENDERSON.get()).email(EMAIL)
                                                    .telephoneNumber(NUMBER).dateOfBirth(LocalDate.of(1992, 11, 3)).build();

        Student sebastian = createSebastian();
        Student charlotte = createCharlotte();
        guardian.getStudents().addAll(Arrays.asList(sebastian, charlotte));
        sebastian.setGuardian(guardian);
        charlotte.setGuardian(guardian);
        guardian.setId(ID);
        return guardian;
    }

    Student createStudent(Long id, String firstName, String lastName, Gender gender) {
        Student student = Student.builder().firstName(firstName).lastName(lastName).gender(gender).build();
        student.setId(id);
        return student;
    }

    Student createSebastian() {
        return createStudent(2L, SEBASTIAN.get(), RODRIGUEZ.get(), MALE);
    }

    Student createCharlotte() {
        return createStudent(3L, CHARLOTTE.get(), HENDERSON.get(), FEMALE);
    }


    @DisplayName("[Happy Path], [Method] = getAllGuardians")
    @Test
    void getAllLegalGuardians() {
        List<Guardian> legalGuardians = Arrays.asList(createEthan(), createEthan(), createEthan());

        when(guardianRepository.findAll()).thenReturn(legalGuardians);

        List<GuardianDTO> legalGuardiansDTO = guardianService.getAllGuardians();

        assertEquals(legalGuardians.size(), legalGuardiansDTO.size());
    }

    @DisplayName("[Happy Path], [Method] = getGuardianByID")
    @Test
    void getGuardianByID() {
        Guardian legalGuardian = createEthan();

        when(guardianRepository.findById(ID)).thenReturn(Optional.of(legalGuardian));

        GuardianDTO legalGuardianDTO = guardianService.getGuardianByID(ID);

        assertEquals(legalGuardianDTO.getFirstName(), ETHAN.get());
        assertEquals(legalGuardianDTO.getLastName(), HENDERSON.get());
        assertEquals(legalGuardianDTO.getTelephoneNumber(), NUMBER);
        assertEquals(legalGuardianDTO.getEmail(), EMAIL);
        assertEquals(legalGuardianDTO.getId(), ID);

    }
    @DisplayName("[Happy Path], [Method] = getGuardianByFirstAndLastName")
    @Test
    void getLegalGuardianByFirstAndLastName() {
        Guardian legalGuardian = createEthan();

        when(guardianRepository.getGuardianByFirstNameAndLastName(anyString(), anyString()))
                .thenReturn(Optional.of(legalGuardian));

        GuardianDTO legalGuardianDTO = guardianService.getGuardianByFirstAndLastName(ETHAN.get(), COOPER.get());

        assertEquals(legalGuardianDTO.getFirstName(), ETHAN.get());
        assertEquals(legalGuardianDTO.getLastName(), HENDERSON.get());
        assertEquals(legalGuardianDTO.getTelephoneNumber(), NUMBER);
        assertEquals(legalGuardianDTO.getEmail(), EMAIL);
        assertEquals(legalGuardianDTO.getId(), ID);
    }

    @DisplayName("[Happy Path], [Method] = getGuardiansByAge")
    @Test
    void getGuardiansByAge() {
        List<Guardian> guardians = Arrays.asList(createEthan(), createEthan());

        when(guardianRepository.findAll()).thenReturn(guardians);

        Map<Long, List<GuardianDTO>> guardiansByAge = guardianService.getGuardiansByAge();

        assertEquals(guardiansByAge.size(), 1);
        assertTrue(guardiansByAge.containsKey(27L));
    }

    @DisplayName("[Happy Path], [Method] = getAllStudentsForGuardian")
    @Test
    void getAllStudentsForGuardian() {
        Guardian guardian = createEthan();

        when(studentRepository.findAll()).thenReturn(guardian.getStudents());

        List<StudentDTO> studentsDTO = guardianService.getAllStudentsForGuardian(guardian.getId());

        assertEquals(studentsDTO.size(), 2);
        assertEquals(studentsDTO.get(0).getFirstName(), SEBASTIAN.get());
        assertEquals(studentsDTO.get(1).getFirstName(), CHARLOTTE.get());
    }

    @DisplayName("[Happy Path], [Method] = createNewGuardian")
    @Test
    void createNewLegalGuardian() {
        Guardian guardian = createEthan();

        GuardianDTO guardianDTO = createEthanDTO();

        when(guardianRepository.findById(anyLong())).thenReturn(Optional.of(guardian));
        when(guardianRepository.save(any(Guardian.class))).thenReturn(guardian);

        GuardianDTO returnDTO = guardianService.createNewGuardian(guardianDTO);

        assertEquals(returnDTO.getFirstName(), ETHAN.get());
        assertEquals(returnDTO.getLastName(), HENDERSON.get());
        assertEquals(returnDTO.getTelephoneNumber(), NUMBER);
        assertEquals(returnDTO.getEmail(), EMAIL);
        assertEquals(returnDTO.getId(), ID);
    }

    @DisplayName("[Happy Path], [Method] = updateTeacher")
    @Test
    void updateLegalGuardianHappyPath() {
        Guardian guardian = createEthan();

        GuardianDTO guardianDTO = createEthanDTO();
        guardianDTO.setFirstName("Updated");

        when(guardianRepository.findById(anyLong())).thenReturn(Optional.of(guardian));
        when(guardianRepository.save(any(Guardian.class))).thenReturn(guardian);

        GuardianDTO returnDTO = guardianService.updateGuardian(guardianDTO, ID);

        assertEquals(returnDTO.getFirstName(), "Updated");
        assertEquals(returnDTO.getLastName(), HENDERSON.get());
        assertEquals(returnDTO.getTelephoneNumber(), NUMBER);
        assertEquals(returnDTO.getEmail(), EMAIL);
        assertEquals(returnDTO.getId(), ID);
    }

    @DisplayName("[Unhappy Path], [Method] = updateGuardian, [Reason] = Guardian with id 222 not found")
    @Test
    void updateLegalGuardianUnhappyPath() {
        GuardianDTO guardianDTO = createEthanDTO();

        Throwable ex = catchThrowable(() -> guardianService.updateGuardian(guardianDTO, 222L));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }

    @DisplayName("[Happy Path], [Method] = deleteGuardianByID")
    @Test
    void deleteLegalGuardianByIDHappyPath() {
        Guardian guardian = createEthan();

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

