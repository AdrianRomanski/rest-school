package adrianromanski.restschool.mapper.person;

import adrianromanski.restschool.domain.person.Student;
import adrianromanski.restschool.model.person.StudentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface StudentMapper {

    StudentMapper INSTANCE = Mappers.getMapper(StudentMapper.class);

    @Mappings({
            @Mapping(source = "address", target = "addressDTO"),
            @Mapping(source = "contact", target = "contactDTO"),
            @Mapping(source = "subjects", target = "subjectsDTO"),
            @Mapping(source = "exams", target = "examsDTO"),
            @Mapping(source = "studentClass", target = "studentClassDTO"),
            @Mapping(source = "sportTeam",target = "sportTeamDTO")
    })
    StudentDTO studentToStudentDTO(Student student);


    @Mappings({
            @Mapping(source = "addressDTO", target = "address"),
            @Mapping(source = "subjectsDTO", target = "subjects"),
            @Mapping(source = "examsDTO", target = "exams"),
            @Mapping(source = "studentClassDTO", target = "studentClass"),
            @Mapping(source = "sportTeamDTO", target = "sportTeam")
    })
    Student studentDTOToStudent(StudentDTO studentDTO);
}
