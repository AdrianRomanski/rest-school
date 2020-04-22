package adrianromanski.restschool.services.base_entity.subject;

import adrianromanski.restschool.model.base_entity.SubjectDTO;

import java.util.List;

public interface SubjectService {

    List<SubjectDTO> getAllSubjects();

    SubjectDTO createNewSubject(SubjectDTO subjectDTO);

    SubjectDTO updateSubject(Long id, SubjectDTO subjectDTO);

    SubjectDTO getSubjectByID(Long id);

    SubjectDTO getSubjectByName(String name);

    List<SubjectDTO> getSubjectsWithFullValue();

    List<SubjectDTO> getSubjectsWithLowestValue();

    SubjectDTO getMostPopularSubject();

    void deleteSubjectByID(Long id);
}
