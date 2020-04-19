package adrianromanski.restschool.mapper;

import adrianromanski.restschool.domain.base_entity.person.LegalGuardian;
import adrianromanski.restschool.model.base_entity.person.LegalGuardianDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LegalGuardianMapperTest {

    public static final String FIRST_NAME = "Bruce";
    public static final String LAST_NAME = "Wayne";
    public static final String EMAIL = "WayneEnterprise@Gotham.com";
    public static final String NUMBER = "543-352-332";
    public static final long ID = 1L;
    LegalGuardianMapper legalGuardianMapper = LegalGuardianMapper.INSTANCE;

    @Test
    void legalGuardianToLegalGuardianDTO() {
        LegalGuardian legalGuardian = new LegalGuardian();
        legalGuardian.setFirstName(FIRST_NAME);
        legalGuardian.setLastName(LAST_NAME);
        legalGuardian.setEmail(EMAIL);
        legalGuardian.setTelephoneNumber(NUMBER);
        legalGuardian.setId(ID);


        LegalGuardianDTO legalGuardianDTO = legalGuardianMapper.legalGuardianToLegalGuardianDTO(legalGuardian);

        assertEquals(legalGuardianDTO.getFirstName(), FIRST_NAME);
        assertEquals(legalGuardianDTO.getLastName(), LAST_NAME);
        assertEquals(legalGuardianDTO.getEmail(), EMAIL);
        assertEquals(legalGuardianDTO.getTelephoneNumber(), NUMBER);
        assertEquals(legalGuardianDTO.getId(), ID);
    }

    @Test
    void legalGuardianDTOToLegalGuardian() {
        LegalGuardianDTO legalGuardianDTO = new LegalGuardianDTO();
        legalGuardianDTO.setFirstName(FIRST_NAME);
        legalGuardianDTO.setLastName(LAST_NAME);
        legalGuardianDTO.setEmail(EMAIL);
        legalGuardianDTO.setTelephoneNumber(NUMBER);
        legalGuardianDTO.setId(ID);


        LegalGuardian legalGuardian = legalGuardianMapper.legalGuardianDTOToLegalGuardian(legalGuardianDTO);

        assertEquals(legalGuardian.getFirstName(), FIRST_NAME);
        assertEquals(legalGuardian.getLastName(), LAST_NAME);
        assertEquals(legalGuardian.getEmail(), EMAIL);
        assertEquals(legalGuardian.getTelephoneNumber(), NUMBER);
        assertEquals(legalGuardian.getId(), ID);
    }
}