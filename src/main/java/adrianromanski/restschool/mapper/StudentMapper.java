package adrianromanski.restschool.mapper;

import adrianromanski.restschool.domain.Student;
import adrianromanski.restschool.model.StudentDTO;
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
    })
    StudentDTO studentToStudentDTO(Student student);

    @Mappings({
            @Mapping(source = "subjectsDTO", target = "subjects"),
            @Mapping(source = "examsDTO", target = "exams"),
    })
    Student studentDTOToStudent(StudentDTO studentDTO);
}
