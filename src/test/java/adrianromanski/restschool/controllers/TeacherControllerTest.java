    package adrianromanski.restschool.controllers;

    import adrianromanski.restschool.controllers.exception_handler.RestResponseEntityExceptionHandler;
    import adrianromanski.restschool.controllers.person.TeacherController;
    import adrianromanski.restschool.domain.base_entity.contact.TeacherContact;
    import adrianromanski.restschool.domain.enums.Gender;
    import adrianromanski.restschool.domain.enums.Subjects;
    import adrianromanski.restschool.exceptions.ResourceNotFoundException;
    import adrianromanski.restschool.model.base_entity.address.AddressDTO;
    import adrianromanski.restschool.model.base_entity.address.TeacherAddressDTO;
    import adrianromanski.restschool.model.base_entity.contact.TeacherContactDTO;
    import adrianromanski.restschool.model.event.ExamDTO;
    import adrianromanski.restschool.model.person.StudentDTO;
    import adrianromanski.restschool.model.person.TeacherDTO;
    import adrianromanski.restschool.services.person.teacher.TeacherService;
    import org.junit.jupiter.api.BeforeEach;
    import org.junit.jupiter.api.DisplayName;
    import org.junit.jupiter.api.Test;
    import org.mockito.InjectMocks;
    import org.mockito.Mock;
    import org.mockito.MockitoAnnotations;
    import org.springframework.http.MediaType;
    import org.springframework.test.web.servlet.MockMvc;
    import org.springframework.test.web.servlet.setup.MockMvcBuilders;

    import java.util.*;

    import static adrianromanski.restschool.controllers.AbstractRestControllerTest.asJsonString;
    import static adrianromanski.restschool.domain.enums.FemaleName.ARIA;
    import static adrianromanski.restschool.domain.enums.Gender.FEMALE;
    import static adrianromanski.restschool.domain.enums.Gender.MALE;
    import static adrianromanski.restschool.domain.enums.LastName.*;
    import static adrianromanski.restschool.domain.enums.MaleName.BENJAMIN;
    import static adrianromanski.restschool.domain.enums.MaleName.ETHAN;
    import static adrianromanski.restschool.domain.enums.Subjects.*;
    import static org.hamcrest.Matchers.equalTo;
    import static org.hamcrest.Matchers.hasSize;

    import static org.mockito.Mockito.*;
    import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
    import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
    import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

    class TeacherControllerTest {

        public static final String TEACHERS = "/teachers/";
        public static final long ID = 1L;
        public static final String NAME = "Final Math";
        public static final String COUNTRY = "Poland";
        public static final String CITY = "Warsaw";
        public static final String CODE = "222";
        public static final String STREET_NAME = "Sesame";
        public static final String EMAIL = "Ethan@Gmail.com";
        public static final String EMERGENCY_NUMBER = "22-22-11";
        public static final String NUMBER = "22-12-22";

        MockMvc mockMvc;

        @Mock
        TeacherService teacherService;

        @InjectMocks
        TeacherController teacherController;

        @BeforeEach
        void setUp() {
            MockitoAnnotations.initMocks(this);

            mockMvc = MockMvcBuilders.standaloneSetup(teacherController)
                    .setControllerAdvice(RestResponseEntityExceptionHandler.class)
                    .build();
        }

        private TeacherDTO createTeacherDTO(Long id, String firstName, String lastName, Gender gender, Subjects subject) {
            TeacherDTO teacherDTO = TeacherDTO.builder().firstName(firstName).lastName(lastName).gender(gender).build();
            teacherDTO.setId(id);
            teacherDTO.setSubject(subject);
            return teacherDTO;
        }

        private TeacherDTO createEthan() {
            return createTeacherDTO(ID, ETHAN.get(), COOPER.get(), MALE, CHEMISTRY);
        }

        private TeacherDTO createBenjamin() { return createTeacherDTO(2L, BENJAMIN.get(), RODRIGUEZ.get(), MALE, BIOLOGY); }

        private TeacherDTO createAria() {
            return createTeacherDTO(3L, ARIA.get(), WILLIAMS.get(), FEMALE, PHYSICS);
        }

        private ExamDTO createExamDTO() { return ExamDTO.builder().name(NAME).build(); }

        private TeacherAddressDTO createAddressDTO() { return TeacherAddressDTO.builder().country(COUNTRY).city(CITY).postalCode(CODE).streetName(STREET_NAME).build(); }

        private TeacherContactDTO createContactDTO() { return TeacherContactDTO.builder().email(EMAIL).emergencyNumber(EMERGENCY_NUMBER).telephoneNumber(NUMBER).build(); }



        @DisplayName("[GET], [Happy Path], [Method] = getTeacherByID")
        @Test
        void getTeacherByID() throws Exception {
            TeacherDTO teacherDTO = createEthan();

            when(teacherService.getTeacherByID(ID)).thenReturn(teacherDTO);

            mockMvc.perform(get(TEACHERS + "getByID/teacher-1")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(teacherDTO)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.firstName", equalTo(ETHAN.get())))
                    .andExpect(jsonPath("$.lastName", equalTo(COOPER.get())));
        }


        @DisplayName("[GET], [Happy Path], [Method] = getTeacherByFirstNameAndLastName")
        @Test
        void getTeacherByFirstNameAndLastName() throws Exception {
            TeacherDTO teacherDTO = createEthan();

            when(teacherService.getTeacherByFirstNameAndLastName(ETHAN.get(), COOPER.get())).thenReturn(teacherDTO);

            mockMvc.perform(get(TEACHERS + "getByName/" + ETHAN.get() + "-" + COOPER.get())
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(teacherDTO)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.firstName", equalTo(ETHAN.get())))
                    .andExpect(jsonPath("$.lastName", equalTo(COOPER.get())));
        }


        @DisplayName("[GET], [Happy Path], [Method] = getAllTeachers")
        @Test
        void getAllTeachers() throws Exception {
            List<TeacherDTO> teacherDTOList = Arrays.asList(createEthan(), createBenjamin(), createAria());

            when(teacherService.getAllTeachers()).thenReturn(teacherDTOList);

            mockMvc.perform(get(TEACHERS)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(teacherDTOList)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.teachers", hasSize(3)));
        }


        @DisplayName("[GET], [Happy Path], [Method] = getTeachersBySpecialization")
        @Test
        void getTeachersBySpecialization() throws Exception {
            Map<Subjects, List<TeacherDTO>> map = new HashMap<>();
            map.putIfAbsent(BIOLOGY, Arrays.asList(createBenjamin(), createAria()));
            map.putIfAbsent(PHYSICS, Collections.singletonList(createBenjamin()));
            map.putIfAbsent(CHEMISTRY, Arrays.asList(createAria(), createEthan(), createBenjamin()));

            when(teacherService.getTeachersBySpecialization()).thenReturn(map);

            mockMvc.perform(get(TEACHERS + "groupedBy/specializations")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(map)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.BIOLOGY", hasSize(2)))
                    .andExpect(jsonPath("$.PHYSICS", hasSize(1)))
                    .andExpect(jsonPath("$.CHEMISTRY", hasSize(3)));

        }


        @DisplayName("[GET], [Happy Path], [Method] = getTeachersByYearsOfExperience")
        @Test
        void getTeachersByYearsOfExperience() throws Exception {
            Map<Long, List<TeacherDTO>> map = new HashMap<>();
            map.putIfAbsent(5L, Collections.singletonList(createEthan()));
            map.putIfAbsent(12L, Arrays.asList(createAria(), createBenjamin()));

            when(teacherService.getTeachersByYearsOfExperience()).thenReturn(map);

            mockMvc.perform(get(TEACHERS + "groupedBy/experience")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(map)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.5", hasSize(1)))
                    .andExpect(jsonPath("$.12", hasSize(2)));
        }


        @DisplayName("[POST], [Happy Path], [Method] = addExamForClass")
        @Test
        void addExamForClass() throws Exception {
            ExamDTO examDTO = createExamDTO();

            when(teacherService.addExamForClass(anyLong(), any(ExamDTO.class))).thenReturn(examDTO);

            mockMvc.perform(post(TEACHERS + "addExamToClass/teacher-1")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(examDTO)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name", equalTo(NAME)));
        }


        @DisplayName("[POST], [Happy Path], [Method] = addCorrectionExamToStudent")
        @Test
        void addCorrectionExamForStudent() throws Exception {
            ExamDTO examDTO = createExamDTO();

            when(teacherService.addCorrectionExamToStudent(anyLong(), anyLong(), any(ExamDTO.class))).thenReturn(examDTO);

            mockMvc.perform(post(TEACHERS + "addExamToStudent/teacher-1/student-1")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(examDTO)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name", equalTo(NAME)));
        }


        @DisplayName("[POST], [Happy Path], [Method] = addNewStudentToClass")
        @Test
        void addNewStudentToClass() throws Exception {
            StudentDTO studentDTO = StudentDTO.builder().firstName(ETHAN.get()).build();

            when(teacherService.addNewStudentToClass(anyLong(), any(StudentDTO.class))).thenReturn(studentDTO);

            mockMvc.perform(post(TEACHERS + "addStudent/teacher-1")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(studentDTO)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.firstName", equalTo(ETHAN.get())));
        }


        @DisplayName("[POST], [Happy Path], [Method] = createNewTeacher")
        @Test
        void createNewTeacher() throws Exception {
            TeacherDTO teacherDTO = createEthan();

            when(teacherService.createNewTeacher(any(TeacherDTO.class))).thenReturn(teacherDTO);

            mockMvc.perform(post(TEACHERS)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(asJsonString(teacherDTO)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.firstName", equalTo(ETHAN.get())))
                    .andExpect(jsonPath("$.lastName", equalTo(COOPER.get())));
        }


        @DisplayName("[POST], [Happy Path], [Method] = addAddressToTeacher")
        @Test
        void addAddressToTeacher() throws Exception {
            TeacherAddressDTO addressDTO = createAddressDTO();

            when(teacherService.addAddressToTeacher(anyLong(), any(TeacherAddressDTO.class))).thenReturn(addressDTO);

            mockMvc.perform(post(TEACHERS + "addAddress/teacher-1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(asJsonString(addressDTO)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.country", equalTo(COUNTRY)))
                    .andExpect(jsonPath("$.city", equalTo(CITY)))
                    .andExpect(jsonPath("$.postalCode", equalTo(CODE)))
                    .andExpect(jsonPath("$.streetName", equalTo(STREET_NAME)));
        }


        @DisplayName("[POST], [Happy Path], [Method] = addContactToTeacher")
        @Test
        void addContactToTeacher() throws Exception {
            TeacherContactDTO contactDTO = createContactDTO();

            when(teacherService.addContactToTeacher(anyLong(), any(TeacherContactDTO.class))).thenReturn(contactDTO);

            mockMvc.perform(post(TEACHERS + "addContact/teacher-1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(asJsonString(contactDTO)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.email", equalTo(EMAIL)))
                    .andExpect(jsonPath("$.telephoneNumber", equalTo(NUMBER)))
                    .andExpect(jsonPath("$.emergencyNumber", equalTo(EMERGENCY_NUMBER)));
        }


        @DisplayName("[PUT], [Happy Path], [Method] = changeClassPresident")
        @Test
        void changeClassPresident() throws Exception {
            TeacherDTO teacherDTO = createEthan();

            when(teacherService.changeClassPresident(anyLong(), anyLong())).thenReturn(teacherDTO);

            mockMvc.perform(put(TEACHERS + "changePresident/teacher-1/student-1")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(teacherDTO)))
                    .andExpect(status().isOk());
        }


        @DisplayName("[PUT], [Happy Path], [Method] = updateTeacher")
        @Test
        void updateTeacher() throws Exception {
            TeacherDTO teacherDTO = createEthan();
            teacherDTO.setFirstName("Updated");

            when(teacherService.updateTeacher(anyLong(), any(TeacherDTO.class))).thenReturn(teacherDTO);

            mockMvc.perform(put(TEACHERS + "updateTeacher/teacher-1")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(teacherDTO)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.firstName", equalTo("Updated")))
                    .andExpect(jsonPath("$.lastName", equalTo(COOPER.get())));
        }


        @DisplayName("[PUT], [Happy Path], [Method] = updateAddress")
        @Test
        void updateAddress() throws Exception {
            TeacherAddressDTO addressDTO = createAddressDTO();
            addressDTO.setCity("Updated");

            when(teacherService.updateAddress(anyLong(), any(TeacherAddressDTO.class))).thenReturn(addressDTO);

            mockMvc.perform(put(TEACHERS + "updateAddress/teacher-1")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(addressDTO)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.country", equalTo(COUNTRY)))
                    .andExpect(jsonPath("$.city", equalTo("Updated")))
                    .andExpect(jsonPath("$.postalCode", equalTo(CODE)))
                    .andExpect(jsonPath("$.streetName", equalTo(STREET_NAME)));
        }

        @DisplayName("[PUT], [Happy Path], [Method] = updateContact")
        @Test
        void updateContact() throws Exception {
            TeacherContactDTO contactDTO = createContactDTO();
            contactDTO.setEmail("Updated@Gmail.com");

            when(teacherService.updateContact(anyLong(), any(TeacherContactDTO.class))).thenReturn(contactDTO);

            mockMvc.perform(put(TEACHERS + "updateContact/teacher-1")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(contactDTO)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.email", equalTo("Updated@Gmail.com")))
                    .andExpect(jsonPath("$.telephoneNumber", equalTo(NUMBER)))
                    .andExpect(jsonPath("$.emergencyNumber", equalTo(EMERGENCY_NUMBER)));
        }


        @DisplayName("[DELETE], [Happy Path], [Method] = removeStudentFromClass")
        @Test
        void removeStudentFromClass() throws Exception {
            mockMvc.perform(delete(TEACHERS + "removeStudent/teacher-1/student-1")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());

            verify(teacherService).removeStudentFromClass(ID, ID);
        }


        @DisplayName("[DELETE], [Happy Path], [Method] = deleteTeacherById")
        @Test
        void deleteTeacherById() throws Exception {
            mockMvc.perform(delete(TEACHERS + ID)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());

            verify(teacherService).deleteTeacherById(ID);
        }


        @DisplayName("[DELETE], [Happy Path], [Method] = deleteContact")
        @Test
        void deleteContact() throws Exception {
            mockMvc.perform(delete(TEACHERS + "deleteContact/teacher-1")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());

            verify(teacherService).deleteContact(ID);
        }


        @DisplayName("[DELETE], [Happy Path], [Method] = deleteAddress")
        @Test
        void deleteAddress() throws Exception {
            mockMvc.perform(delete(TEACHERS + "deleteAddress/teacher-1")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());

            verify(teacherService).deleteAddress(ID);
        }


        @DisplayName("[GET, PUT, DELETE], [Unhappy Path], [Reason] = Teacher with id 222 not found")
        @Test
        public void testNotFoundException() throws Exception {

            when(teacherService.getTeacherByID(anyLong())).thenThrow(ResourceNotFoundException.class);

            mockMvc.perform(get(TEACHERS + "getByID/teacher-222")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
        }
}
