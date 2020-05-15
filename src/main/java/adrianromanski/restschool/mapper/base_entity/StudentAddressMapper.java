package adrianromanski.restschool.mapper.base_entity;

import adrianromanski.restschool.domain.base_entity.address.StudentAddress;
import adrianromanski.restschool.model.base_entity.address.StudentAddressDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface StudentAddressMapper {

    StudentAddressMapper INSTANCE = Mappers.getMapper(StudentAddressMapper.class);

    @Mapping(source = "student", target = "studentDTO")
    StudentAddressDTO addressToAddressDTO(StudentAddress studentAddress);

    @Mapping(source = "studentDTO", target = "student")
    StudentAddress addressDTOToAddress(StudentAddressDTO studentAddressDTO);
}
