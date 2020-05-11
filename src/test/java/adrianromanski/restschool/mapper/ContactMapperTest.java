package adrianromanski.restschool.mapper;

import adrianromanski.restschool.domain.base_entity.Address;
import adrianromanski.restschool.domain.base_entity.Contact;
import adrianromanski.restschool.domain.base_entity.enums.Gender;
import adrianromanski.restschool.domain.base_entity.enums.LastName;
import adrianromanski.restschool.domain.base_entity.enums.MaleName;
import adrianromanski.restschool.domain.base_entity.person.Student;
import adrianromanski.restschool.mapper.base_entity.ContactMapper;
import adrianromanski.restschool.model.base_entity.AddressDTO;
import adrianromanski.restschool.model.base_entity.ContactDTO;
import adrianromanski.restschool.model.base_entity.person.StudentDTO;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static adrianromanski.restschool.domain.base_entity.enums.Gender.MALE;
import static adrianromanski.restschool.domain.base_entity.enums.LastName.HENDERSON;
import static adrianromanski.restschool.domain.base_entity.enums.MaleName.*;
import static org.junit.jupiter.api.Assertions.*;

class ContactMapperTest {

    public static final String EMAIL = "HotHotSteve@Gmail.com";
    public static final String TELEPHONE_NUMBER = "220-4221-22";
    public static final String STREET_NAME = "Sunken Quarter";
    public static final String CITY = "Yalahar";
    public static final String COUNTRY = "Tibia";
    public static final String POSTAL_CODE = "22-444";
    public static final LocalDate DATE_OF_BIRTH = LocalDate.now();


    ContactMapper contactMapper = ContactMapper.INSTANCE;


    @Test
    void contactToContactDTO() {
        Contact contact = Contact.builder().email(EMAIL).telephoneNumber(TELEPHONE_NUMBER).build();
        Address address = Address.builder().streetName(STREET_NAME).city(CITY)
                .country(COUNTRY).postalCode(POSTAL_CODE).build();
        contact.setAddress(address);
        Student student = Student.builder().firstName(ETHAN.get()).lastName(HENDERSON.get()).dateOfBirth(DATE_OF_BIRTH).gender(MALE).build();
        contact.setStudent(student);

        ContactDTO contactDTO = contactMapper.contactToContactDTO(contact);

        assertEquals(contactDTO.getEmail(), EMAIL);
        assertEquals(contactDTO.getTelephoneNumber(), TELEPHONE_NUMBER);
        assertEquals(contactDTO.getAddressDTO().getPostalCode(), POSTAL_CODE);
        assertEquals(contactDTO.getAddressDTO().getStreetName(), STREET_NAME);
        assertEquals(contactDTO.getAddressDTO().getCity(), CITY);
        assertEquals(contactDTO.getAddressDTO().getCountry(), COUNTRY);
        assertEquals(contactDTO.getStudentDTO().getFirstName(), ETHAN.get());
        assertEquals(contactDTO.getStudentDTO().getLastName(), HENDERSON.get());
        assertEquals(contactDTO.getStudentDTO().getGender(), MALE);
        assertEquals(contactDTO.getStudentDTO().getDateOfBirth(), DATE_OF_BIRTH);
    }

    @Test
    void contactDTOToContact() {
        ContactDTO contactDTO = ContactDTO.builder().email(EMAIL).telephoneNumber(TELEPHONE_NUMBER).build();
        AddressDTO addressDTO = AddressDTO.builder().streetName(STREET_NAME).city(CITY)
                .country(COUNTRY).postalCode(POSTAL_CODE).build();
        contactDTO.setAddressDTO(addressDTO);
        StudentDTO studentDTO = StudentDTO.builder().firstName(ETHAN.get()).lastName(HENDERSON.get()).dateOfBirth(DATE_OF_BIRTH).gender(MALE).build();
        contactDTO.setStudentDTO(studentDTO);

        Contact contact = contactMapper.contactDTOToContact(contactDTO);

        assertEquals(contact.getEmail(), EMAIL);
        assertEquals(contact.getTelephoneNumber(), TELEPHONE_NUMBER);
        assertEquals(contact.getAddress().getPostalCode(), POSTAL_CODE);
        assertEquals(contact.getAddress().getStreetName(), STREET_NAME);
        assertEquals(contact.getAddress().getCity(), CITY);
        assertEquals(contact.getAddress().getCountry(), COUNTRY);
        assertEquals(contact.getStudent().getFirstName(), ETHAN.get());
        assertEquals(contact.getStudent().getLastName(), HENDERSON.get());
        assertEquals(contact.getStudent().getGender(), MALE);
        assertEquals(contact.getStudent().getDateOfBirth(), DATE_OF_BIRTH);
    }
}