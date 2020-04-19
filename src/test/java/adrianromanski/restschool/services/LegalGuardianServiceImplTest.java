package adrianromanski.restschool.services;

import adrianromanski.restschool.domain.base_entity.person.LegalGuardian;
import adrianromanski.restschool.mapper.LegalGuardianMapper;
import adrianromanski.restschool.model.base_entity.person.LegalGuardianDTO;
import adrianromanski.restschool.repositories.LegalGuardianRepository;
import adrianromanski.restschool.services.legal_guardian.LegalGuardianService;
import adrianromanski.restschool.services.legal_guardian.LegalGuardianServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class LegalGuardianServiceImplTest {

    public static final String FIRST_NAME = "Bruce";
    public static final String LAST_NAME = "Wayne";
    public static final String EMAIL = "WayneEnterprise@Gotham.com";
    public static final String NUMBER = "543-352-332";
    public static final long ID = 1L;


    LegalGuardianService legalGuardianService;


    @Mock
    LegalGuardianRepository legalGuardianRepository;

    @BeforeEach
    void beforeAll() {
        MockitoAnnotations.initMocks(this);

        legalGuardianService = new LegalGuardianServiceImpl(LegalGuardianMapper.INSTANCE,legalGuardianRepository);
    }

    LegalGuardianDTO initLegalGuardianDTO() {
        LegalGuardianDTO legalGuardianDTO = new LegalGuardianDTO();
        legalGuardianDTO.setFirstName(FIRST_NAME);
        legalGuardianDTO.setLastName(LAST_NAME);
        legalGuardianDTO.setEmail(EMAIL);
        legalGuardianDTO.setTelephoneNumber(NUMBER);
        legalGuardianDTO.setId(ID);
        return legalGuardianDTO;
    }

    LegalGuardian initLegalGuardian() {
        LegalGuardian legalGuardian = new LegalGuardian();
        legalGuardian.setFirstName(FIRST_NAME);
        legalGuardian.setLastName(LAST_NAME);
        legalGuardian.setEmail(EMAIL);
        legalGuardian.setTelephoneNumber(NUMBER);
        legalGuardian.setId(ID);
        return legalGuardian;
    }

    @Test
    void getAllLegalGuardians() {
        List<LegalGuardian> legalGuardians = Arrays.asList(new LegalGuardian(), new LegalGuardian(), new LegalGuardian());

        when(legalGuardianRepository.findAll()).thenReturn(legalGuardians);

        List<LegalGuardianDTO> legalGuardiansDTO = legalGuardianService.getAllLegalGuardians();

        assertEquals(legalGuardians.size(), legalGuardiansDTO.size());
    }

    @Test
    void getGuardianByID() {
        LegalGuardian legalGuardian = initLegalGuardian();

        when(legalGuardianRepository.findById(ID)).thenReturn(Optional.of(legalGuardian));

        LegalGuardianDTO legalGuardianDTO = legalGuardianService.getGuardianByID(ID);

        assertEquals(legalGuardianDTO.getFirstName(), FIRST_NAME);
        assertEquals(legalGuardianDTO.getLastName(), LAST_NAME);
        assertEquals(legalGuardianDTO.getTelephoneNumber(), NUMBER);
        assertEquals(legalGuardianDTO.getEmail(), EMAIL);
        assertEquals(legalGuardianDTO.getId(), ID);

    }

    @Test
    void getLegalGuardianByFirstAndLastName() {
        LegalGuardian legalGuardian = initLegalGuardian();

        when(legalGuardianRepository.getLegalGuardianByFirstNameAndLastName(FIRST_NAME, LAST_NAME))
                                                                        .thenReturn(Optional.of(legalGuardian));

        LegalGuardianDTO legalGuardianDTO = legalGuardianService.getLegalGuardianByFirstAndLastName(FIRST_NAME, LAST_NAME);

        assertEquals(legalGuardianDTO.getFirstName(), FIRST_NAME);
        assertEquals(legalGuardianDTO.getLastName(), LAST_NAME);
        assertEquals(legalGuardianDTO.getTelephoneNumber(), NUMBER);
        assertEquals(legalGuardianDTO.getEmail(), EMAIL);
        assertEquals(legalGuardianDTO.getId(), ID);
    }

    @Test
    void createNewLegalGuardian() {
        LegalGuardian legalGuardian = initLegalGuardian();

        LegalGuardianDTO legalGuardianDTO = initLegalGuardianDTO();

        when(legalGuardianRepository.save(any(LegalGuardian.class))).thenReturn(legalGuardian);

        LegalGuardianDTO returnDTO = legalGuardianService.createNewLegalGuardian(legalGuardianDTO);

        assertEquals(returnDTO.getFirstName(), FIRST_NAME);
        assertEquals(returnDTO.getLastName(), LAST_NAME);
        assertEquals(returnDTO.getTelephoneNumber(), NUMBER);
        assertEquals(returnDTO.getEmail(), EMAIL);
        assertEquals(returnDTO.getId(), ID);
    }

    @Test
    void updateLegalGuardian() {
        LegalGuardian legalGuardian = initLegalGuardian();

        LegalGuardianDTO legalGuardianDTO = initLegalGuardianDTO();

        when(legalGuardianRepository.save(any(LegalGuardian.class))).thenReturn(legalGuardian);

        LegalGuardianDTO returnDTO = legalGuardianService.updateLegalGuardian(legalGuardianDTO, ID);

        assertEquals(returnDTO.getFirstName(), FIRST_NAME);
        assertEquals(returnDTO.getLastName(), LAST_NAME);
        assertEquals(returnDTO.getTelephoneNumber(), NUMBER);
        assertEquals(returnDTO.getEmail(), EMAIL);
        assertEquals(returnDTO.getId(), ID);
    }

    @Test
    void deleteLegalGuardianByID() {
        legalGuardianRepository.deleteById(ID);

        verify(legalGuardianRepository, times(1)).deleteById(ID);
    }
}