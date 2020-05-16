package adrianromanski.restschool.mapper.base_entity;

import adrianromanski.restschool.domain.base_entity.contact.StudentContact;
import adrianromanski.restschool.model.base_entity.contact.StudentContactDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface StudentContactMapper {

    StudentContactMapper INSTANCE = Mappers.getMapper(StudentContactMapper.class);

    @Mappings ({
        @Mapping(target = "studentDTO", source = "student"),
    })
    StudentContactDTO contactToContactDTO(StudentContact contact);


    @Mappings ({
        @Mapping(target = "student", source = "studentDTO"),
    })
    StudentContact contactDTOToContact(StudentContactDTO contactDTO);
}
