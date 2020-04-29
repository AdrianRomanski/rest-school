package adrianromanski.restschool.services.group.student_class;

import adrianromanski.restschool.domain.base_entity.group.StudentClass;
import adrianromanski.restschool.domain.base_entity.person.Student;
import adrianromanski.restschool.exceptions.ResourceNotFoundException;
import adrianromanski.restschool.mapper.group.StudentClassMapper;
import adrianromanski.restschool.mapper.person.TeacherMapper;
import adrianromanski.restschool.model.base_entity.group.StudentClassDTO;
import adrianromanski.restschool.model.base_entity.person.TeacherDTO;
import adrianromanski.restschool.repositories.group.StudentClassRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class StudentClassServiceImpl implements StudentClassService {

    private final StudentClassRepository studentClassRepository;
    private final StudentClassMapper studentClassMapper;

    public StudentClassServiceImpl(StudentClassRepository studentClassRepository, StudentClassMapper studentClassMapper) {
        this.studentClassRepository = studentClassRepository;
        this.studentClassMapper = studentClassMapper;
    }


    /**
     * @return all Student Classes
     */
    @Override
    public List<StudentClassDTO> getAllStudentClasses() {
        return studentClassRepository.findAll()
                        .stream()
                        .map(studentClassMapper::StudentClassToStudentClassDTO)
                        .collect(Collectors.toList());
    }


    /**
     * @return Student Class with matching id
     * @throws ResourceNotFoundException if not found
     */
    @Override
    public StudentClassDTO getStudentClassByID(Long id) {
        return studentClassMapper.StudentClassToStudentClassDTO(studentClassRepository
                                                    .findById(id)
                                                    .orElseThrow(ResourceNotFoundException::new));
    }


    /**
     * Converts DTO Object and Save it to Database
     * @return TeacherDTO object
     */
    @Override
    public StudentClassDTO createNewStudentClass(StudentClassDTO studentClassDTO) {
       studentClassRepository.save(studentClassMapper.StudentClassDTOToStudentClass(studentClassDTO));
       log.info("Student Class with id: " + studentClassDTO.getId() + " successfully saved");
       return studentClassDTO;
    }


    /**
     * Converts DTO Object, Update Student Class with Matching ID and save it to Database
     * @return StudentClassDTO object if the Student Class was successfully saved
     * @throws ResourceNotFoundException if not found
     */
    @Override
    public StudentClassDTO updateStudentClass(Long id, StudentClassDTO studentClassDTO) {
        if(studentClassRepository.findById(id).isPresent()) {
            StudentClass updatedClass = studentClassMapper.StudentClassDTOToStudentClass(studentClassDTO);
            updatedClass.setId(id);
            studentClassRepository.save(updatedClass);
            log.info("Student Class with id: " + id + " successfully updated");
            return studentClassMapper.StudentClassToStudentClassDTO(updatedClass);
        } else {
            log.debug("Student Class id: " + id + " not found");
            throw new ResourceNotFoundException("Student Class with id: " + id + " not found");
        }
    }


    /**
     * Delete Student Class with matching id
     * @throws ResourceNotFoundException if not found
     */
    @Override
    public void deleteStudentClassById(Long id) {
        if(studentClassRepository.findById(id).isPresent()) {
            studentClassRepository.deleteById(id);
            log.info("Student Class with id: " + id + " successfully deleted");
        } else {
            log.debug("Student Class id: " + id + " not found");
            throw new ResourceNotFoundException("Student Class with id: " + id + " not found");
        }
    }
}
