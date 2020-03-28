package adrianromanski.restschool.services;

import adrianromanski.restschool.domain.Subject;
import adrianromanski.restschool.exceptions.ResourceNotFoundException;
import adrianromanski.restschool.mapper.SubjectMapper;
import adrianromanski.restschool.model.SubjectDTO;
import adrianromanski.restschool.repositories.SubjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubjectServiceImpl implements SubjectService {

    private SubjectMapper subjectMapper;
    private SubjectRepository subjectRepository;

    public SubjectServiceImpl(SubjectMapper subjectMapper, SubjectRepository subjectRepository) {
        this.subjectMapper = subjectMapper;
        this.subjectRepository = subjectRepository;
    }

    @Override
    public List<SubjectDTO> getAllSubjects() {
        return subjectRepository.findAll()
                .stream()
                .map(subjectMapper::subjectToSubjectDTO)
                .collect(Collectors.toList());
    }

    @Override
    public SubjectDTO createNewSubject(SubjectDTO subjectDTO) {
        return saveAndReturnDto(subjectMapper.subjectDTOToSubject(subjectDTO));
    }

    @Override
    public SubjectDTO updateSubject(Long id, SubjectDTO subjectDTO) {
        Subject subject = subjectMapper.subjectDTOToSubject(subjectDTO);
        subject.setId(id);
        return saveAndReturnDto(subject);
    }

    @Override
    public SubjectDTO getSubjectByID(Long id) {
        return subjectMapper.subjectToSubjectDTO(subjectRepository.findById(id)
                                                    .orElseThrow(ResourceNotFoundException::new));
    }

    @Override
    public SubjectDTO getSubjectByName(String name) {
        return subjectMapper.subjectToSubjectDTO(subjectRepository.findByName(name));
    }

    @Override
    public List<SubjectDTO> getSubjectsWithFullValue() {
        return subjectRepository.findAll()
                .stream()
                .filter(subject -> subject.getValue() == 10)
                .map(subjectMapper::subjectToSubjectDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<SubjectDTO> getSubjectsWithLowestValue() {
        return subjectRepository.findAll()
                .stream()
                .filter(subject -> subject.getValue() == 1)
                .map(subjectMapper::subjectToSubjectDTO)
                .collect(Collectors.toList());
    }

    @Override
    public SubjectDTO getMostPopularSubject() {
        List<Subject> subjects = subjectRepository.findAll();
        subjects.sort((subject1, subject2) -> subject2.getStudents().size() - subject1.getStudents().size());
        return subjectMapper.subjectToSubjectDTO(subjects.get(0));
    }

    @Override
    public void deleteSubjectByID(Long id) {
        subjectRepository.deleteById(id);
    }

    public SubjectDTO saveAndReturnDto(Subject subject) {
        Subject savedSubject = subjectRepository.save(subject);
        return subjectMapper.subjectToSubjectDTO(subject);
    }
}
