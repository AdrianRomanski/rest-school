package adrianromanski.restschool.mapper.base_entity;

import adrianromanski.restschool.domain.base_entity.contact.TeacherContact;
import adrianromanski.restschool.model.base_entity.contact.TeacherContactDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TeacherContactMapper {

    TeacherContactMapper INSTANCE = Mappers.getMapper(TeacherContactMapper.class);

    @Mapping(source = "teacher", target = "teacherDTO")
    TeacherContactDTO contactToContactDTO(TeacherContact contact);

    @Mapping(source = "teacherDTO", target = "teacher")
    TeacherContact contactDTOToContact(TeacherContactDTO contact);
}
