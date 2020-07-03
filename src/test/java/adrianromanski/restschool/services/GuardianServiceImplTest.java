package adrianromanski.restschool.services;

import adrianromanski.restschool.domain.base_entity.address.Address;
import adrianromanski.restschool.domain.base_entity.address.GuardianAddress;
import adrianromanski.restschool.domain.base_entity.contact.Contact;
import adrianromanski.restschool.domain.base_entity.contact.GuardianContact;
import adrianromanski.restschool.domain.enums.Gender;
import adrianromanski.restschool.domain.person.Guardian;
import adrianromanski.restschool.domain.person.Student;
import adrianromanski.restschool.exceptions.DeleteBeforeInitializationException;
import adrianromanski.restschool.exceptions.ResourceNotFoundException;
import adrianromanski.restschool.exceptions.UpdateBeforeInitializationException;
import adrianromanski.restschool.mapper.base_entity.GuardianAddressMapper;
import adrianromanski.restschool.mapper.base_entity.GuardianContactMapper;
import adrianromanski.restschool.mapper.person.GuardianMapper;
import adrianromanski.restschool.mapper.person.StudentMapper;
import adrianromanski.restschool.model.base_entity.address.GuardianAddressDTO;
import adrianromanski.restschool.model.base_entity.contact.ContactDTO;
import adrianromanski.restschool.model.base_entity.contact.GuardianContactDTO;
import adrianromanski.restschool.model.person.GuardianDTO;
import adrianromanski.restschool.model.person.StudentDTO;
import adrianromanski.restschool.repositories.base_entity.AddressRepository;
import adrianromanski.restschool.repositories.base_entity.ContactRepository;
import adrianromanski.restschool.repositories.person.GuardianRepository;
import adrianromanski.restschool.repositories.person.StudentRepository;
import adrianromanski.restschool.services.person.guardian.GuardianService;
import adrianromanski.restschool.services.person.guardian.GuardianServiceImpl;
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

import static adrianromanski.restschool.domain.enums.FemaleName.CHARLOTTE;
import static adrianromanski.restschool.domain.enums.Gender.FEMALE;
import static adrianromanski.restschool.domain.enums.Gender.MALE;
import static adrianromanski.restschool.domain.enums.LastName.*;
import static adrianromanski.restschool.domain.enums.MaleName.ETHAN;
import static adrianromanski.restschool.domain.enums.MaleName.SEBASTIAN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class GuardianServiceImplTest {

    public static final long ID = 1L;
    public static final String COUNTRY = "Poland";
    public static final String CITY = "Warsaw";
    public static final String POSTAL_CODE = "22-44";
    public static final String STREET_NAME = "Sesame";
    public static final String EMAIL = "Email@gmail.com";
    public static final String EMERGENCY_NUMBER = "22-33";
    public static final String NUMBER = "21-34";
    public static final String E = "SomeEmail@gmail.com";

    GuardianService guardianService;

    @Mock
    GuardianRepository guardianRepository;

    @Mock
    AddressRepository addressRepository;

    @Mock
    StudentRepository studentRepository;

    @Mock
    ContactRepository contactRepository;

    @BeforeEach
    void beforeAll() {
        MockitoAnnotations.initMocks(this);

        guardianService = new GuardianServiceImpl(GuardianMapper.INSTANCE, GuardianAddressMapper.INSTANCE, GuardianContactMapper.INSTANCE, StudentMapper.INSTANCE,
                                                    guardianRepository, addressRepository, contactRepository, studentRepository);
    }

    GuardianDTO createEthanDTO() {
        GuardianDTO guardianDTO = GuardianDTO.builder().firstName(ETHAN.get()).lastName(HENDERSON.get()).build();
        guardianDTO.setId(ID);
        return guardianDTO;
    }

    Guardian createEthan() {
        Guardian guardian = Guardian.builder().firstName(ETHAN.get()).lastName(HENDERSON.get()).dateOfBirth(LocalDate.of(1992, 11, 3)).build();

        Student sebastian = createSebastian();
        Student charlotte = createCharlotte();
        guardian.getStudents().addAll(Arrays.asList(sebastian, charlotte));
        sebastian.setGuardian(guardian);
        charlotte.setGuardian(guardian);
        guardian.setId(ID);
        return guardian;
    }

    Student createStudent(Long id, String firstName, String lastName, Gender gender) {
        Student student = Student.builder().firstName(firstName).lastName(lastName).gender(gender).build();
        student.setId(id);
        return student;
    }
    Student createSebastian() {
        return createStudent(2L, SEBASTIAN.get(), RODRIGUEZ.get(), MALE);
    }
    Student createCharlotte() {
        return createStudent(3L, CHARLOTTE.get(), HENDERSON.get(), FEMALE);
    }

    private GuardianAddress getAddress() { return GuardianAddress.builder().country(COUNTRY).city(CITY).postalCode(POSTAL_CODE).streetName(STREET_NAME).build(); }
    private GuardianAddressDTO getAddressDTO() { return GuardianAddressDTO.builder().country(COUNTRY).city(CITY).postalCode(POSTAL_CODE).streetName(STREET_NAME).build(); }

    private GuardianContactDTO getContactDTO() { return GuardianContactDTO.builder().email(EMAIL).emergencyNumber(EMERGENCY_NUMBER).telephoneNumber(NUMBER).build(); }
    private GuardianContact getContact() { return GuardianContact.builder().email(EMAIL).emergencyNumber(EMERGENCY_NUMBER).telephoneNumber(NUMBER).build(); }



    @DisplayName("[Happy Path], [Method] = getAllGuardians")
    @Test
    void getAllLegalGuardians() {
        List<Guardian> legalGuardians = Arrays.asList(createEthan(), createEthan(), createEthan());

        when(guardianRepository.findAll()).thenReturn(legalGuardians);

        List<GuardianDTO> legalGuardiansDTO = guardianService.getAllGuardians();

        assertEquals(legalGuardians.size(), legalGuardiansDTO.size());
    }


    @DisplayName("[Happy Path], [Method] = getGuardianByID")
    @Test
    void getGuardianByID() {
        Guardian legalGuardian = createEthan();

        when(guardianRepository.findById(ID)).thenReturn(Optional.of(legalGuardian));

        GuardianDTO legalGuardianDTO = guardianService.getGuardianByID(ID);

        assertEquals(legalGuardianDTO.getFirstName(), ETHAN.get());
        assertEquals(legalGuardianDTO.getLastName(), HENDERSON.get());
        assertEquals(legalGuardianDTO.getId(), ID);

    }


    @DisplayName("[Happy Path], [Method] = getGuardianByFirstAndLastName")
    @Test
    void getLegalGuardianByFirstAndLastName() {
        Guardian legalGuardian = createEthan();

        when(guardianRepository.getGuardianByFirstNameAndLastName(anyString(), anyString()))
                .thenReturn(Optional.of(legalGuardian));

        GuardianDTO legalGuardianDTO = guardianService.getGuardianByFirstAndLastName(ETHAN.get(), COOPER.get());

        assertEquals(legalGuardianDTO.getFirstName(), ETHAN.get());
        assertEquals(legalGuardianDTO.getLastName(), HENDERSON.get());
        assertEquals(legalGuardianDTO.getId(), ID);
    }


    @DisplayName("[Happy Path], [Method] = getGuardiansByAge")
    @Test
    void getGuardiansByAge() {
        List<Guardian> guardians = Arrays.asList(createEthan(), createEthan());

        when(guardianRepository.findAll()).thenReturn(guardians);

        Map<Long, List<GuardianDTO>> guardiansByAge = guardianService.getGuardiansByAge();

        assertEquals(guardiansByAge.size(), 1);
        assertTrue(guardiansByAge.containsKey(27L));
    }


    @DisplayName("[Happy Path], [Method] = getAllStudentsForGuardian")
    @Test
    void getAllStudentsForGuardian() {
        Guardian guardian = createEthan();

        when(studentRepository.findAll()).thenReturn(guardian.getStudents());

        List<StudentDTO> studentsDTO = guardianService.getAllStudentsForGuardian(guardian.getId());

        assertEquals(studentsDTO.size(), 2);
        assertEquals(studentsDTO.get(0).getFirstName(), SEBASTIAN.get());
        assertEquals(studentsDTO.get(1).getFirstName(), CHARLOTTE.get());
    }


    @DisplayName("[Happy Path], [Method] = createNewGuardian")
    @Test
    void createNewLegalGuardian() {
        Guardian guardian = createEthan();

        GuardianDTO guardianDTO = createEthanDTO();

        when(guardianRepository.findById(anyLong())).thenReturn(Optional.of(guardian));
        when(guardianRepository.save(any(Guardian.class))).thenReturn(guardian);

        GuardianDTO returnDTO = guardianService.createNewGuardian(guardianDTO);

        assertEquals(returnDTO.getFirstName(), ETHAN.get());
        assertEquals(returnDTO.getLastName(), HENDERSON.get());
        assertEquals(returnDTO.getId(), ID);
    }


    @DisplayName("[Happy Path], [Method] = addAddress")
    @Test
    void addAddressHappyPath() {
        Guardian guardian = createEthan();
        GuardianAddressDTO addressDTO = getAddressDTO();

        when(guardianRepository.findById(anyLong())).thenReturn(Optional.of(guardian));

        GuardianAddressDTO returnDTO = guardianService.addAddress(ID, addressDTO);

        assertEquals(returnDTO.getCountry(), COUNTRY);
        assertEquals(returnDTO.getCity(), CITY);
        assertEquals(returnDTO.getStreetName(), STREET_NAME);
        assertEquals(returnDTO.getPostalCode(), POSTAL_CODE);

        verify(guardianRepository, times(1)).save(any(Guardian.class));
        verify(addressRepository, times(1)).save(any(GuardianAddress.class));
    }


    @DisplayName("[Unhappy Path], [Method] = addAddress")
    @Test
    void addAddressUnhappyPath() {
        GuardianAddressDTO addressDTO = getAddressDTO();

        Throwable ex = catchThrowable(() -> guardianService.addAddress(222L, addressDTO));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }


    @DisplayName("[Happy Path], [Method] = addContact")
    @Test
    void addContactHappyPath() {
        Guardian guardian = createEthan();
        GuardianContactDTO contactDTO = getContactDTO();

        when(guardianRepository.findById(anyLong())).thenReturn(Optional.of(guardian));

        GuardianContactDTO returnDTO = guardianService.addContact(1L, contactDTO);

        assertEquals(returnDTO.getEmail(), EMAIL);
        assertEquals(returnDTO.getEmergencyNumber(), EMERGENCY_NUMBER);
        assertEquals(returnDTO.getTelephoneNumber(), NUMBER);

        verify(guardianRepository, times(1)).save(any(Guardian.class));
        verify(contactRepository, times(1)).save(any(GuardianContact.class));
    }


    @DisplayName("[Unhappy Path], [Method] = addContact")
    @Test
    void addContactUnhappyPath() {
        GuardianContactDTO contactDTO = getContactDTO();

        Throwable ex = catchThrowable(() -> guardianService.addContact(222L, contactDTO));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }


    @DisplayName("[Happy Path], [Method] = updateTeacher")
    @Test
    void updateLegalGuardianHappyPath() {
        Guardian guardian = createEthan();

        GuardianDTO guardianDTO = createEthanDTO();
        guardianDTO.setFirstName("Updated");

        when(guardianRepository.findById(anyLong())).thenReturn(Optional.of(guardian));
        when(guardianRepository.save(any(Guardian.class))).thenReturn(guardian);

        GuardianDTO returnDTO = guardianService.updateGuardian(guardianDTO, ID);

        assertEquals(returnDTO.getFirstName(), "Updated");
        assertEquals(returnDTO.getLastName(), HENDERSON.get());
        assertEquals(returnDTO.getId(), ID);
    }


    @DisplayName("[Unhappy Path], [Method] = updateGuardian")
    @Test
    void updateLegalGuardianUnhappyPath() {
        GuardianDTO guardianDTO = createEthanDTO();

        Throwable ex = catchThrowable(() -> guardianService.updateGuardian(guardianDTO, 222L));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }


    @DisplayName("[Happy Path], [Method] = updateAddress")
    @Test
    void updateAddressHappyPath() {
        Guardian guardian = createEthan();
        GuardianAddress address = getAddress();
        GuardianAddressDTO addressDTO = getAddressDTO();
        guardian.setAddress(address);
        address.setGuardian(guardian);

        when(guardianRepository.findById(anyLong())).thenReturn(Optional.of(guardian));

        GuardianAddressDTO returnDTO = guardianService.updateAddress(1L, addressDTO);

        assertEquals(returnDTO.getCountry(), COUNTRY);
        assertEquals(returnDTO.getCity(), CITY);
        assertEquals(returnDTO.getStreetName(), STREET_NAME);
        assertEquals(returnDTO.getPostalCode(), POSTAL_CODE);
    }


    @DisplayName("[Unhappy Path], [Method] = updateAddress, [Reason] = Guardian with id not found")
    @Test
    void updateAddressUnhappyPathIDNotFound() {
        Guardian guardian = createEthan();
        GuardianAddress address = getAddress();
        GuardianAddressDTO addressDTO = getAddressDTO();
        guardian.setAddress(address);
        address.setGuardian(guardian);

        Throwable ex = catchThrowable(() -> guardianService.updateAddress(222L, addressDTO));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }


    @DisplayName("[Unhappy Path], [Method] = updateAddress, [Reason] = Address not initialized")
    @Test
    void updateAddressUnhappyPathNoInitialization() {
        GuardianAddressDTO addressDTO = getAddressDTO();
        Guardian guardian = createEthan();

        when(guardianRepository.findById(anyLong())).thenReturn(Optional.of(guardian));

        Throwable ex = catchThrowable(() -> guardianService.updateAddress(222L, addressDTO));

        assertThat(ex).isInstanceOf(UpdateBeforeInitializationException.class);
    }


    @DisplayName("[Happy Path], [Method] = updateContact")
    @Test
    void updateContactHappyPath() {
        Guardian guardian = createEthan();
        GuardianContact contact = getContact();
        GuardianContactDTO contactDTO = getContactDTO();
        guardian.setContact(contact);
        contact.setGuardian(guardian);

        when(guardianRepository.findById(anyLong())).thenReturn(Optional.of(guardian));

        GuardianContactDTO returnDTO = guardianService.updateContact(1L, contactDTO);

        assertEquals(returnDTO.getEmail(), EMAIL);
        assertEquals(returnDTO.getEmergencyNumber(), EMERGENCY_NUMBER);
        assertEquals(returnDTO.getTelephoneNumber(), NUMBER);

        verify(guardianRepository, times(1)).save(any(Guardian.class));
        verify(contactRepository, times(1)).save(any(GuardianContact.class));
    }


    @DisplayName("[Unhappy Path], [Method] = updateContact, [Reason] = Guardian with id not found")
    @Test
    void updateContactUnhappyPathIDNotFound() {
        Guardian guardian = createEthan();
        GuardianContact contact = getContact();
        GuardianContactDTO contactDTO = getContactDTO();
        guardian.setContact(contact);
        contact.setGuardian(guardian);

        Throwable ex = catchThrowable(() -> guardianService.updateContact(222L, contactDTO));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }


    @DisplayName("[Unhappy Path], [Method] = updateContact, [Reason] = Contact not initialized")
    @Test
    void updateContactUnhappyPathNoInitialization() {
        GuardianContactDTO contactDTO = getContactDTO();
        Guardian guardian = createEthan();

        when(guardianRepository.findById(anyLong())).thenReturn(Optional.of(guardian));

        Throwable ex = catchThrowable(() -> guardianService.updateContact(222L,  contactDTO));

        assertThat(ex).isInstanceOf(UpdateBeforeInitializationException.class);
    }


    @DisplayName("[Happy Path], [Method] = deleteGuardianByID")
    @Test
    void deleteLegalGuardianByIDHappyPath() {
        Guardian guardian = createEthan();

        when(guardianRepository.findById(anyLong())).thenReturn(Optional.of(guardian));
        guardianService.deleteGuardianByID(guardian.getId());

        verify(guardianRepository, times(1)).delete(guardian);
    }


    @DisplayName("[Unhappy Path], [Method] = deleteGuardianByID, [Reason] = Guardian with id 222 not found")
    @Test
    void deleteLegalGuardianByIDUnhappyPath() {
        Throwable ex = catchThrowable(() -> guardianService.deleteGuardianByID(222L));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }


    @DisplayName("[Happy Path], [Method] = deleteContact")
    @Test
    void deleteContactHappyPath() {
        Guardian guardian = createEthan();
        GuardianContact contact = getContact();
        guardian.setContact(contact);

        when(guardianRepository.findById(anyLong())).thenReturn(Optional.of(guardian));

        guardianService.deleteContact(1L);

        verify(guardianRepository, times(1)).save(any(Guardian.class));
        verify(contactRepository, times(1)).delete(any(Contact.class));
    }


    @DisplayName("[Unhappy Path], [Method] = deleteContact, [Reason] = Guardian not found")
    @Test
    void deleteContactUnhappyPathGuardian() {
        Throwable ex = catchThrowable(() -> guardianService.deleteContact(222L));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }


    @DisplayName("[Unhappy Path], [Method] = deleteContact, [Reason] = Contact not initialized")
    @Test
    void deleteContactUnhappyPathInitialization() {
        Guardian guardian = createEthan();

        when(guardianRepository.findById(anyLong())).thenReturn(Optional.of(guardian));

        Throwable ex = catchThrowable(() -> guardianService.deleteContact(222L));

        assertThat(ex).isInstanceOf(DeleteBeforeInitializationException.class);
    }


    @DisplayName("[Happy Path], [Method] = deleteAddress")
    @Test
    void deleteAddressHappyPath() {
        Guardian guardian = createEthan();
        GuardianAddress address = getAddress();

        guardian.setAddress(address);

        when(guardianRepository.findById(anyLong())).thenReturn(Optional.of(guardian));

        guardianService.deleteAddress(1L);

        verify(guardianRepository, times(1)).save(any(Guardian.class));
        verify(addressRepository, times(1)).delete(any(Address.class));
    }


    @DisplayName("[Unhappy Path], [Method] = deleteAddress, [Reason] = Guardian not found")
    @Test
    void deleteAddressUnhappyPathGuardian() {
        Throwable ex = catchThrowable(() -> guardianService.deleteAddress(222L));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }


    @DisplayName("[Unhappy Path], [Method] = deleteAddress, [Reason] = Address not initialized")
    @Test
    void deleteAddressUnhappyPathInitialization() {
        Guardian guardian = createEthan();

        when(guardianRepository.findById(anyLong())).thenReturn(Optional.of(guardian));

        Throwable ex = catchThrowable(() -> guardianService.deleteAddress(222L));

        assertThat(ex).isInstanceOf(DeleteBeforeInitializationException.class);
    }
}

