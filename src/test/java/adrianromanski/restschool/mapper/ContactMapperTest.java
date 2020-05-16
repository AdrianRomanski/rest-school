package adrianromanski.restschool.mapper;

import adrianromanski.restschool.domain.base_entity.contact.StudentContact;
import adrianromanski.restschool.domain.person.Student;
import adrianromanski.restschool.mapper.base_entity.StudentContactMapper;
import adrianromanski.restschool.model.base_entity.contact.StudentContactDTO;
import adrianromanski.restschool.model.person.StudentDTO;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

import static adrianromanski.restschool.domain.enums.Gender.MALE;
import static adrianromanski.restschool.domain.enums.LastName.HENDERSON;
import static adrianromanski.restschool.domain.enums.MaleName.*;
import static org.junit.jupiter.api.Assertions.*;

class ContactMapperTest {

    public static final String EMAIL = "HotHotSteve@Gmail.com";
    public static final String TELEPHONE_NUMBER = "220-4221-22";
    public static final LocalDate DATE_OF_BIRTH = LocalDate.now();


    StudentContactMapper contactMapper = StudentContactMapper.INSTANCE;


    @Test
    void contactToContactDTO() {
        StudentContact contact = StudentContact.builder().email(EMAIL).telephoneNumber(TELEPHONE_NUMBER).build();;
        Student student = Student.builder().firstName(ETHAN.get()).lastName(HENDERSON.get()).dateOfBirth(DATE_OF_BIRTH).gender(MALE).build();
        contact.setStudent(student);

        StudentContactDTO contactDTO = contactMapper.contactToContactDTO(contact);

        assertEquals(contactDTO.getEmail(), EMAIL);
        assertEquals(contactDTO.getTelephoneNumber(), TELEPHONE_NUMBER);
        assertEquals(contactDTO.getStudentDTO().getFirstName(), ETHAN.get());
        assertEquals(contactDTO.getStudentDTO().getLastName(), HENDERSON.get());
        assertEquals(contactDTO.getStudentDTO().getGender(), MALE);
        assertEquals(contactDTO.getStudentDTO().getDateOfBirth(), DATE_OF_BIRTH);
    }

    @Test
    void contactDTOToContact() {
        StudentContactDTO contactDTO = StudentContactDTO.builder().email(EMAIL).telephoneNumber(TELEPHONE_NUMBER).build();
        StudentDTO studentDTO = StudentDTO.builder().firstName(ETHAN.get()).lastName(HENDERSON.get()).dateOfBirth(DATE_OF_BIRTH).gender(MALE).build();
        contactDTO.setStudentDTO(studentDTO);

        StudentContact contact = contactMapper.contactDTOToContact(contactDTO);

        assertEquals(contact.getEmail(), EMAIL);
        assertEquals(contact.getTelephoneNumber(), TELEPHONE_NUMBER);
        assertEquals(contact.getStudent().getFirstName(), ETHAN.get());
        assertEquals(contact.getStudent().getLastName(), HENDERSON.get());
        assertEquals(contact.getStudent().getGender(), MALE);
        assertEquals(contact.getStudent().getDateOfBirth(), DATE_OF_BIRTH);
    }
}