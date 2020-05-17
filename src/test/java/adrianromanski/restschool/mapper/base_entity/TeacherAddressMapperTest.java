package adrianromanski.restschool.mapper.base_entity;

import adrianromanski.restschool.domain.base_entity.address.TeacherAddress;
import adrianromanski.restschool.domain.enums.LastName;
import adrianromanski.restschool.domain.enums.MaleName;
import adrianromanski.restschool.domain.person.Teacher;
import adrianromanski.restschool.model.base_entity.address.TeacherAddressDTO;
import adrianromanski.restschool.model.person.TeacherDTO;
import org.junit.jupiter.api.Test;

import static adrianromanski.restschool.domain.enums.LastName.*;
import static adrianromanski.restschool.domain.enums.MaleName.*;
import static org.junit.jupiter.api.Assertions.*;

class TeacherAddressMapperTest {

    public static final String COUNTRY = "Poland";
    public static final String CITY = "Warsaw";
    public static final String STREET_NAME = "Sesame";
    public static final String POSTAL_CODE = "22-44";

    TeacherAddressMapper teacherAddressMapper = TeacherAddressMapper.INSTANCE;

    @Test
    void teacherAddressToTeacherAddressDTO() {
        TeacherAddress teacherAddress = TeacherAddress.builder().country(COUNTRY).city(CITY).streetName(STREET_NAME).postalCode(POSTAL_CODE).build();
        Teacher teacher = Teacher.builder().firstName(ETHAN.get()).lastName(COOPER.get()).build();
        teacherAddress.setTeacher(teacher);
        teacher.setAddress(teacherAddress);

        TeacherAddressDTO teacherAddressDTO = teacherAddressMapper.teacherAddressToTeacherAddressDTO(teacherAddress);

        assertEquals(teacherAddressDTO.getCountry(), COUNTRY);
        assertEquals(teacherAddressDTO.getCity(), CITY);
        assertEquals(teacherAddressDTO.getStreetName(), STREET_NAME);
        assertEquals(teacherAddressDTO.getPostalCode(), POSTAL_CODE);
    }


    @Test
    void teacherAddressDTOToTeacherAddress() {
        TeacherAddressDTO teacherAddressDTO = TeacherAddressDTO.builder().country(COUNTRY).city(CITY).streetName(STREET_NAME).postalCode(POSTAL_CODE).build();
        TeacherDTO teacherDTO = TeacherDTO.builder().firstName(ETHAN.get()).lastName(COOPER.get()).build();
        teacherAddressDTO.setTeacherDTO(teacherDTO);
        teacherDTO.setAddressDTO(teacherAddressDTO);

        TeacherAddress teacherAddress = teacherAddressMapper.teacherAddressDTOToTeacherAddress(teacherAddressDTO);

        assertEquals(teacherAddress.getCountry(), COUNTRY);
        assertEquals(teacherAddress.getCity(), CITY);
        assertEquals(teacherAddress.getStreetName(), STREET_NAME);
        assertEquals(teacherAddress.getPostalCode(), POSTAL_CODE);
    }
}