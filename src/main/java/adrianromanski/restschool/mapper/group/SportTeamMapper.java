package adrianromanski.restschool.mapper.group;

import adrianromanski.restschool.domain.base_entity.group.SportTeam;
import adrianromanski.restschool.model.base_entity.group.SportTeamDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SportTeamMapper {

    SportTeamMapper INSTANCE = Mappers.getMapper(SportTeamMapper.class);

    @Mapping(source = "students", target = "studentsDTO")
    SportTeamDTO sportTeamToSportTeamDTO(SportTeam sportTeam);

    @Mapping(source = "studentsDTO", target = "students")
    SportTeam sportTeamDTOToSportTeam(SportTeamDTO sportTeamDTO);
}
