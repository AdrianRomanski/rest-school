package adrianromanski.restschool.mapper;

import adrianromanski.restschool.domain.Student;
import adrianromanski.restschool.model.StudentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface StudentMapper {

    StudentMapper INSTANCE = Mappers.getMapper(StudentMapper.class);

    @Mapping(source = "subjects", target = "subjectsDTO")
    StudentDTO studentToStudentDTO(Student student);

    @Mapping(source = "subjectsDTO", target = "subjects")
    Student studentDTOToStudent(StudentDTO studentDTO);
}
