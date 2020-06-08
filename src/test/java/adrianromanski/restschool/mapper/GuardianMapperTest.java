package adrianromanski.restschool.mapper;

import adrianromanski.restschool.domain.base_entity.address.GuardianAddress;
import adrianromanski.restschool.domain.base_entity.contact.GuardianContact;
import adrianromanski.restschool.domain.person.Guardian;
import adrianromanski.restschool.mapper.person.GuardianMapper;
import adrianromanski.restschool.model.base_entity.address.GuardianAddressDTO;
import adrianromanski.restschool.model.base_entity.contact.GuardianContactDTO;
import adrianromanski.restschool.model.person.GuardianDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GuardianMapperTest {

    public static final String FIRST_NAME = "Bruce";
    public static final String LAST_NAME = "Wayne";
    public static final long ID = 1L;
    public static final String EMAIL = "Someemail@gmail.com";
    public static final String EMERGENCY_NUMBER = "22020-22";
    public static final String NUMBER = "22020-22-532";
    public static final String COUNTRY = "Poland";
    public static final String CITY = "Warsaw";
    public static final String POSTAL_CODE = "22-44";
    public static final String STREET_NAME = "Sesame";

    GuardianMapper guardianMapper = GuardianMapper.INSTANCE;

    @Test
    void legalGuardianToLegalGuardianDTO() {
        Guardian guardian = new Guardian();
        guardian.setFirstName(FIRST_NAME);
        guardian.setLastName(LAST_NAME);
        guardian.setId(ID);
        GuardianContact contact = GuardianContact.builder().email(EMAIL).emergencyNumber(EMERGENCY_NUMBER).telephoneNumber(NUMBER).build();
        guardian.setContact(contact);
        contact.setGuardian(guardian);

        GuardianAddress address = GuardianAddress.builder().country(COUNTRY).city(CITY).postalCode(POSTAL_CODE).streetName(STREET_NAME).build();
        guardian.setAddress(address);
        address.setGuardian(guardian);

        GuardianDTO guardianDTO = guardianMapper.guardianToGuardianDTO(guardian);

        assertEquals(guardianDTO.getFirstName(), FIRST_NAME);
        assertEquals(guardianDTO.getLastName(), LAST_NAME);
        assertEquals(guardianDTO.getId(), ID);

        assertEquals(guardianDTO.getContactDTO().getEmail(), EMAIL);
        assertEquals(guardianDTO.getContactDTO().getEmergencyNumber(), EMERGENCY_NUMBER);
        assertEquals(guardianDTO.getContactDTO().getTelephoneNumber(), NUMBER);

        assertEquals(guardianDTO.getAddressDTO().getCountry(), COUNTRY);
        assertEquals(guardianDTO.getAddressDTO().getCity(), CITY);
        assertEquals(guardianDTO.getAddressDTO().getPostalCode(), POSTAL_CODE);
        assertEquals(guardianDTO.getAddressDTO().getStreetName(), STREET_NAME);
    }

    @Test
    void legalGuardianDTOToLegalGuardian() {
        GuardianDTO guardianDTO = new GuardianDTO();
        guardianDTO.setFirstName(FIRST_NAME);
        guardianDTO.setLastName(LAST_NAME);
        guardianDTO.setId(ID);
        GuardianContactDTO contactDTO = GuardianContactDTO.builder().email(EMAIL).emergencyNumber(EMERGENCY_NUMBER).telephoneNumber(NUMBER).build();
        guardianDTO.setContactDTO(contactDTO);
        contactDTO.setGuardianDTO(guardianDTO);

        GuardianAddressDTO addressDTO = GuardianAddressDTO.builder().country(COUNTRY).city(CITY).postalCode(POSTAL_CODE).streetName(STREET_NAME).build();
        guardianDTO.setAddressDTO(addressDTO);
        addressDTO.setGuardianDTO(guardianDTO);


        Guardian guardian = guardianMapper.guardianDTOToGuardian(guardianDTO);

        assertEquals(guardian.getFirstName(), FIRST_NAME);
        assertEquals(guardian.getLastName(), LAST_NAME);
        assertEquals(guardian.getId(), ID);

        assertEquals(guardian.getContact().getEmail(), EMAIL);
        assertEquals(guardian.getContact().getEmergencyNumber(), EMERGENCY_NUMBER);
        assertEquals(guardian.getContact().getTelephoneNumber(), NUMBER);

        assertEquals(guardian.getAddress().getCountry(), COUNTRY);
        assertEquals(guardian.getAddress().getCity(), CITY);
        assertEquals(guardian.getAddress().getPostalCode(), POSTAL_CODE);
        assertEquals(guardian.getAddress().getStreetName(), STREET_NAME);

    }
}