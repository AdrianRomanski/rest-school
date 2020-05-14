package adrianromanski.restschool.mapper.base_entity;

import adrianromanski.restschool.domain.base_entity.Address;
import adrianromanski.restschool.model.base_entity.AddressDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AddressMapper {

    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

    @Mapping(source = "student", target = "studentDTO")
    AddressDTO addressToAddressDTO(Address address);

    @Mapping(source = "studentDTO", target = "student")
    Address addressDTOToAddress(AddressDTO addressDTO);
}
