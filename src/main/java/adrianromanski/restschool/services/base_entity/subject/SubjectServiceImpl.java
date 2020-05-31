package adrianromanski.restschool.services.base_entity.subject;

import adrianromanski.restschool.domain.base_entity.Subject;
import adrianromanski.restschool.domain.enums.Subjects;
import adrianromanski.restschool.domain.person.Teacher;
import adrianromanski.restschool.exceptions.ResourceNotFoundException;
import adrianromanski.restschool.mapper.base_entity.SubjectMapper;
import adrianromanski.restschool.model.base_entity.SubjectDTO;
import adrianromanski.restschool.repositories.base_entity.SubjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SubjectServiceImpl implements SubjectService {

    private final SubjectMapper subjectMapper;
    private final SubjectRepository subjectRepository;

    public SubjectServiceImpl(SubjectMapper subjectMapper, SubjectRepository subjectRepository) {
        this.subjectMapper = subjectMapper;
        this.subjectRepository = subjectRepository;
    }


    /**
     * @return List with all Subjects
     */
    @Override
    public List<SubjectDTO> getAllSubjects() {
        return subjectRepository.findAll()
                .stream()
                .map(subjectMapper::subjectToSubjectDTO)
                .collect(Collectors.toList());
    }


    /**
     * @param id of the subject to be found
     * @return Subject with matching id
     * @throws ResourceNotFoundException if not found
     */
    @Override
    public SubjectDTO getSubjectByID(Long id) {
        return subjectMapper.subjectToSubjectDTO(subjectRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id, Subject.class)));
    }


    /**
     * @param name of the subject to be found
     * @return Subject with matching name
     * @throws ResourceNotFoundException if not found
     */
    @Override
    public SubjectDTO getSubjectByName(String name) {
        return subjectMapper.subjectToSubjectDTO(subjectRepository
                .findByName(Subjects.valueOf(name))
                .orElseThrow(() -> new ResourceNotFoundException(name, Subject.class)));
    }


    /**
     * @return List of Subjects with full point value
     */
    @Override
    public List<SubjectDTO> getSubjectsWithFullValue() {
        return subjectRepository.findAll()
                .stream()
                .filter(subject -> subject.getValue() == 10)
                .map(subjectMapper::subjectToSubjectDTO)
                .collect(Collectors.toList());
    }


    /**
     * @return List of Subjects with lowest point value
     */
    @Override
    public List<SubjectDTO> getSubjectsWithLowestValue() {
        return subjectRepository.findAll()
                .stream()
                .filter(subject -> subject.getValue() == 1)
                .map(subjectMapper::subjectToSubjectDTO)
                .collect(Collectors.toList());
    }


    /**
     * @param subjectDTO
     * Save Subject to Database
     * @return Subject after saving it to database
     */
    @Override
    public SubjectDTO createNewSubject(SubjectDTO subjectDTO) {
        subjectRepository.save(subjectMapper.subjectDTOToSubject(subjectDTO));
        log.info("Subject with id: " + subjectDTO.getId() + "successfully saved to database");
        return subjectDTO;
    }


    /**
     * @param id of Subject to be updated
     * @param subjectDTO body to save
     * @return Updated Subject if successfully saved
     * @throws ResourceNotFoundException if not found
     */
    @Override
    public SubjectDTO updateSubject(Long id, SubjectDTO subjectDTO) {
        Subject subject = subjectRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id, Subject.class));
        Subject updatedSubject = subjectMapper.subjectDTOToSubject(subjectDTO);
        updatedSubject.setId(id);
        subjectRepository.save(subject);
        log.info("Subject with id: " + id + "successfully saved to database");
        return subjectMapper.subjectToSubjectDTO(updatedSubject);
    }


    /**
     * @param id of Subject to be deleted
     * @throws ResourceNotFoundException if not found
     */
    @Override
    public void deleteSubjectByID(Long id) {
        Subject subject = subjectRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id, Subject.class));
        subjectRepository.delete(subject);
        log.info("Subject with id: " + id + "successfully deleted");
    }
}
