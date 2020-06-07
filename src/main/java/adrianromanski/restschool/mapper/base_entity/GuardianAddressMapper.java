package adrianromanski.restschool.mapper.base_entity;

import adrianromanski.restschool.domain.base_entity.address.GuardianAddress;
import adrianromanski.restschool.model.base_entity.address.GuardianAddressDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GuardianAddressMapper {


    GuardianAddressMapper INSTANCE = Mappers.getMapper(GuardianAddressMapper.class);

    GuardianAddressDTO addressToAddressDTO(GuardianAddress guardianAddress);

    GuardianAddress addressDTOToAddress(GuardianAddressDTO guardianAddressDTO);
}
