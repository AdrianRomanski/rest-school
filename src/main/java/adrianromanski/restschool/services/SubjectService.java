package adrianromanski.restschool.services;

import adrianromanski.restschool.model.SubjectDTO;
import adrianromanski.restschool.model.SubjectListDTO;

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
}
