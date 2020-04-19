package adrianromanski.restschool.mapper;

import adrianromanski.restschool.domain.base_entity.person.Guardian;
import adrianromanski.restschool.model.base_entity.person.GuardianDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GuardianMapper {

    GuardianMapper INSTANCE  = Mappers.getMapper(GuardianMapper.class);

    GuardianDTO guardianToGuardianDTO(Guardian Guardian);

    Guardian guardianDTOToGuardian(GuardianDTO GuardianDTO);

}
