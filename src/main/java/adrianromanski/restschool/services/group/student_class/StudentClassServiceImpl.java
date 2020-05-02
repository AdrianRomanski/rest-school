package adrianromanski.restschool.services.group.student_class;

import adrianromanski.restschool.domain.base_entity.enums.Gender;
import adrianromanski.restschool.domain.base_entity.enums.Subjects;
import adrianromanski.restschool.domain.base_entity.group.StudentClass;
import adrianromanski.restschool.exceptions.ResourceNotFoundException;
import adrianromanski.restschool.mapper.group.StudentClassMapper;
import adrianromanski.restschool.mapper.person.StudentMapper;
import adrianromanski.restschool.model.base_entity.group.StudentClassDTO;
import adrianromanski.restschool.model.base_entity.person.StudentDTO;
import adrianromanski.restschool.repositories.group.StudentClassRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
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

//    Predicate<StudentDTO> firstNameNotNull = s -> s.getFirstName() != null;
//    Predicate<StudentDTO> lastNameNotNull = s -> s.getLastName() != null;
//    Predicate<StudentClass> presidentNotNull = studentClass -> studentClass.getPresident() != null;
//    Predicate<StudentClassDTO> specNotNull = sc -> sc.getSpecialization() != null;
//    Predicate<StudentClassDTO> nameNotNul = sc -> sc.getName() != null;

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
     * @return a List of Student Classes with matching president
     */
    @Override
    public List<StudentClassDTO> getStudentClassByPresident(String president) {
        return studentClassRepository.findAll()
                .stream()
//                .filter(presidentNotNull)
                .filter(studentClass -> studentClass.getPresident().equals(president))
                .map(studentClassMapper::StudentClassToStudentClassDTO)
                .collect(Collectors.toList());
    }


    /**
     * @return Map where the keys are Specializations and values maps containing Student Classes grouped by name
     */
    @Override
    public Map<Subjects, Map<String, List<StudentClassDTO>>> getStudentClassesGroupedBySpecialization() {
        return studentClassRepository
                .findAll()
                .stream()
                .map(studentClassMapper::StudentClassToStudentClassDTO)
//                .filter(specNotNull.and(nameNotNul))
                .collect(groupingBy(
                        StudentClassDTO::getSubject,
                        groupingBy(
                                StudentClassDTO::getName)
                ));
    }

    /**
     * @return a list of Student Classes with matching specialization
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
     * @return Map where the keys are Genders and values List of Students of the Class
     * @throws ResourceNotFoundException if not found
     */
    @Override
    public Map<Gender, List<StudentDTO>> getAllStudentsForClass(Long id) {
        Predicate<StudentDTO> firstNameNotNull = s -> s.getFirstName() != null;
        Predicate<StudentDTO> lastNameNotNull = s -> s.getLastName() != null;
        if (studentClassRepository.findById(id).isPresent()) {
            StudentClass studentClass = studentClassRepository.findById(id).get();
            return studentClass.getStudentList()
                    .stream()
                    .map(studentMapper::studentToStudentDTO)
                    .filter(firstNameNotNull.and(lastNameNotNull))
                    .sorted(studentComparator)
                    .collect(
                            Collectors.groupingBy(
                                    StudentDTO::getGender
                            )
                    );
        } else {
            throw new ResourceNotFoundException("Student Class with id: " + id + " not found");
        }
    }

    /**
     *
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
