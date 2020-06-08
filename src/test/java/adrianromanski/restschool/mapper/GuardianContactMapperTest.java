package adrianromanski.restschool.mapper;

import adrianromanski.restschool.domain.base_entity.contact.GuardianContact;
import adrianromanski.restschool.mapper.base_entity.GuardianContactMapper;
import adrianromanski.restschool.model.base_entity.contact.GuardianContactDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GuardianContactMapperTest {

    public static final String EMAIL = "Someemail@gmail.com";
    public static final String EMERGENCY_NUMBER = "22020-22";
    public static final String NUMBER = "22020-22-532";

    GuardianContactMapper mapper = GuardianContactMapper.INSTANCE;

    @Test
    void contactToContactDTO() {
        GuardianContact contact = GuardianContact.builder().email(EMAIL).emergencyNumber(EMERGENCY_NUMBER).telephoneNumber(NUMBER).build();

        GuardianContactDTO contactDTO = mapper.contactToContactDTO(contact);

        assertEquals(contactDTO.getEmail(), EMAIL);
        assertEquals(contactDTO.getEmergencyNumber(), EMERGENCY_NUMBER);
        assertEquals(contactDTO.getTelephoneNumber(), NUMBER);

    }

    @Test
    void contactDTOToContact() {
        GuardianContactDTO contactDTO = GuardianContactDTO.builder().email(EMAIL).emergencyNumber(EMERGENCY_NUMBER).telephoneNumber(NUMBER).build();

        GuardianContact contact = mapper.contactDTOToContact(contactDTO);

        assertEquals(contact.getEmail(), EMAIL);
        assertEquals(contact.getEmergencyNumber(), EMERGENCY_NUMBER);
        assertEquals(contact.getTelephoneNumber(), NUMBER);
    }
}