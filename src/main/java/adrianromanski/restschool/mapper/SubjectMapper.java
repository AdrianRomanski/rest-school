package adrianromanski.restschool.mapper;

import adrianromanski.restschool.domain.Subject;
import adrianromanski.restschool.model.SubjectDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SubjectMapper {

    SubjectMapper INSTANCE = Mappers.getMapper(SubjectMapper.class);

    SubjectDTO subjectToSubjectDTO(Subject subject);
    Subject subjectDTOToSubject(SubjectDTO subjectDTO);
}
