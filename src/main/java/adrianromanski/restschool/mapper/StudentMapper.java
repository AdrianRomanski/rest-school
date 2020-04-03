package adrianromanski.restschool.mapper;

import adrianromanski.restschool.domain.base_entity.person.Student;
import adrianromanski.restschool.model.base_entity.person.StudentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface StudentMapper {

    StudentMapper INSTANCE = Mappers.getMapper(StudentMapper.class);

    @Mappings({
            @Mapping(source = "subjects", target = "subjectsDTO"),
            @Mapping(source = "exams", target = "examsDTO"),
            @Mapping(source = "studentClass", target = "studentClassDTO")
    })
    StudentDTO studentToStudentDTO(Student student);

    @Mappings({
            @Mapping(source = "subjectsDTO", target = "subjects"),
            @Mapping(source = "examsDTO", target = "exams"),
            @Mapping(source = "studentClassDTO", target = "studentClass")
    })
    Student studentDTOToStudent(StudentDTO studentDTO);
}
