package adrianromanski.restschool.mapper.base_entity;

import adrianromanski.restschool.domain.base_entity.contact.GuardianContact;
import adrianromanski.restschool.model.base_entity.contact.GuardianContactDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GuardianContactMapper {

    GuardianContactMapper INSTANCE = Mappers.getMapper(GuardianContactMapper.class);

    @Mapping(source = "guardian", target = "guardianDTO")
    GuardianContactDTO contactToContactDTO(GuardianContact contact);

    @Mapping(source = "guardianDTO", target = "guardian")
    GuardianContact contactDTOToContact(GuardianContactDTO contactDTO);
}

