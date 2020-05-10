package adrianromanski.restschool.mapper.base_entity;

import adrianromanski.restschool.domain.base_entity.Address;
import adrianromanski.restschool.model.base_entity.AddressDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AddressMapper {

    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

    AddressDTO addressToAddressDTO(Address address);

    Address addressDTOToAddress(AddressDTO addressDTO);
}
