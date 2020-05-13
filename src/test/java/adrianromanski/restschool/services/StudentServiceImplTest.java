package adrianromanski.restschool.services;

import adrianromanski.restschool.domain.base_entity.Address;
import adrianromanski.restschool.domain.base_entity.Contact;
import adrianromanski.restschool.domain.base_entity.person.Student;
import adrianromanski.restschool.domain.base_entity.enums.Gender;
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
import adrianromanski.restschool.services.person.student.StudentService;
import adrianromanski.restschool.services.person.student.StudentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.validation.constraints.Email;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static adrianromanski.restschool.domain.base_entity.enums.FemaleName.*;
import static adrianromanski.restschool.domain.base_entity.enums.Gender.FEMALE;
import static adrianromanski.restschool.domain.base_entity.enums.Gender.MALE;
import static adrianromanski.restschool.domain.base_entity.enums.LastName.*;
import static adrianromanski.restschool.domain.base_entity.enums.MaleName.*;
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

    StudentService studentService;

    @Mock
    StudentRepository studentRepository;

    @Mock
    ContactRepository contactRepository;

    @Mock
    AddressRepository addressRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        studentService = new StudentServiceImpl(StudentMapper.INSTANCE, ContactMapper.INSTANCE, AddressMapper.INSTANCE,
                                                studentRepository, contactRepository, addressRepository);
    }

    private Student createStudent(Long id, String firstName, String lastName, Gender gender) {
        Student student = Student.builder().firstName(firstName).lastName(lastName).gender(gender).build();
        student.setId(id);
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

    @DisplayName("[Happy Path], [Method] = getAllStudents")
    @Test
    void getAllStudents() {
        List<Student> students = getStudents();

        when(studentRepository.findAll()).thenReturn(students);

        List<StudentDTO> studentDTOS = studentService.getAllStudents();

        assertEquals(3, studentDTOS.size());
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

    @DisplayName("[Happy Path], [Method] = getStudentByFirstAndLastName")
    @Test
    void getStudentByFirstAndLastName() {
        Student student = createEthan();

        when(studentRepository.findByFirstNameAndLastName(anyString(), anyString())).thenReturn(Optional.of(student));

        StudentDTO studentDTO = studentService.getStudentByName(ETHAN.get(), COOPER.get());

        assertEquals(ETHAN.get(), studentDTO.getFirstName());
        assertEquals(COOPER.get(), studentDTO.getLastName());
        assertEquals(MALE, studentDTO.getGender());
        assertEquals(ID, studentDTO.getId());
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
        ContactDTO contactDTO = ContactDTO.builder().email(EMAIL).telephoneNumber(NUMBER).build();
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
        ContactDTO contactDTO = ContactDTO.builder().email(EMAIL).telephoneNumber(NUMBER).build();

        Throwable ex = catchThrowable(() -> studentService.addContactToStudent(contactDTO, ID));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);

    }

    @DisplayName("[Happy Path], [Method] = addAddressToStudent")
    @Test
    void addAddressToStudentHappyPath() {
        AddressDTO addressDTO = AddressDTO.builder().city(WARSAW).country(POLAND).postalCode(POSTAL_CODE).streetName(SESAME).build();
        Contact contact = Contact.builder().email(EMAIL).telephoneNumber(NUMBER).build();
        Student student = createEthan();

        when(contactRepository.findById(anyLong())).thenReturn(Optional.of(contact));
        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student));

        AddressDTO returnDTO = studentService.addAddressToStudent(addressDTO, ID, ID);

        assertEquals(returnDTO.getCity(), WARSAW);
        assertEquals(returnDTO.getCountry(), POLAND);
        assertEquals(returnDTO.getPostalCode(), POSTAL_CODE);
        assertEquals(returnDTO.getStreetName(), SESAME);

        verify(contactRepository, times(1)).save(any(Contact.class));
        verify(studentRepository, times(1)).save(any(Student.class));
        verify(addressRepository, times(1)).save(any(Address.class));
    }

    @DisplayName("[Unhappy Path], [Method] = addAddressToStudent, [Reason] = Student not found")
    @Test
    void addAddressToStudentUnHappyPath() {
        AddressDTO addressDTO = new AddressDTO();

        Throwable ex = catchThrowable(() -> studentService.addAddressToStudent(addressDTO, ID, ID));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }

    @DisplayName("[Unhappy Path], [Method] = addAddressToStudent, [Reason] = Contact not found")
    @Test
    void addAddressToStudentUnHappyPath2() {
        AddressDTO addressDTO = new AddressDTO();
        Student student = createEthan();

        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student));
        Throwable ex = catchThrowable(() -> studentService.addAddressToStudent(addressDTO, ID, ID));

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
        ContactDTO contactDTO = ContactDTO.builder().email(EMAIL).telephoneNumber(NUMBER).build();
        Contact contact = Contact.builder().email(EMAIL).telephoneNumber(NUMBER).build();
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
        AddressDTO addressDTO = AddressDTO.builder().country(POLAND).city(WARSAW).postalCode(POSTAL_CODE).streetName(SESAME).build();
        Address address = Address.builder().country(POLAND).city(WARSAW).postalCode(POSTAL_CODE).streetName(SESAME).build();
        Contact contact = Contact.builder().email(EMAIL).telephoneNumber(NUMBER).build();
        Student student = createEthan();

        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student));
        when(contactRepository.findById(anyLong())).thenReturn(Optional.of(contact));
        when(addressRepository.findById(anyLong())).thenReturn(Optional.of(address));

        AddressDTO returnDTO = studentService.updateAddress(addressDTO,ID,ID,ID);

        assertEquals(returnDTO.getCountry(), POLAND);
        assertEquals(returnDTO.getCity(), WARSAW);
        assertEquals(returnDTO.getPostalCode(), POSTAL_CODE);
        assertEquals(returnDTO.getStreetName(), SESAME);

        verify(studentRepository).save(any(Student.class));
        verify(contactRepository).save(any(Contact.class));
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
        Contact contact = Contact.builder().telephoneNumber(NUMBER).email(EMAIL).build();

        when(contactRepository.findByStudentId(ID)).thenReturn(Optional.of(contact));

        studentService.deleteStudentContact(ID);

        verify(contactRepository, times(1)).delete(contact);
    }

    @DisplayName("[Happy Path], [Method] = deleteStudentAddress")
    @Test
    void deleteStudentAddress() {
        Contact contact = Contact.builder().telephoneNumber(NUMBER).email(EMAIL).build();
        Address address = Address.builder().country(POLAND).city(WARSAW).postalCode(POSTAL_CODE).streetName(SESAME).build();
        contact.setAddress(address);

        when(contactRepository.findById(ID)).thenReturn(Optional.of(contact));

        studentService.deleteStudentAddress(ID);

        verify(addressRepository, times(1)).delete(address);
    }
}