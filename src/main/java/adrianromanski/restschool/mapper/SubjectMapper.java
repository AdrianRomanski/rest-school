package adrianromanski.restschool.mapper;

import adrianromanski.restschool.domain.Subject;
import adrianromanski.restschool.model.SubjectDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SubjectMapper {

    SubjectMapper INSTANCE = Mappers.getMapper(SubjectMapper.class);

    @Mappings({
            @Mapping(source = "students", target = "studentsDTO"),
            @Mapping(source = "exams", target = "examsDTO"),
    })
    SubjectDTO subjectToSubjectDTO(Subject subject);

    @Mappings({
            @Mapping(source = "studentsDTO", target = "students"),
            @Mapping(source = "examsDTO", target = "exams"),
    })
    @Mapping(source = "studentsDTO", target = "students")
    Subject subjectDTOToSubject(SubjectDTO subjectDTO);
}
