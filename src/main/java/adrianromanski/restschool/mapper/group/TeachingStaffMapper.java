package adrianromanski.restschool.mapper.group;

import adrianromanski.restschool.domain.group.TeachingStaff;
import adrianromanski.restschool.model.group.TeachingStaffDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TeachingStaffMapper {

    TeachingStaffMapper INSTANCE = Mappers.getMapper(TeachingStaffMapper.class);


    @Mapping(source = "teachers", target = "teachersDTO")
    TeachingStaffDTO teachingStaffToTeachingStaffDTO(TeachingStaff teachingStaff);


    @Mapping(source = "teachersDTO",target = "teachers")
    TeachingStaff teachingStaffToTeachingStaffDTO(TeachingStaffDTO teachingStaffDTO);
}
