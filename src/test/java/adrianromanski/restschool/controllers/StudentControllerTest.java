package adrianromanski.restschool.controllers;

import adrianromanski.restschool.controllers.exception_handler.RestResponseEntityExceptionHandler;
import adrianromanski.restschool.controllers.person.StudentController;
import adrianromanski.restschool.domain.base_entity.enums.Gender;
import adrianromanski.restschool.exceptions.ResourceNotFoundException;
import adrianromanski.restschool.model.base_entity.AddressDTO;
import adrianromanski.restschool.model.base_entity.ContactDTO;
import adrianromanski.restschool.model.base_entity.person.StudentDTO;
import adrianromanski.restschool.services.person.student.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


import static adrianromanski.restschool.domain.base_entity.enums.FemaleName.CHARLOTTE;
import static adrianromanski.restschool.domain.base_entity.enums.Gender.FEMALE;
import static adrianromanski.restschool.domain.base_entity.enums.Gender.MALE;
import static adrianromanski.restschool.domain.base_entity.enums.LastName.*;
import static adrianromanski.restschool.domain.base_entity.enums.MaleName.ETHAN;
import static adrianromanski.restschool.domain.base_entity.enums.MaleName.SEBASTIAN;
import static org.hamcrest.Matchers.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class StudentControllerTest  extends AbstractRestControllerTest {

    public static final Long ID = 1L;
    public static final String STUDENTS = "/students/";
    public static final String TELEPHONE_NUMBER = "222-44-22";
    public static final String EMAIL = "Jimmy@Gmail.com";
    public static final String COUNTRY = "Poland";
    public static final String CITY = "Warsaw";
    public static final String POSTAL_CODE = "22-22";
    public static final String STREET_NAME = "Happy";

    @Mock
    StudentService studentService;

    @InjectMocks
    StudentController studentController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(studentController)
                .setControllerAdvice(RestResponseEntityExceptionHandler.class)
                .build();
    }

    StudentDTO createStudentDTO(Long id, String firstName, String lastName, Gender gender) {
        StudentDTO studentDTO = StudentDTO.builder().firstName(firstName).lastName(lastName).gender(gender).build();
        studentDTO.setId(id);
        return studentDTO;
    }

    StudentDTO createEthan() {
        return createStudentDTO(ID, ETHAN.get(), COOPER.get(), MALE);
    }

    StudentDTO createSebastian() {
        return createStudentDTO(2L, SEBASTIAN.get(), RODRIGUEZ.get(), MALE);
    }

    StudentDTO createCharlotte() {
        return createStudentDTO(3L, CHARLOTTE.get(), HENDERSON.get(), FEMALE);
    }


    @DisplayName("[GET], [Happy Path], [Method] = getAllStudents")
    @Test
    void getAllStudents() throws Exception {
        List<StudentDTO> students = Arrays.asList(createEthan(), createSebastian(), createCharlotte());

        when(studentService.getAllStudents()).thenReturn(students);

        mockMvc.perform(get(STUDENTS)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.students", hasSize(3)));
    }

    @DisplayName("[GET], [Happy Path], [Method] = getAllFemaleStudents")
    @Test
    void getAllFemaleStudents() throws Exception {
        List<StudentDTO> students = Collections.singletonList(createCharlotte());

        when(studentService.getAllFemaleStudents()).thenReturn(students);

        mockMvc.perform(get(STUDENTS + "female")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.students", hasSize(1)));
    }

    @DisplayName("[GET], [Happy Path], [Method] = getAllMaleStudents")
    @Test
    void getAllMaleStudents() throws Exception {
        List<StudentDTO> students = Arrays.asList(createEthan(), createSebastian());

        when(studentService.getAllMaleStudents()).thenReturn(students);

        mockMvc.perform(get(STUDENTS + "male")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.students", hasSize(2)));
    }

    @DisplayName("[GET], [Happy Path], [Method] = getStudentByFirstAndLastName")
    @Test
    void getStudentByFirstAndLastName() throws Exception {
        StudentDTO studentDTO = createEthan();

        when(studentService.getStudentByName(anyString(), anyString())).thenReturn(studentDTO);

        mockMvc.perform(get(STUDENTS + ETHAN.get() + "/" + COOPER.get())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(studentDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo(ETHAN.get())))
                .andExpect(jsonPath("$.lastName", equalTo(COOPER.get())))
                .andExpect(jsonPath("$.gender", equalTo(MALE.toString())));
    }

    @DisplayName("[GET], [Happy Path], [Method] = getStudentById")
    @Test
    void getStudentByID() throws Exception {
        StudentDTO studentDTO = createEthan();

        when(studentService.getStudentByID(anyLong())).thenReturn(studentDTO);

        mockMvc.perform(get(STUDENTS + ID)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo(ETHAN.get())))
                .andExpect(jsonPath("$.lastName", equalTo(COOPER.get())))
                .andExpect(jsonPath("$.gender", equalTo(MALE.toString())));
    }

    @DisplayName("[POST], [Happy Path], [Method] = createNewStudent")
    @Test
    void createNewStudent() throws Exception {
        StudentDTO returnDTO = createEthan();

        when(studentService.createNewStudent(any(StudentDTO.class))).thenReturn(returnDTO);

        mockMvc.perform(post(STUDENTS)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(returnDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", equalTo(ETHAN.get())))
                .andExpect(jsonPath("$.lastName", equalTo(COOPER.get())))
                .andExpect(jsonPath("$.gender", equalTo(MALE.toString())));
    }

    @DisplayName("[POST], [Happy Path], [Method] = createNewStudent")
    @Test
    void addContactToStudent() throws Exception {
        ContactDTO contactDTO = ContactDTO.builder().telephoneNumber(TELEPHONE_NUMBER).email(EMAIL).build();

        when(studentService.addContactToStudent(any(ContactDTO.class), anyLong())).thenReturn(contactDTO);

        mockMvc.perform(post(STUDENTS + "addContact/student-id-1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(contactDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.telephoneNumber", equalTo(TELEPHONE_NUMBER)))
                .andExpect(jsonPath("$.email", equalTo(EMAIL)));
    }

    @DisplayName("[POST], [Happy Path], [Method] = addAddressToStudent")
    @Test
    void addAddressToStudent() throws Exception {
        AddressDTO addressDTO = AddressDTO.builder().country(COUNTRY).city(CITY).postalCode(POSTAL_CODE).streetName(STREET_NAME).build();

        when(studentService.addAddressToStudent(any(AddressDTO.class), anyLong(), anyLong())).thenReturn(addressDTO);

        mockMvc.perform(post(STUDENTS + "addAddress/student-id-1/contact-id-1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(addressDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.country", equalTo(COUNTRY)))
                .andExpect(jsonPath("$.city", equalTo(CITY)))
                .andExpect(jsonPath("$.postalCode", equalTo(POSTAL_CODE)))
                .andExpect(jsonPath("$.streetName", equalTo(STREET_NAME)));
    }

    @DisplayName("[PUT], [Happy Path], [Method] = updateStudent")
    @Test
    void updateStudent() throws Exception {
        StudentDTO returnDTO = createEthan();
        returnDTO.setLastName("Roman");

        when(studentService.updateStudent(anyLong(), any(StudentDTO.class))).thenReturn(returnDTO);

        mockMvc.perform(put(STUDENTS + ID)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(returnDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo(ETHAN.get())))
                .andExpect(jsonPath("$.lastName", equalTo("Roman")))
                .andExpect(jsonPath("$.gender", equalTo(MALE.toString())));
    }


    @DisplayName("[PUT], [Happy Path], [Method] = updateContact")
    @Test
    void updateContact() throws Exception {
        ContactDTO contactDTO = ContactDTO.builder().telephoneNumber(TELEPHONE_NUMBER).email(EMAIL).build();

        when(studentService.updateContact(any(ContactDTO.class), anyLong(), anyLong())).thenReturn(contactDTO);

        mockMvc.perform(put(STUDENTS + "updateContact/student-1/contact-1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(contactDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.telephoneNumber", equalTo(TELEPHONE_NUMBER)))
                .andExpect(jsonPath("$.email", equalTo(EMAIL)));
    }

    @DisplayName("[PUT], [Happy Path], [Method] = updateAddress")
    @Test
    void updateAddress() throws Exception {
        AddressDTO addressDTO = AddressDTO.builder().country(COUNTRY).city(CITY).postalCode(POSTAL_CODE).streetName(STREET_NAME).build();

        when(studentService.updateAddress(any(AddressDTO.class), anyLong(), anyLong(), anyLong())).thenReturn(addressDTO);

        mockMvc.perform(put(STUDENTS + "updateAddress/student-1/contact-1/address-1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(addressDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.country", equalTo(COUNTRY)))
                .andExpect(jsonPath("$.city", equalTo(CITY)))
                .andExpect(jsonPath("$.postalCode", equalTo(POSTAL_CODE)))
                .andExpect(jsonPath("$.streetName", equalTo(STREET_NAME)));
    }

    @DisplayName("[DELETE], [Happy Path], [Method] = deleteStudentByID")
    @Test
    void deleteStudent() throws Exception {
        mockMvc.perform(delete(STUDENTS + 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(studentService).deleteStudentByID(anyLong());
    }


    @DisplayName("[DELETE], [Happy Path], [Method] = deleteStudentContact")
    @Test
    void deleteStudentContact() throws Exception {
        mockMvc.perform(delete(STUDENTS + "deleteContact/student-1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(studentService).deleteStudentContact(anyLong());
    }


    @DisplayName("[DELETE], [Happy Path], [Method] = deleteStudentAddress")
    @Test
    void deleteStudentAddress() throws Exception {
        mockMvc.perform(delete(STUDENTS + "deleteAddress/contact-1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(studentService).deleteStudentAddress(anyLong());
    }

    @DisplayName("[GET, PUT, DELETE], [Unhappy Path], [Reason] = Student with id 222 not found")
    @Test
    public void testNotFoundException() throws Exception {
        when(studentService.getStudentByID(anyLong())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(STUDENTS + 222)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}