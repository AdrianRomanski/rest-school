package adrianromanski.restschool.services.person.student;

import adrianromanski.restschool.domain.base_entity.Address;
import adrianromanski.restschool.domain.base_entity.Contact;
import adrianromanski.restschool.domain.base_entity.person.Student;
import adrianromanski.restschool.exceptions.ResourceNotFoundException;
import adrianromanski.restschool.mapper.base_entity.AddressMapper;
import adrianromanski.restschool.mapper.base_entity.ContactMapper;
import adrianromanski.restschool.mapper.person.StudentMapper;
import adrianromanski.restschool.model.base_entity.AddressDTO;
import adrianromanski.restschool.model.base_entity.ContactDTO;
import adrianromanski.restschool.model.base_entity.person.StudentDTO;
import adrianromanski.restschool.repositories.base_entity.AddressRepository;
import adrianromanski.restschool.repositories.base_entity.ContactRepository;
import adrianromanski.restschool.repositories.person.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static adrianromanski.restschool.domain.base_entity.enums.Gender.FEMALE;
import static adrianromanski.restschool.domain.base_entity.enums.Gender.MALE;

@Slf4j
@Service
public class StudentServiceImpl implements StudentService{

    private final StudentMapper studentMapper;
    private final ContactMapper contactMapper;
    private final AddressMapper addressMapper;
    private final StudentRepository studentRepository;
    private final ContactRepository contactRepository;
    private final AddressRepository addressRepository;

    public static final Comparator<Student> COMPARATOR = Comparator.comparing(Student::getAge)
                                                                        .thenComparing(Student::getLastName)
                                                                        .thenComparing((Student::getFirstName));

    public StudentServiceImpl(StudentMapper studentMapper, ContactMapper contactMapper,
                              AddressMapper addressMapper, StudentRepository studentRepository, ContactRepository contactRepository, AddressRepository addressRepository) {
        this.studentMapper = studentMapper;
        this.contactMapper = contactMapper;
        this.addressMapper = addressMapper;
        this.studentRepository = studentRepository;
        this.contactRepository = contactRepository;
        this.addressRepository = addressRepository;
    }

    /**
     * @return all Students sorted by age -> lastName -> firstName
     */
    @Override
    public List<StudentDTO> getAllStudents() {
        return studentRepository.findAll()
                .stream()
                .sorted(COMPARATOR)
                .map(studentMapper::studentToStudentDTO)
                .collect(Collectors.toList());
    }

    /**
     * @return  all Female Students sorted by age -> lastName -> firstName
     */
    @Override
    public List<StudentDTO> getAllFemaleStudents() {
        return studentRepository.findAll()
                .stream()
                .sorted(COMPARATOR)
                .map(studentMapper::studentToStudentDTO)
                .filter(studentDTO -> studentDTO.getGender().equals(FEMALE))
                .collect(Collectors.toList());
    }

    /**
     * @return Male Students sorted by age -> lastName -> firstName
     */
    @Override
    public List<StudentDTO> getAllMaleStudents() {
        return studentRepository.findAll()
                .stream()
                .sorted(COMPARATOR)
                .map(studentMapper::studentToStudentDTO)
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
     * Saving Student to Database
     * @return StudentDTO object
     */
    @Override
    public StudentDTO createNewStudent(StudentDTO studentDTO) {
        studentRepository.save(studentMapper.studentDTOToStudent(studentDTO));
            log.info("Student with id: " + studentDTO.getId() + " successfully saved");
        return studentDTO;
    }


    /**
     * Adding Contact to Student
     * @throws ResourceNotFoundException if Student not found
     */
    @Override
    public ContactDTO addContactToStudent(ContactDTO contactDTO, Long studentID) {
        Student student = studentRepository
                .findById(studentID)
                .orElseThrow(() -> new ResourceNotFoundException("Student with id: " + studentID + " not found"));
        Contact contact = contactMapper.contactDTOToContact(contactDTO);
            student.setContact(contact);
            contactDTO.setStudentDTO(studentMapper.studentToStudentDTO(student));
        contactRepository.save(contact);
        studentRepository.save(student);
            log.info("Contact successfully added to Student with id:  " + studentID);
        return contactMapper.contactToContactDTO(contact);
    }


    /**
     * Adding Address to Contact
     * @throws ResourceNotFoundException if Student not found
     * @throws ResourceNotFoundException if Contact not found
     */
    @Override
    public AddressDTO addAddressToStudent(AddressDTO addressDTO, Long contactID, Long studentID) {
        Student student = studentRepository
                .findById(studentID)
                .orElseThrow(() -> new ResourceNotFoundException("Student with id: " + studentID + " not found"));
        Contact contact = contactRepository
                .findById(contactID)
                .orElseThrow(() -> new ResourceNotFoundException("Contact with id: " + contactID + " not found"));
        Address address = addressMapper.addressDTOToAddress(addressDTO);
            contact.setAddress(address);
            student.setContact(contact);
        contactRepository.save(contact);
        studentRepository.save(student);
        addressRepository.save(address);
            log.info("Address successfully added to Student with id: " + studentID);
        return addressDTO;
    }


    /**
     *  Update Student with Matching ID and save it to Database
     * @return StudentDTO object if the student was successfully saved
     * @throws ResourceNotFoundException if not found
     */
    @Override
    public StudentDTO updateStudent(Long id, StudentDTO studentDTO) {
           studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student with id: " + id + " not found")); // I should log debug
           Student updatedStudent = studentMapper.studentDTOToStudent(studentDTO);
               updatedStudent.setId(id);
           studentRepository.save(updatedStudent);
               log.info("Student with id:" + id +  " successfully updated");
           return studentMapper.studentToStudentDTO(updatedStudent);

    }

    /**
     * Delete Student with matching id
     * @throws ResourceNotFoundException if not found
     */
    @Override
    public void deleteStudentByID(Long id) {
        Student student = studentRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student with id: " + id + " not found")); // I should log debug
        studentRepository.delete(student);
            log.info("Student with id:" + id +  " successfully deleted");
    }
}
