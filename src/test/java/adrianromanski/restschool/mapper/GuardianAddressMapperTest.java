package adrianromanski.restschool.mapper;


import adrianromanski.restschool.domain.base_entity.address.GuardianAddress;
import adrianromanski.restschool.domain.person.Guardian;
import adrianromanski.restschool.mapper.base_entity.GuardianAddressMapper;
import adrianromanski.restschool.model.base_entity.address.GuardianAddressDTO;
import adrianromanski.restschool.model.person.GuardianDTO;
import org.junit.jupiter.api.Test;

import static adrianromanski.restschool.domain.enums.MaleName.ETHAN;
import static org.junit.jupiter.api.Assertions.assertEquals;


class GuardianAddressMapperTest {

    public static final String COUNTRY = "Poland";
    public static final String CITY = "Warsaw";
    public static final String POSTAL_CODE = "22-44";
    public static final String STREET_NAME = "Sesame";
    public static final long ID = 1L;
    GuardianAddressMapper mapper = GuardianAddressMapper.INSTANCE;


    @Test
    void addressToAddressDTO() {
        GuardianAddress address = GuardianAddress.builder().country(COUNTRY).city(CITY)
                                                            .postalCode(POSTAL_CODE).streetName(STREET_NAME).build();
        address.setId(ID);

        Guardian guardian = Guardian.builder().firstName(ETHAN.get()).build();
        guardian.setId(ID);
        guardian.setAddress(address);
        address.setGuardian(guardian);


        GuardianAddressDTO addressDTO = mapper.addressToAddressDTO(address);

        assertEquals(addressDTO.getCountry(), COUNTRY);
        assertEquals(addressDTO.getCity(), CITY);
        assertEquals(addressDTO.getPostalCode(), POSTAL_CODE);
        assertEquals(addressDTO.getStreetName(), STREET_NAME);
    }


    @Test
    void addressDTOToAddress() {
        GuardianAddressDTO addressDTO = GuardianAddressDTO.builder().country(COUNTRY).city(CITY)
                .postalCode(POSTAL_CODE).streetName(STREET_NAME).build();

        GuardianDTO guardianDTO = GuardianDTO.builder().firstName(ETHAN.get()).build();
        addressDTO.setGuardianDTO(guardianDTO);
        guardianDTO.setAddressDTO(addressDTO);

        GuardianAddress address = mapper.addressDTOToAddress(addressDTO);

        assertEquals(address.getCountry(), COUNTRY);
        assertEquals(address.getCity(), CITY);
        assertEquals(address.getPostalCode(), POSTAL_CODE);
        assertEquals(address.getStreetName(), STREET_NAME);
    }
}