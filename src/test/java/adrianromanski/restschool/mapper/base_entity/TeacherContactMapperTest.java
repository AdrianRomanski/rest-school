package adrianromanski.restschool.mapper.base_entity;

import adrianromanski.restschool.domain.base_entity.contact.TeacherContact;
import adrianromanski.restschool.model.base_entity.contact.TeacherContactDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TeacherContactMapperTest {

    public static final String EMAIL = "Someemail@gmail.com";
    public static final String EMERGENCY_NUMBER = "22020-22";
    public static final String NUMBER = "22020-22-532";
    TeacherContactMapper mapper = TeacherContactMapper.INSTANCE;

    @Test
    void contactToContactDTO() {
        TeacherContact contact = TeacherContact.builder().email(EMAIL).emergencyNumber(EMERGENCY_NUMBER).telephoneNumber(NUMBER).build();

        TeacherContactDTO dto = mapper.contactToContactDTO(contact);

        assertEquals(dto.getEmail(), EMAIL);
        assertEquals(dto.getEmergencyNumber(), EMERGENCY_NUMBER);
        assertEquals(dto.getTelephoneNumber(), NUMBER);
    }

    @Test
    void contactDTOToContact() {
        TeacherContactDTO dto = TeacherContactDTO.builder().email(EMAIL).emergencyNumber(EMERGENCY_NUMBER).telephoneNumber(NUMBER).build();

        TeacherContact contact = mapper.contactDTOToContact(dto);

        assertEquals(contact.getEmail(), EMAIL);
        assertEquals(contact.getEmergencyNumber(), EMERGENCY_NUMBER);
        assertEquals(contact.getTelephoneNumber(), NUMBER);
    }
}