package adrianromanski.restschool.services.group.student_class;

import adrianromanski.restschool.domain.enums.Gender;
import adrianromanski.restschool.domain.enums.Subjects;
import adrianromanski.restschool.domain.group.StudentClass;
import adrianromanski.restschool.exceptions.ResourceNotFoundException;
import adrianromanski.restschool.mapper.group.StudentClassMapper;
import adrianromanski.restschool.mapper.person.StudentMapper;
import adrianromanski.restschool.model.group.StudentClassDTO;
import adrianromanski.restschool.model.person.StudentDTO;
import adrianromanski.restschool.repositories.group.StudentClassRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

@Slf4j
@Service
public class StudentClassServiceImpl implements StudentClassService {

    private final StudentClassRepository studentClassRepository;
    private final StudentMapper studentMapper;
    private final StudentClassMapper studentClassMapper;

    public StudentClassServiceImpl(StudentClassRepository studentClassRepository, StudentClassMapper studentClassMapper, StudentMapper studentMapper) {
        this.studentClassRepository = studentClassRepository;
        this.studentClassMapper = studentClassMapper;
        this.studentMapper = studentMapper;
    }

    Comparator<StudentDTO> studentComparator = Comparator.comparing(StudentDTO::getFirstName)
                                                                .thenComparing(StudentDTO::getLastName);

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
     * @param id of StudentClass
     * @return Student Class with matching id
     * @throws ResourceNotFoundException if not found
     */
    @Override
    public StudentClassDTO getStudentClassByID(Long id) {
        return studentClassRepository.findById(id)
                .map(studentClassMapper::StudentClassToStudentClassDTO)
                .orElseThrow(() -> new ResourceNotFoundException(id, StudentClass.class));

    }


    /**
     * @param president name of the Class or either classes
     * @return a List of Student Classes with matching president
     */
    @Override
    public List<StudentClassDTO> getStudentClassByPresident(String president) {
        return studentClassRepository.findAll()
                .stream()
                .filter(studentClass -> studentClass.getPresident().equals(president))
                .map(studentClassMapper::StudentClassToStudentClassDTO)
                .collect(Collectors.toList());
    }


    /**
     * @return StudentClasses grouped Specializations and Names
     */
    @Override
    public Map<Subjects, Map<String, List<StudentClassDTO>>> getStudentClassesGroupedBySpecialization() {
        return studentClassRepository
                .findAll()
                .stream()
                .map(studentClassMapper::StudentClassToStudentClassDTO)
                .collect(groupingBy(
                        StudentClassDTO::getSubject,
                        groupingBy(
                                StudentClassDTO::getName)
                ));
    }


    /**
     * @param subject name(Specialization of the class is just a  most important subject)
     * @return Student Classes with matching specialization
     */
    @Override
    public List<StudentClassDTO> getAllStudentClassForSpecialization(Subjects subject) {
        return studentClassRepository.findAll()
                .stream()
                .filter(sc -> sc.getSubject() == subject)
                .map(studentClassMapper::StudentClassToStudentClassDTO)
                .collect(toList());
    }


    /**
     * @return a list of Student Classes with largest number of students
     */
    @Override
    public List<StudentClassDTO> getLargestStudentClass() {
        return studentClassRepository.findAll()
                .stream()
                .sorted(Comparator.comparingInt(studentClass -> -studentClass.getStudentList().size())) // Reversing
                .map(studentClassMapper::StudentClassToStudentClassDTO)
                .limit(1)
                .collect(Collectors.toList());
    }


    /**
     * @return a list of Student Classes with smallest number of students
     */
    @Override
    public List<StudentClassDTO> getSmallestStudentClass() {
        return studentClassRepository.findAll()
                .stream()
                .sorted(Comparator.comparingInt(studentClass -> studentClass.getStudentList().size()))
                .map(studentClassMapper::StudentClassToStudentClassDTO)
                .limit(1)
                .collect(Collectors.toList());
    }


    /**
     * @param id of StudentClass
     * @return Students from the Class with matching id grouped by Gender
     * @throws ResourceNotFoundException if not found
     */
    @Override
    public Map<Gender, List<StudentDTO>> getAllStudentsForClass(Long id) {
        StudentClass studentClass = studentClassRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id, StudentClass.class));
        return studentClass.getStudentList()
                .stream()
                .map(studentMapper::studentToStudentDTO)
                .sorted(studentComparator)
                .collect(
                        Collectors.groupingBy(
                                StudentDTO::getGender
                        )
                );
    }


    /**
     * @param studentClassDTO
     * Save Payment to Database
     * @return StudentClass after saving it to database
     */
    @Override
    public StudentClassDTO createNewStudentClass(StudentClassDTO studentClassDTO) {
       studentClassRepository.save(studentClassMapper.StudentClassDTOToStudentClass(studentClassDTO));
       log.info("Student Class with id: " + studentClassDTO.getId() + " successfully saved");
       return studentClassDTO;
    }


    /**
     * @param id of StudentClass to be updated
     * @param studentClassDTO object to save
     * @return Updated Payment if successfully saved
     * @throws ResourceNotFoundException if not found
     */
    @Override
    public StudentClassDTO updateStudentClass(Long id, StudentClassDTO studentClassDTO) {
        studentClassRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id, StudentClass.class));
        StudentClass updatedClass = studentClassMapper.StudentClassDTOToStudentClass(studentClassDTO);
        updatedClass.setId(id);
        studentClassRepository.save(updatedClass);
        log.info("Student Class with id: " + id + " successfully updated");
        return studentClassMapper.StudentClassToStudentClassDTO(updatedClass);
    }


    /**
     * @param id of StudentClass to be deleted
     * @throws ResourceNotFoundException if not found
     */
    @Override
    public void deleteStudentClassById(Long id) {
        studentClassRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id, StudentClass.class));
        studentClassRepository.deleteById(id);
        log.info("Student Class with id: " + id + " successfully deleted");
    }
}
