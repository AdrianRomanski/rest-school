package adrianromanski.restschool.services.person.student;

import adrianromanski.restschool.domain.base_entity.address.Address;
import adrianromanski.restschool.domain.base_entity.contact.Contact;
import adrianromanski.restschool.domain.base_entity.address.StudentAddress;
import adrianromanski.restschool.domain.base_entity.contact.StudentContact;
import adrianromanski.restschool.domain.person.Student;
import adrianromanski.restschool.exceptions.ResourceNotFoundException;
import adrianromanski.restschool.mapper.base_entity.StudentAddressMapper;
import adrianromanski.restschool.mapper.base_entity.StudentContactMapper;
import adrianromanski.restschool.mapper.person.StudentMapper;
import adrianromanski.restschool.model.base_entity.address.StudentAddressDTO;
import adrianromanski.restschool.model.base_entity.contact.StudentContactDTO;
import adrianromanski.restschool.model.person.StudentDTO;
import adrianromanski.restschool.repositories.base_entity.AddressRepository;
import adrianromanski.restschool.repositories.base_entity.StudentContactRepository;
import adrianromanski.restschool.repositories.person.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static adrianromanski.restschool.domain.enums.Gender.FEMALE;
import static adrianromanski.restschool.domain.enums.Gender.MALE;
import static java.util.stream.Collectors.*;

@Slf4j
@Service
public class StudentServiceImpl implements StudentService{

    private final StudentMapper studentMapper;
    private final StudentContactMapper contactMapper;
    private final StudentAddressMapper studentAddressMapper;
    private final StudentRepository studentRepository;
    private final StudentContactRepository studentContactRepository;
    private final AddressRepository addressRepository;

    public static final Comparator<Student> COMPARATOR = Comparator.comparing(Student::getAge)
                                                                        .thenComparing(Student::getLastName)
                                                                        .thenComparing((Student::getFirstName));

    public static final Function<StudentDTO, String> GROUPED_BY_COUNTRY = s -> s.getAddressDTO().getCountry();
    public static final Function<StudentDTO, String> GROUPED_BY_CITY = s -> s.getAddressDTO().getCity();

    public StudentServiceImpl(StudentMapper studentMapper, StudentContactMapper contactMapper,
                              StudentAddressMapper studentAddressMapper, StudentRepository studentRepository, StudentContactRepository studentContactRepository, AddressRepository addressRepository) {
        this.studentMapper = studentMapper;
        this.contactMapper = contactMapper;
        this.studentAddressMapper = studentAddressMapper;
        this.studentRepository = studentRepository;
        this.studentContactRepository = studentContactRepository;
        this.addressRepository = addressRepository;
    }


    /**
     * @return Student with matching id
     * @throws ResourceNotFoundException if not found
     */
    @Override
    public StudentDTO getStudentByID(Long studentID) {
        return studentMapper.studentToStudentDTO(studentRepository
                .findById(studentID)
                .orElseThrow(() -> new ResourceNotFoundException(studentID, Student.class)));
    }


    /**
     * @return Student with matching firstName and lastName
     * @throws ResourceNotFoundException if not found
     */
    @Override
    public StudentDTO getStudentByName(String firstName, String lastName) {
        return studentMapper.studentToStudentDTO(studentRepository
                .findByFirstNameAndLastName(firstName, lastName)
                .orElseThrow(() -> new ResourceNotFoundException(firstName, lastName, Student.class)));
    }


    /**
     * @return Students sorted by age -> lastName -> firstName
     */
    @Override
    public List<StudentDTO> getAllStudents() {
        return studentRepository.findAll()
                .stream()
                .sorted(COMPARATOR)
                .map(studentMapper::studentToStudentDTO)
                .collect(toList());
    }

    /**
     * @return Female Students sorted by age -> lastName -> firstName
     */
    @Override
    public List<StudentDTO> getAllFemaleStudents() {
        return studentRepository.findAll()
                .stream()
                .sorted(COMPARATOR)
                .map(studentMapper::studentToStudentDTO)
                .filter(studentDTO -> studentDTO.getGender().equals(FEMALE))
                .collect(toList());
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
                .collect(toList());
    }

    /**
     * @return Students grouped by Age
     */
    @Override
    public Map<Long, List<StudentDTO>> getStudentsByAge() {
        return studentRepository.findAll()
                .stream()
                .map(studentMapper::studentToStudentDTO)
                .collect(
                        groupingBy(
                                StudentDTO::getAge
                        )
                );
    }


    /**
     * @return Students grouped by Country and City
     */
    @Override
    public Map<String, Map<String, List<StudentDTO>>> getStudentsByLocation() {
        return studentRepository.findAll()
                .stream()
                .map(studentMapper::studentToStudentDTO)
                .collect(
                        groupingBy(
                                GROUPED_BY_COUNTRY,
                                groupingBy(
                                        GROUPED_BY_CITY
                                )
                        )
                );
    }


    /**
     * Saving Student to Database
     * @return StudentDTO
     */
    @Override
    public StudentDTO createNewStudent(StudentDTO studentDTO) {
        studentRepository.save(studentMapper.studentDTOToStudent(studentDTO));
            log.info("Student with id: " + studentDTO.getId() + " successfully saved");
        return studentDTO;
    }


    /**
     * Adding Contact to Student
     * @throws ResourceNotFoundException if not found
     */
    @Override
    public StudentContactDTO addContactToStudent(StudentContactDTO contactDTO, Long studentID) {
        Student student = studentRepository
                .findById(studentID)
                .orElseThrow(() -> new ResourceNotFoundException(studentID, Student.class));
        StudentContact contact = contactMapper.contactDTOToContact(contactDTO);
            student.setContact(contact);
            contact.setStudent(student);
        studentContactRepository.save(contact);
        studentRepository.save(student);
            log.info("Contact successfully added to Student with id:  " + studentID);
        return contactMapper.contactToContactDTO(contact);
    }


    /**
     * Adding Address to Contact
     * @throws ResourceNotFoundException if not found
     */
    @Override
    public StudentAddressDTO addAddressToStudent(StudentAddressDTO addressDTO, Long studentID) {
        Student student = studentRepository
                .findById(studentID)
                .orElseThrow(() -> new ResourceNotFoundException(studentID, Student.class));
        Address address = studentAddressMapper.addressDTOToAddress(addressDTO);
        studentRepository.save(student);
        addressRepository.save(address);
            log.info("Address successfully added to Student with id: " + studentID);
        return addressDTO;
    }


    /**
     * Update Student with Matching ID and save it to Database
     * @return StudentDTO object
     * @throws ResourceNotFoundException if not found
     */
    @Override
    public StudentDTO updateStudent(Long studentID, StudentDTO studentDTO) {
           studentRepository.findById(studentID)
                .orElseThrow(() -> new ResourceNotFoundException(studentID, Student.class));
           Student updatedStudent = studentMapper.studentDTOToStudent(studentDTO);
               updatedStudent.setId(studentID);
           studentRepository.save(updatedStudent);
               log.info("Student with id:" + studentID +  " successfully updated");
           return studentMapper.studentToStudentDTO(updatedStudent);
    }

    /**
     * Update Contact with Matching ID and save it to Database
     * @return ContactDTO
     * @throws ResourceNotFoundException if not found
     */
    @Override
    public StudentContactDTO updateContact(StudentContactDTO contactDTO, Long studentID, Long contactID) {
        Student student = studentRepository.findById(studentID)
                .orElseThrow(() -> new ResourceNotFoundException(studentID, Student.class));
        studentContactRepository.findById(contactID)
                .orElseThrow(() -> new ResourceNotFoundException(contactID, Contact.class));
        StudentContact updatedContact = contactMapper.contactDTOToContact(contactDTO);
            updatedContact.setId(contactID);
            student.setContact(updatedContact);
            updatedContact.setStudent(student);
        studentRepository.save(student);
        studentContactRepository.save(updatedContact);
            log.info("Contact with id: " + contactID + " successfully updated");
        return contactMapper.contactToContactDTO(updatedContact);
    }

    /**
     * Update Address with Matching ID and save it to Database
     * @return StudentAddressDTO
     * @throws ResourceNotFoundException if not found student
     */
    @Override
    public StudentAddressDTO updateAddress(StudentAddressDTO addressDTO, Long studentID, Long addressID) {
        Student student = studentRepository.findById(studentID)
                .orElseThrow(() -> new ResourceNotFoundException(studentID, Student.class));
        addressRepository.findById(addressID)
                .orElseThrow(() -> new ResourceNotFoundException(addressID, Address.class));
        StudentAddress updatedAddress = studentAddressMapper.addressDTOToAddress(addressDTO);
            updatedAddress.setId(addressID);
        studentRepository.save(student);
        addressRepository.save(updatedAddress);
            log.info("Address with id: " + addressID + " successfully updated");
        return studentAddressMapper.addressToAddressDTO(updatedAddress);
    }

    /**
     * Delete Student with matching id
     * @throws ResourceNotFoundException if not found
     */
    @Override
    public void deleteStudentByID(Long studentID) {
        Student student = studentRepository
                .findById(studentID)
                .orElseThrow(() -> new ResourceNotFoundException(studentID, Student.class));
        studentRepository.delete(student);
        log.info("Student with id:" + studentID +  " successfully deleted");
    }


    /**
     * Delete Contact from the Student with matching id and replaces it with default Contact
     * @throws ResourceNotFoundException if not found
     */
    @Override
    public void deleteStudentContact(Long studentID) {
        Student student = studentRepository
                .findById(studentID)
                .orElseThrow(() -> new ResourceNotFoundException(studentID, Student.class));
        StudentContact contact = student.getContactOptional().
                orElseThrow(() -> new ResourceNotFoundException(studentID, Student.class, StudentContact.class));
        StudentContact emptyContact = new StudentContact();
            emptyContact.setStudent(student);
            student.setContact(emptyContact);
        studentContactRepository.delete(contact);
        studentRepository.save(student);
        log.info("Contact successfully deleted from the Student with id: " + studentID);
    }

    /**
     * Delete Address from the Student with matching id and replaces it with default Address
     * @throws ResourceNotFoundException if not found
     */
    @Override
    public void deleteStudentAddress(Long studentID) {
        Student student = studentRepository
                .findById(studentID)
                .orElseThrow(() -> new ResourceNotFoundException(studentID, Student.class));
        StudentAddress address = student.getAddressOptional()
                .orElseThrow(() -> new ResourceNotFoundException(studentID, Student.class, Address.class));
        StudentAddress emptyAddress = new StudentAddress();
            emptyAddress.setStudent(student);
            student.setAddress(emptyAddress);
        addressRepository.delete(address);
        studentRepository.save(student);
        log.info("Address successfully deleted from the Student with id: " + studentID);
    }

}
