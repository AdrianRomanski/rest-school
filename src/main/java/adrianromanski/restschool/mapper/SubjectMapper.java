package adrianromanski.restschool.mapper;

import adrianromanski.restschool.domain.Subject;
import adrianromanski.restschool.model.SubjectDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SubjectMapper {

    SubjectMapper INSTANCE = Mappers.getMapper(SubjectMapper.class);

    @Mapping(source = "students", target = "studentsDTO")
    SubjectDTO subjectToSubjectDTO(Subject subject);
    @Mapping(source = "studentsDTO", target = "students")
    Subject subjectDTOToSubject(SubjectDTO subjectDTO);
}
