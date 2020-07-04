package adrianromanski.restschool.mapper.event;

import adrianromanski.restschool.domain.event.SchoolYear;
import adrianromanski.restschool.model.event.SchoolYearDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SchoolYearMapper {

    SchoolYearMapper INSTANCE = Mappers.getMapper(SchoolYearMapper.class);


    @Mappings({
            @Mapping(source = "teachingStaff", target = "teachingStaffDTO"),
            @Mapping(source = "studentClasses", target = "studentClassesDTO"),
            @Mapping(source = "sportTeams", target = "sportTeamsDTO"),
            @Mapping(source = "director", target = "directorDTO")
    })
    SchoolYearDTO schoolYearToSchoolYearDTO(SchoolYear schoolYear);

    @Mappings({
            @Mapping(source = "teachingStaffDTO", target = "teachingStaff"),
            @Mapping(source = "studentClassesDTO", target = "studentClasses"),
            @Mapping(source = "sportTeamsDTO", target = "sportTeams"),
            @Mapping(source = "directorDTO", target = "director")
    })
    SchoolYear schoolYearDTOToSchoolYear(SchoolYearDTO schoolYearDTO);
}
