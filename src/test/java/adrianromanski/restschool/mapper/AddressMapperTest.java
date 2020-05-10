package adrianromanski.restschool.mapper;

import adrianromanski.restschool.domain.base_entity.Address;
import adrianromanski.restschool.mapper.base_entity.AddressMapper;
import adrianromanski.restschool.model.base_entity.AddressDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddressMapperTest {

    public static final String STREET_NAME = "Sunken Quarter";
    public static final String CITY = "Yalahar";
    public static final String COUNTRY = "Tibia";
    public static final String POSTAL_CODE = "22-444";
    AddressMapper addressMapper = AddressMapper.INSTANCE;

    @Test
    public void addressToAddressDTO() {
        Address address = Address.builder().streetName(STREET_NAME).city(CITY)
                                           .country(COUNTRY).postalCode(POSTAL_CODE).build();

        AddressDTO returnDTO = addressMapper.addressToAddressDTO(address);

        assertEquals(returnDTO.getStreetName(), STREET_NAME);
        assertEquals(returnDTO.getCity(), CITY);
        assertEquals(returnDTO.getCountry(), COUNTRY);
        assertEquals(returnDTO.getPostalCode(), POSTAL_CODE);
    }

    @Test
    public void addressDTOToAddress() {
        AddressDTO addressDTO = AddressDTO.builder().streetName(STREET_NAME).city(CITY)
                .country(COUNTRY).postalCode(POSTAL_CODE).build();

        Address returnAddress = addressMapper.addressDTOToAddress(addressDTO);

        assertEquals(returnAddress.getStreetName(), STREET_NAME);
        assertEquals(returnAddress.getCity(), CITY);
        assertEquals(returnAddress.getCountry(), COUNTRY);
        assertEquals(returnAddress.getPostalCode(), POSTAL_CODE);
    }

}
