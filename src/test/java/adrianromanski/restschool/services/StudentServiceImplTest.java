package adrianromanski.restschool.services;

import adrianromanski.restschool.domain.base_entity.address.Address;
import adrianromanski.restschool.domain.base_entity.Contact;
import adrianromanski.restschool.domain.base_entity.address.StudentAddress;
import adrianromanski.restschool.domain.person.Student;
import adrianromanski.restschool.domain.enums.Gender;
import adrianromanski.restschool.exceptions.ResourceNotFoundException;
import adrianromanski.restschool.mapper.base_entity.StudentAddressMapper;
import adrianromanski.restschool.mapper.base_entity.ContactMapper;
import adrianromanski.restschool.mapper.person.StudentMapper;
import adrianromanski.restschool.model.base_entity.address.AddressDTO;
import adrianromanski.restschool.model.base_entity.ContactDTO;
import adrianromanski.restschool.model.base_entity.address.StudentAddressDTO;
import adrianromanski.restschool.model.person.StudentDTO;
import adrianromanski.restschool.repositories.base_entity.AddressRepository;
import adrianromanski.restschool.repositories.base_entity.ContactRepository;
import adrianromanski.restschool.repositories.person.StudentRepository;
import adrianromanski.restschool.services.person.student.StudentService;
import adrianromanski.restschool.services.person.student.StudentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static adrianromanski.restschool.domain.enums.FemaleName.*;
import static adrianromanski.restschool.domain.enums.Gender.FEMALE;
import static adrianromanski.restschool.domain.enums.Gender.MALE;
import static adrianromanski.restschool.domain.enums.LastName.*;
import static adrianromanski.restschool.domain.enums.MaleName.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class StudentServiceImplTest {

    public static final Long ID = 1L;
    public static final String EMAIL = "EthanCool@Gmail.com";
    public static final String NUMBER = "222-444-22";
    public static final String WARSAW = "Warsaw";
    public static final String POLAND = "Poland";
    public static final String POSTAL_CODE = "22-44";
    public static final String SESAME = "Sesame";

    @Mock
    StudentRepository studentRepository;
    @Mock
    ContactRepository contactRepository;
    @Mock
    AddressRepository addressRepository;

    StudentService studentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        studentService = new StudentServiceImpl(StudentMapper.INSTANCE, ContactMapper.INSTANCE, StudentAddressMapper.INSTANCE,
                                                studentRepository, contactRepository, addressRepository);
    }

    private Student createStudent(Long id, String firstName, String lastName, Gender gender) {
        Student student = Student.builder().firstName(firstName).lastName(lastName).gender(gender).build();
        student.setId(id);
        student.setDateOfBirth(LocalDate.of(1992,11,03));
        Contact contact = getContact();
        StudentAddress address = getAddress();
        student.setContact(contact);
        student.setAddress(address);
        contact.setStudent(student);
        return student;
    }

    private StudentDTO createEthanDTO() {
        StudentDTO studentDTO = StudentDTO.builder().firstName(ETHAN.get()).lastName(COOPER.get()).gender(MALE).build();
        studentDTO.setId(ID);
        return studentDTO;
    }

    private Student createEthan() {
        return createStudent(ID, ETHAN.get(), COOPER.get(), MALE);
    }

    private Student createSebastian() {
        return createStudent(2L, SEBASTIAN.get(), RODRIGUEZ.get(), MALE);
    }

    private Student createCharlotte() {
        return createStudent(3L, CHARLOTTE.get(), HENDERSON.get(), FEMALE);
    }

    private List<Student> getStudents() {
        return Arrays.asList(createEthan(), createSebastian(), createCharlotte());
    }

    private StudentAddressDTO getAddressDTO() { return StudentAddressDTO.builder().country(POLAND).city(WARSAW).postalCode(POSTAL_CODE).streetName(SESAME).build(); }

    private StudentAddress getAddress() { return StudentAddress.builder().country(POLAND).city(WARSAW).postalCode(POSTAL_CODE).streetName(SESAME).build(); }

    private ContactDTO getContactDTO() { return ContactDTO.builder().email(EMAIL).telephoneNumber(NUMBER).build(); }

    private Contact getContact() { return Contact.builder().telephoneNumber(NUMBER).email(EMAIL).build(); }




    @DisplayName("[Happy Path], [Method] = getAllStudents")
    @Test
    void getAllStudents() {
        List<Student> students = getStudents();

        when(studentRepository.findAll()).thenReturn(students);

        List<StudentDTO> studentDTOS = studentService.getAllStudents();

        assertEquals(3, studentDTOS.size());
    }

    @DisplayName("[Happy Path], [Method] = getStudentById")
    @Test
    void getStudentById() {
        Student student = createEthan();

        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student));

        StudentDTO studentDTO = studentService.getStudentByID(ID);

        assertEquals(ETHAN.get(), studentDTO.getFirstName());
        assertEquals(COOPER.get(), studentDTO.getLastName());
        assertEquals(MALE, studentDTO.getGender());
        assertEquals(ID, studentDTO.getId());
    }

    @DisplayName("[Happy Path], [Method] = getAllFemaleStudents")
    @Test
    void getAllFemaleStudents() {
        List<Student> students = getStudents();

        when(studentRepository.findAll()).thenReturn(students);

        List<StudentDTO> studentDTOS = studentService.getAllFemaleStudents();

        assertEquals(1, studentDTOS.size());
    }

    @DisplayName("[Happy Path], [Method] = getAllMaleStudents")
    @Test
    void getAllMaleStudents() {
        List<Student> students = getStudents();

        when(studentRepository.findAll()).thenReturn(students);

        List<StudentDTO> studentDTOS = studentService.getAllMaleStudents();

        assertEquals(2, studentDTOS.size());
    }

    @DisplayName("[Happy Path], [Method] = getStudentByName")
    @Test
    void getStudentByName() {
        Student student = createEthan();

        when(studentRepository.findByFirstNameAndLastName(anyString(), anyString())).thenReturn(Optional.of(student));

        StudentDTO studentDTO = studentService.getStudentByName(ETHAN.get(), COOPER.get());

        assertEquals(ETHAN.get(), studentDTO.getFirstName());
        assertEquals(COOPER.get(), studentDTO.getLastName());
        assertEquals(MALE, studentDTO.getGender());
        assertEquals(ID, studentDTO.getId());
    }

    @DisplayName("[Happy Path], [Method] = getStudentsByAge")
    @Test
    void getStudentsByAge() {
        List<Student> students = getStudents();

        when(studentRepository.findAll()).thenReturn(students);

        Map<Long, List<StudentDTO>> returnDTO = studentService.getStudentsByAge();

        assertThat(returnDTO.containsKey(27L));
        assertEquals(returnDTO.size(), 1);
        assertEquals(returnDTO.get(27L).size(), 3);
    }

    @DisplayName("[Happy Path], [Method] = getStudentsByLocation")
    @Test
    void getStudentByLocation() {
        List<Student> students = getStudents();

        when(studentRepository.findAll()).thenReturn(students);

        Map<String, Map<String, List<StudentDTO>>> returnDTO = studentService.getStudentsByLocation();

        assertThat(returnDTO.containsKey(POLAND));
        assertThat(returnDTO.get(POLAND).containsKey(WARSAW));
        assertEquals(returnDTO.size(), 1);
    }


    @DisplayName("[Happy Path], [Method] = createNewStudent")
    @Test
    void createNewStudent() {
        StudentDTO studentDTO = createEthanDTO();
        Student savedStudent =  createEthan();

        when(studentRepository.save(any(Student.class))).thenReturn(savedStudent);

        StudentDTO returnDTO = studentService.createNewStudent(studentDTO);

        assertEquals(ETHAN.get(), returnDTO.getFirstName());
        assertEquals(COOPER.get(), returnDTO.getLastName());
        assertEquals(MALE, returnDTO.getGender());
        assertEquals(ID, returnDTO.getId());

        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @DisplayName("[Happy Path], [Method] = addContactToStudent")
    @Test
    void addContactToStudentHappyPath() {
        ContactDTO contactDTO = getContactDTO();
        Student student = createEthan();

        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student));

        ContactDTO returnDTO = studentService.addContactToStudent(contactDTO, ID);

        assertEquals(returnDTO.getEmail(), EMAIL);
        assertEquals(returnDTO.getTelephoneNumber(), NUMBER);

        verify(contactRepository, times(1)).save(any(Contact.class));
        verify(studentRepository, times(1)).save(any(Student.class));
    }



    @DisplayName("[Unhappy Path], [Method] = addContactToStudent")
    @Test
    void addContactToStudentUnHappyPath() {
        ContactDTO contactDTO = getContactDTO();

        Throwable ex = catchThrowable(() -> studentService.addContactToStudent(contactDTO, ID));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);

    }

    @DisplayName("[Happy Path], [Method] = addAddressToStudent")
    @Test
    void addAddressToStudentHappyPath() {
        StudentAddressDTO addressDTO = getAddressDTO();
        Address address = getAddress();
        Student student = createEthan();

        when(addressRepository.findById(anyLong())).thenReturn(Optional.of(address));
        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student));

        AddressDTO returnDTO = studentService.addAddressToStudent(addressDTO, ID);

        assertEquals(returnDTO.getCity(), WARSAW);
        assertEquals(returnDTO.getCountry(), POLAND);
        assertEquals(returnDTO.getPostalCode(), POSTAL_CODE);
        assertEquals(returnDTO.getStreetName(), SESAME);

        verify(studentRepository, times(1)).save(any(Student.class));
        verify(addressRepository, times(1)).save(any(Address.class));
    }

    @DisplayName("[Unhappy Path], [Method] = addAddressToStudent, [Reason] = Student not found")
    @Test
    void addAddressToStudentUnHappyPath() {
        StudentAddressDTO addressDTO = new StudentAddressDTO();

        Throwable ex = catchThrowable(() -> studentService.addAddressToStudent(addressDTO, ID));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }


    @DisplayName("[Happy Path], [Method] = updateStudent")
    @Test
    void updateStudentHappyPath() {
        StudentDTO studentDTO = createEthanDTO();
        Student savedStudent =  createEthan();

        when(studentRepository.findById(ID)).thenReturn(Optional.of(savedStudent));
        when(studentRepository.save(any(Student.class))).thenReturn(savedStudent);

        StudentDTO returnDTO = studentService.updateStudent(ID, studentDTO);

        assertEquals(ETHAN.get(), returnDTO.getFirstName());
        assertEquals(COOPER.get(), returnDTO.getLastName());
        assertEquals(MALE, returnDTO.getGender());
        assertEquals(ID, returnDTO.getId());

        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @DisplayName("[Unhappy Path], [Method] = updateStudent, [Reason] = Student with id 222 not found")
    @Test
    void updateStudentUnHappyPath() {
        StudentDTO studentDTO = createEthanDTO();

        Throwable ex = catchThrowable(() -> studentService.updateStudent(222L,studentDTO));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }

    @DisplayName("[Happy Path], [Method] = updateContact")
    @Test
    void updateContact() {
        ContactDTO contactDTO = getContactDTO();
        Contact contact = getContact();
        Student student = createEthan();

        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student));
        when(contactRepository.findById(anyLong())).thenReturn(Optional.of(contact));

        ContactDTO returnDTO = studentService.updateContact(contactDTO, ID, ID);

        assertEquals(returnDTO.getEmail(), EMAIL);

        verify(studentRepository).save(any(Student.class));
        verify(contactRepository).save(any(Contact.class));
    }

    @DisplayName("[Happy Path], [Method] = updateAddress")
    @Test
    void updateAddress() {
        StudentAddressDTO addressDTO = getAddressDTO();
        StudentAddress address = getAddress();
        Student student = createEthan();

        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student));
        when(addressRepository.findById(anyLong())).thenReturn(Optional.of(address));

        AddressDTO returnDTO = studentService.updateAddress(addressDTO,ID,ID);

        assertEquals(returnDTO.getCountry(), POLAND);
        assertEquals(returnDTO.getCity(), WARSAW);
        assertEquals(returnDTO.getPostalCode(), POSTAL_CODE);
        assertEquals(returnDTO.getStreetName(), SESAME);

        verify(studentRepository).save(any(Student.class));
        verify(addressRepository).save(any(Address.class));
    }


    @DisplayName("[Happy Path], [Method] = deleteStudentByID")
    @Test
    void deleteStudentByID() {
        Student student =  createEthan();

        when(studentRepository.findById(ID)).thenReturn(Optional.of(student));

        studentService.deleteStudentByID(ID);

        verify(studentRepository, times(1)).delete(student);
    }

    @DisplayName("[Unhappy Path], [Method] = deleteStudentByID, [Reason] = Student with id 222 not found")
    @Test
    void deleteStudentByIDUnHappyPath() {
        Throwable ex = catchThrowable(() -> studentService.deleteStudentByID(222L));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }

    @DisplayName("[Happy Path], [Method] = deleteStudentContact")
    @Test
    void deleteStudentContact() {
        Contact contact = getContact();
        Student student = createEthan();

        when(studentRepository.findById(ID)).thenReturn(Optional.of(student));

        studentService.deleteStudentContact(ID);

        verify(contactRepository, times(1)).delete(contact);
        verify(studentRepository, times(1)).save(student);
    }

    @DisplayName("[Happy Path], [Method] = deleteStudentAddress")
    @Test
    void deleteStudentAddress() {
        StudentAddress address = getAddress();
        Student student = createEthan();

        when(studentRepository.findById(ID)).thenReturn(Optional.of(student));

        studentService.deleteStudentAddress(ID);

        verify(addressRepository, times(1)).delete(address);
        verify(studentRepository, times(1)).save(student);
    }
}