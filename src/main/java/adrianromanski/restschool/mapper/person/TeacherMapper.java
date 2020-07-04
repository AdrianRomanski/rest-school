package adrianromanski.restschool.mapper.person;

import adrianromanski.restschool.domain.person.Teacher;
import adrianromanski.restschool.model.person.TeacherDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TeacherMapper {

    TeacherMapper INSTANCE = Mappers.getMapper(TeacherMapper.class);

    @Mappings({
            @Mapping(source = "address", target = "addressDTO"),
            @Mapping(source = "contact", target = "contactDTO"),
            @Mapping(source = "studentClass", target = "studentClassDTO"),
            @Mapping(source = "exams", target = "examsDTO"),
            @Mapping(source = "payments",target = "paymentsDTO"),
            @Mapping(source = "teachingStaff",target = "teachingStaffDTO")
    })
    TeacherDTO teacherToTeacherDTO(Teacher teacher);

    @Mappings({
            @Mapping(source = "addressDTO", target = "address"),
            @Mapping(source = "contactDTO", target = "contact"),
            @Mapping(source = "studentClassDTO", target = "studentClass"),
            @Mapping(source = "examsDTO", target = "exams"),
            @Mapping(source = "paymentsDTO", target = "payments"),
            @Mapping(source = "teachingStaffDTO",target = "teachingStaff")
    })
    Teacher teacherDTOToTeacher(TeacherDTO teacherDTO);
}
