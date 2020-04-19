package adrianromanski.restschool.mapper;

import adrianromanski.restschool.domain.base_entity.person.Guardian;
import adrianromanski.restschool.model.base_entity.person.GuardianDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GuardianMapperTest {

    public static final String FIRST_NAME = "Bruce";
    public static final String LAST_NAME = "Wayne";
    public static final String EMAIL = "WayneEnterprise@Gotham.com";
    public static final String NUMBER = "543-352-332";
    public static final long ID = 1L;
    GuardianMapper guardianMapper = GuardianMapper.INSTANCE;

    @Test
    void legalGuardianToLegalGuardianDTO() {
        Guardian guardian = new Guardian();
        guardian.setFirstName(FIRST_NAME);
        guardian.setLastName(LAST_NAME);
        guardian.setEmail(EMAIL);
        guardian.setTelephoneNumber(NUMBER);
        guardian.setId(ID);


        GuardianDTO guardianDTO = guardianMapper.guardianToGuardianDTO(guardian);

        assertEquals(guardianDTO.getFirstName(), FIRST_NAME);
        assertEquals(guardianDTO.getLastName(), LAST_NAME);
        assertEquals(guardianDTO.getEmail(), EMAIL);
        assertEquals(guardianDTO.getTelephoneNumber(), NUMBER);
        assertEquals(guardianDTO.getId(), ID);
    }

    @Test
    void legalGuardianDTOToLegalGuardian() {
        GuardianDTO guardianDTO = new GuardianDTO();
        guardianDTO.setFirstName(FIRST_NAME);
        guardianDTO.setLastName(LAST_NAME);
        guardianDTO.setEmail(EMAIL);
        guardianDTO.setTelephoneNumber(NUMBER);
        guardianDTO.setId(ID);


        Guardian guardian = guardianMapper.guardianDTOToGuardian(guardianDTO);

        assertEquals(guardian.getFirstName(), FIRST_NAME);
        assertEquals(guardian.getLastName(), LAST_NAME);
        assertEquals(guardian.getEmail(), EMAIL);
        assertEquals(guardian.getTelephoneNumber(), NUMBER);
        assertEquals(guardian.getId(), ID);
    }
}