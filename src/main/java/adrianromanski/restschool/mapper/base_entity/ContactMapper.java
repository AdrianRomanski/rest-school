package adrianromanski.restschool.mapper.base_entity;

import adrianromanski.restschool.domain.base_entity.Contact;
import adrianromanski.restschool.model.base_entity.ContactDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ContactMapper {

    ContactMapper INSTANCE = Mappers.getMapper(ContactMapper.class);

    @Mappings ({
        @Mapping(target = "studentDTO", source = "student"),
        @Mapping(target = "addressDTO", source = "address")
    })
    ContactDTO contactToContactDTO(Contact contact);

    @Mappings ({
        @Mapping(target = "student", source = "studentDTO"),
        @Mapping(target = "address", source = "addressDTO")
    })
    Contact contactDTOToContact(ContactDTO contactDTO);
}
