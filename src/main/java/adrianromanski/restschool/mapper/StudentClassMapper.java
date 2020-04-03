package adrianromanski.restschool.mapper;

import adrianromanski.restschool.domain.base_entity.group.StudentClass;
import adrianromanski.restschool.model.base_entity.group.StudentClassDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface StudentClassMapper {

    StudentClassMapper INSTANCE = Mappers.getMapper(StudentClassMapper.class);

    @Mappings({
            @Mapping(source = "teacher", target = "teacherDTO"),
            @Mapping(source = "studentList", target = "studentDTOList")
    })
    StudentClassDTO StudentClassToStudentClassDTO(StudentClass studentClass);

    @Mappings({
            @Mapping(source = "teacherDTO", target = "teacher"),
            @Mapping(source = "studentDTOList", target = "studentList")
    })
    StudentClass StudentClassDTOToStudentClass(StudentClassDTO studentClassDTO);
}
