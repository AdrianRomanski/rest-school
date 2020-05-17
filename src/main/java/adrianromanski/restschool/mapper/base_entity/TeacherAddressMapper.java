package adrianromanski.restschool.mapper.base_entity;

import adrianromanski.restschool.domain.base_entity.address.TeacherAddress;

import adrianromanski.restschool.model.base_entity.address.TeacherAddressDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TeacherAddressMapper {

    TeacherAddressMapper  INSTANCE = Mappers.getMapper(TeacherAddressMapper.class);

    @Mapping(source = "teacher", target = "teacherDTO")
    TeacherAddressDTO teacherAddressToTeacherAddressDTO(TeacherAddress teacherAddress);

    @Mapping(source = "teacherDTO", target = "teacher")
    TeacherAddress teacherAddressDTOToTeacherAddress(TeacherAddressDTO teacherAddressDTO);
}
