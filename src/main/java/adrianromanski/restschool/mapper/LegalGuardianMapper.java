package adrianromanski.restschool.mapper;

import adrianromanski.restschool.domain.base_entity.person.LegalGuardian;
import adrianromanski.restschool.model.base_entity.person.LegalGuardianDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LegalGuardianMapper {

    LegalGuardianMapper INSTANCE  = Mappers.getMapper(LegalGuardianMapper.class);

    LegalGuardianDTO legalGuardianToLegalGuardianDTO(LegalGuardian legalGuardian);

    LegalGuardian legalGuardianDTOToLegalGuardian(LegalGuardianDTO legalGuardianDTO);

}
