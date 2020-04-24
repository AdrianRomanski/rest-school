package adrianromanski.restschool.services.person.student;

import adrianromanski.restschool.domain.base_entity.person.Student;
import adrianromanski.restschool.exceptions.ResourceNotFoundException;
import adrianromanski.restschool.mapper.person.StudentMapper;
import adrianromanski.restschool.model.base_entity.person.StudentDTO;
import adrianromanski.restschool.repositories.person.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static adrianromanski.restschool.domain.base_entity.person.enums.Gender.FEMALE;
import static adrianromanski.restschool.domain.base_entity.person.enums.Gender.MALE;

@Slf4j
@Service
public class StudentServiceImpl implements StudentService {

    private final StudentMapper studentMapper;
    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentMapper studentMapper, StudentRepository studentRepository) {
        this.studentMapper = studentMapper;
        this.studentRepository = studentRepository;
    }

    /**
     * @return all Students sorted by lastName&firstName
     */
    @Override
    public List<StudentDTO> getAllStudents() {
        return studentRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Student::getLastName).thenComparing(Student::getFirstName))
                .map(studentMapper::studentToStudentDTO)
                .collect(Collectors.toList());
    }

    /**
     * @return  all Female Students sorted by lastName&firstName
     */
    @Override
    public List<StudentDTO> getAllFemaleStudents() {
        return studentRepository.findAll()
                .stream()
                .map(studentMapper::studentToStudentDTO)
                .sorted(Comparator.comparing(StudentDTO::getLastName).thenComparing(StudentDTO::getLastName))
                .filter(studentDTO -> studentDTO.getGender().equals(FEMALE))
                .collect(Collectors.toList());
    }

    /**
     * @return Male Students sorted by lastName&firstName
     */
    @Override
    public List<StudentDTO> getAllMaleStudents() {
        return studentRepository.findAll()
                .stream()
                .map(studentMapper::studentToStudentDTO)
                .sorted(Comparator.comparing(StudentDTO::getLastName).thenComparing(StudentDTO::getFirstName))
                .filter(studentDTO -> studentDTO.getGender().equals(MALE))
                .collect(Collectors.toList());
    }

    /**
     * @return Student with matching firstName and lastName
     * @throws ResourceNotFoundException if not found
     */
    @Override
    public StudentDTO getStudentByFirstAndLastName(String firstName, String lastName) {
        return studentMapper.studentToStudentDTO(studentRepository
                                .findByFirstNameAndLastName(firstName, lastName)
                                .orElseThrow(ResourceNotFoundException::new)); // To do better Exceptions
    }

    /**
     * @return Student with matching id
     * @throws ResourceNotFoundException if not found
     */
    @Override
    public StudentDTO getStudentByID(Long id) {
        return studentMapper.studentToStudentDTO(studentRepository
                                .findById(id)
                                .orElseThrow(ResourceNotFoundException::new)); // To do better Exceptions
    }

    /**
     * Converting StudentDTO to Student and saving it to Database
     * @return StudentDTO object
     */
    @Override
    public StudentDTO createNewStudent(StudentDTO studentDTO) {
        studentRepository.save(studentMapper.studentDTOToStudent(studentDTO));
        log.info("Student with id: " + studentDTO.getId() + " successfully saved");
        return studentDTO;
    }

    /**
     * Updating existing Student and saving to Database
     * @return StudentDTO object if the student was successfully saved
     * @throws ResourceNotFoundException if not found
     */
    @Override
    public StudentDTO updateStudent(Long id, StudentDTO studentDTO) {
        if(studentRepository.findById(id).isPresent()) {
           Student updatedStudent = studentMapper.studentDTOToStudent(studentDTO);
           studentRepository.save(updatedStudent);
            log.info("Student with id:" + id +  "successfully updated");
           return studentDTO;
        } else {
            throw new ResourceNotFoundException("Student with id: " + id + " not found");
        }
    }

    /**
     * Delete Student with matching id
     * @throws ResourceNotFoundException if not found
     */
    @Override
    public void deleteStudentByID(Long id) {
        if(studentRepository.findById(id).isPresent()) {
            studentRepository.deleteById(id);
            log.info("Student with id:" + id +  "successfully deleted");
        } else {
            log.debug("Student with id: " + id + " not found");
            throw new ResourceNotFoundException("Student with id: " + id + " not found");
        }
    }
}