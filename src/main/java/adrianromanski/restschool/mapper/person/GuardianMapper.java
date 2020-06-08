package adrianromanski.restschool.mapper.person;

import adrianromanski.restschool.domain.person.Guardian;
import adrianromanski.restschool.model.person.GuardianDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GuardianMapper {

    GuardianMapper INSTANCE  = Mappers.getMapper(GuardianMapper.class);

    @Mappings({
            @Mapping(source = "address", target = "addressDTO"),
            @Mapping(source = "contact", target = "contactDTO"),
            @Mapping(source = "students", target = "studentsDTO")
    })
    GuardianDTO guardianToGuardianDTO(Guardian Guardian);

    @Mappings({
            @Mapping(source = "addressDTO", target = "address"),
            @Mapping(source = "contactDTO", target = "contact"),
            @Mapping(source = "studentsDTO", target = "students")
    })
    Guardian guardianDTOToGuardian(GuardianDTO GuardianDTO);

}
