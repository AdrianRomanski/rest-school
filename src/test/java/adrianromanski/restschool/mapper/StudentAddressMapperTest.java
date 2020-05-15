package adrianromanski.restschool.mapper;

import adrianromanski.restschool.domain.base_entity.address.Address;
import adrianromanski.restschool.domain.base_entity.address.StudentAddress;
import adrianromanski.restschool.mapper.base_entity.StudentAddressMapper;
import adrianromanski.restschool.model.base_entity.address.AddressDTO;
import adrianromanski.restschool.model.base_entity.address.StudentAddressDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StudentAddressMapperTest {

    public static final String STREET_NAME = "Sunken Quarter";
    public static final String CITY = "Yalahar";
    public static final String COUNTRY = "Tibia";
    public static final String POSTAL_CODE = "22-444";
    StudentAddressMapper studentAddressMapper = StudentAddressMapper.INSTANCE;

    @Test
    public void addressToAddressDTO() {
        StudentAddress address = StudentAddress.builder().streetName(STREET_NAME).city(CITY)
                                           .country(COUNTRY).postalCode(POSTAL_CODE).build();

        AddressDTO returnDTO = studentAddressMapper.addressToAddressDTO(address);

        assertEquals(returnDTO.getStreetName(), STREET_NAME);
        assertEquals(returnDTO.getCity(), CITY);
        assertEquals(returnDTO.getCountry(), COUNTRY);
        assertEquals(returnDTO.getPostalCode(), POSTAL_CODE);
    }

    @Test
    public void addressDTOToAddress() {
        StudentAddressDTO addressDTO = StudentAddressDTO.builder().streetName(STREET_NAME).city(CITY)
                .country(COUNTRY).postalCode(POSTAL_CODE).build();

        Address returnAddress = studentAddressMapper.addressDTOToAddress(addressDTO);

        assertEquals(returnAddress.getStreetName(), STREET_NAME);
        assertEquals(returnAddress.getCity(), CITY);
        assertEquals(returnAddress.getCountry(), COUNTRY);
        assertEquals(returnAddress.getPostalCode(), POSTAL_CODE);
    }

}
