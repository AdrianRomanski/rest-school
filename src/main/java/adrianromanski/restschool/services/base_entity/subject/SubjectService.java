package adrianromanski.restschool.services.base_entity.subject;

import adrianromanski.restschool.model.base_entity.SubjectDTO;

import java.util.List;

public interface SubjectService {

    // GET
    List<SubjectDTO> getAllSubjects();

    SubjectDTO getSubjectByID(Long id);

    SubjectDTO getSubjectByName(String name);

    List<SubjectDTO> getSubjectsWithFullValue();

    List<SubjectDTO> getSubjectsWithLowestValue();

    // POST
    SubjectDTO createNewSubject(SubjectDTO subjectDTO);

    // PUT
    SubjectDTO updateSubject(Long id, SubjectDTO subjectDTO);

    // DELETE
    void deleteSubjectByID(Long id);
}
