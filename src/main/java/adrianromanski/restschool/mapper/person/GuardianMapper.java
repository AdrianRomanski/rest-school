package adrianromanski.restschool.mapper.person;

import adrianromanski.restschool.domain.person.Guardian;
import adrianromanski.restschool.model.person.GuardianDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GuardianMapper {

    GuardianMapper INSTANCE  = Mappers.getMapper(GuardianMapper.class);

    GuardianDTO guardianToGuardianDTO(Guardian Guardian);

    Guardian guardianDTOToGuardian(GuardianDTO GuardianDTO);

}
