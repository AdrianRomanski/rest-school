package adrianromanski.restschool.services;

import adrianromanski.restschool.domain.base_entity.person.Guardian;
import adrianromanski.restschool.mapper.person.GuardianMapper;
import adrianromanski.restschool.model.base_entity.person.GuardianDTO;
import adrianromanski.restschool.repositories.person.GuardianRepository;
import adrianromanski.restschool.services.person.guardian.GuardianService;
import adrianromanski.restschool.services.person.guardian.GuardianServiceImpl;
import org.junit.jupiter.api.*;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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

    @BeforeEach
    void beforeAll() {
        MockitoAnnotations.initMocks(this);

        guardianService = new GuardianServiceImpl(GuardianMapper.INSTANCE, guardianRepository);
    }

    GuardianDTO initLegalGuardianDTO() {
        GuardianDTO legalGuardianDTO = new GuardianDTO();
        legalGuardianDTO.setFirstName(FIRST_NAME);
        legalGuardianDTO.setLastName(LAST_NAME);
        legalGuardianDTO.setEmail(EMAIL);
        legalGuardianDTO.setTelephoneNumber(NUMBER);
        legalGuardianDTO.setId(ID);
        return legalGuardianDTO;
    }

    Guardian initLegalGuardian() {
        Guardian legalGuardian = new Guardian();
        legalGuardian.setFirstName(FIRST_NAME);
        legalGuardian.setLastName(LAST_NAME);
        legalGuardian.setEmail(EMAIL);
        legalGuardian.setTelephoneNumber(NUMBER);
        legalGuardian.setId(ID);
        return legalGuardian;
    }

    @Test
    void getAllLegalGuardians() {
        List<Guardian> legalGuardians = Arrays.asList(new Guardian(), new Guardian(), new Guardian());

        when(guardianRepository.findAll()).thenReturn(legalGuardians);

        List<GuardianDTO> legalGuardiansDTO = guardianService.getAllGuardians();

        assertEquals(legalGuardians.size(), legalGuardiansDTO.size());
    }

    @Test
    void getGuardianByID() {
        Guardian legalGuardian = initLegalGuardian();

        when(guardianRepository.findById(ID)).thenReturn(Optional.of(legalGuardian));

        GuardianDTO legalGuardianDTO = guardianService.getGuardianByID(ID);

        assertEquals(legalGuardianDTO.getFirstName(), FIRST_NAME);
        assertEquals(legalGuardianDTO.getLastName(), LAST_NAME);
        assertEquals(legalGuardianDTO.getTelephoneNumber(), NUMBER);
        assertEquals(legalGuardianDTO.getEmail(), EMAIL);
        assertEquals(legalGuardianDTO.getId(), ID);

    }

    @Test
    void getLegalGuardianByFirstAndLastName() {
        Guardian legalGuardian = initLegalGuardian();

        when(guardianRepository.getLegalGuardianByFirstNameAndLastName(FIRST_NAME, LAST_NAME))
                .thenReturn(Optional.of(legalGuardian));

        GuardianDTO legalGuardianDTO = guardianService.getGuardianByFirstAndLastName(FIRST_NAME, LAST_NAME);

        assertEquals(legalGuardianDTO.getFirstName(), FIRST_NAME);
        assertEquals(legalGuardianDTO.getLastName(), LAST_NAME);
        assertEquals(legalGuardianDTO.getTelephoneNumber(), NUMBER);
        assertEquals(legalGuardianDTO.getEmail(), EMAIL);
        assertEquals(legalGuardianDTO.getId(), ID);
    }

    @Test
    void createNewLegalGuardian() {
        Guardian legalGuardian = initLegalGuardian();

        GuardianDTO legalGuardianDTO = initLegalGuardianDTO();

        when(guardianRepository.save(any(Guardian.class))).thenReturn(legalGuardian);

        GuardianDTO returnDTO = guardianService.createNewGuardian(legalGuardianDTO);

        assertEquals(returnDTO.getFirstName(), FIRST_NAME);
        assertEquals(returnDTO.getLastName(), LAST_NAME);
        assertEquals(returnDTO.getTelephoneNumber(), NUMBER);
        assertEquals(returnDTO.getEmail(), EMAIL);
        assertEquals(returnDTO.getId(), ID);
    }

    @Test
    void updateLegalGuardian() {
        Guardian legalGuardian = initLegalGuardian();

        GuardianDTO legalGuardianDTO = initLegalGuardianDTO();

        when(guardianRepository.save(any(Guardian.class))).thenReturn(legalGuardian);

        GuardianDTO returnDTO = guardianService.updateGuardian(legalGuardianDTO, ID);

        assertEquals(returnDTO.getFirstName(), FIRST_NAME);
        assertEquals(returnDTO.getLastName(), LAST_NAME);
        assertEquals(returnDTO.getTelephoneNumber(), NUMBER);
        assertEquals(returnDTO.getEmail(), EMAIL);
        assertEquals(returnDTO.getId(), ID);
    }

    @Test
    void deleteLegalGuardianByIDHappy() {
        Guardian legalGuardian = initLegalGuardian();

        guardianRepository.deleteById(legalGuardian.getId());

        verify(guardianRepository, times(1)).deleteById(ID);
    }
}

