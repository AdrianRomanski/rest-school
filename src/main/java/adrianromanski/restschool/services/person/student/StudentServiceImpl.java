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
    public StudentDTO getStudentByName(String firstName, String lastName) {
        return studentMapper.studentToStudentDTO(studentRepository
                                .findByFirstNameAndLastName(firstName, lastName)
                                .orElseThrow(() -> new ResourceNotFoundException(firstName, lastName, Student.class)));
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
                .orElseThrow(() -> new ResourceNotFoundException(studentID, Student.class));
        Contact contact = contactMapper.contactDTOToContact(contactDTO);
            student.setContact(contact);
            contact.setStudent(student);
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
                .orElseThrow(() -> new ResourceNotFoundException(studentID, Student.class));
        Contact contact = contactRepository
                .findById(contactID)
                .orElseThrow(() -> new ResourceNotFoundException(contactID, Contact.class));
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
     *  Update Contact with Matching ID and save it to Database
     * @return ContactDTO object if successfully updated
     * @throws ResourceNotFoundException if not found student or contact
     */
    @Override
    public ContactDTO updateContact(ContactDTO contactDTO, Long studentID, Long contactID) {
        Student student = studentRepository.findById(studentID)
                .orElseThrow(() -> new ResourceNotFoundException(studentID, Student.class));
        contactRepository.findById(contactID)
                .orElseThrow(() -> new ResourceNotFoundException(contactID, Contact.class));
        Contact updatedContact = contactMapper.contactDTOToContact(contactDTO);
            updatedContact.setId(contactID);
            student.setContact(updatedContact);
            updatedContact.setStudent(student);
        studentRepository.save(student);
        contactRepository.save(updatedContact);
            log.info("Contact with id: " + contactID + " successfully updated");
        return contactMapper.contactToContactDTO(updatedContact);
    }

    /**
     *  Update Address with Matching ID and save it to Database
     * @return AddressDTO object if successfully updated
     * @throws ResourceNotFoundException if not found student, contact or address
     */
    @Override
    public AddressDTO updateAddress(AddressDTO addressDTO, Long studentID, Long contactID, Long addressID) {
        Student student = studentRepository.findById(studentID)
                .orElseThrow(() -> new ResourceNotFoundException(studentID, Student.class));
        Contact contact = contactRepository.findById(contactID)
                .orElseThrow(() -> new ResourceNotFoundException(contactID, Contact.class));
        addressRepository.findById(addressID)
                .orElseThrow(() -> new ResourceNotFoundException(addressID, Address.class));
        Address updatedAddress = addressMapper.addressDTOToAddress(addressDTO);
            updatedAddress.setId(addressID);
            contact.setAddress(updatedAddress);
            student.setContact(contact);
        studentRepository.save(student);
        contactRepository.save(contact);
        addressRepository.save(updatedAddress);
            log.info("Address with id: " + addressID + " successfully updated");
        return addressMapper.addressToAddressDTO(updatedAddress);
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
     * Delete Contact from the Student with matching id
     * @throws ResourceNotFoundException if not found
     */
    @Override
    public void deleteStudentContact(Long studentID) {
        Contact contact = contactRepository
                .findByStudentId(studentID)
                .orElseThrow(() -> new ResourceNotFoundException(studentID, Student.class));
        contactRepository.delete(contact);
        log.info("Contact successfully deleted");
    }

    /**
     * Delete Address from the Contact with matching id
     * @throws ResourceNotFoundException if not found
     */
    @Override
    public void deleteStudentAddress(Long contactID) {
        Contact contact = contactRepository
                .findById(contactID)
                .orElseThrow(() -> new ResourceNotFoundException(contactID, Contact.class));
        Address address = contact.getOptionalOfAddress()
                .orElseThrow(() -> new ResourceNotFoundException(Address.class));
        addressRepository.delete(address);
        log.info("Address successfully deleted");
    }

}
