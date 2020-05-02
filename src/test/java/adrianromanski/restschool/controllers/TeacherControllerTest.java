    package adrianromanski.restschool.controllers;

    import adrianromanski.restschool.controllers.exception_handler.RestResponseEntityExceptionHandler;
    import adrianromanski.restschool.controllers.person.TeacherController;
    import adrianromanski.restschool.domain.base_entity.enums.Gender;
    import adrianromanski.restschool.domain.base_entity.enums.Subjects;
    import adrianromanski.restschool.exceptions.ResourceNotFoundException;
    import adrianromanski.restschool.model.base_entity.person.TeacherDTO;
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
    import static adrianromanski.restschool.domain.base_entity.enums.FemaleName.ARIA;
    import static adrianromanski.restschool.domain.base_entity.enums.Gender.FEMALE;
    import static adrianromanski.restschool.domain.base_entity.enums.Gender.MALE;
    import static adrianromanski.restschool.domain.base_entity.enums.LastName.*;
    import static adrianromanski.restschool.domain.base_entity.enums.MaleName.BENJAMIN;
    import static adrianromanski.restschool.domain.base_entity.enums.MaleName.ETHAN;
    import static adrianromanski.restschool.domain.base_entity.enums.Subjects.*;
    import static org.hamcrest.Matchers.equalTo;
    import static org.hamcrest.Matchers.hasSize;

    import static org.mockito.Mockito.*;
    import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
    import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
    import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

    class TeacherControllerTest {

        public static final String TEACHERS = "/teachers/";
        public static final long ID = 1L;

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

        TeacherDTO createTeacherDTO(Long id, String firstName, String lastName, Gender gender, Subjects subject) {
            TeacherDTO teacherDTO = TeacherDTO.builder().firstName(firstName).lastName(lastName).gender(gender).build();
            teacherDTO.setId(id);
            teacherDTO.setSubject(subject);
            return teacherDTO;
        }

        TeacherDTO createEthan() {
            return createTeacherDTO(ID, ETHAN.get(), COOPER.get(), MALE, CHEMISTRY);
        }

        TeacherDTO createBenjamin() {
            return createTeacherDTO(2L, BENJAMIN.get(), RODRIGUEZ.get(), MALE, BIOLOGY);
        }

        TeacherDTO createAria() {
            return createTeacherDTO(3L, ARIA.get(), WILLIAMS.get(), FEMALE, PHYSICS);
        }

        @DisplayName("[GET], [Happy Path], [Method] = getAllStudents, [Expected] = List containing 3 Students")
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

        @DisplayName("[GET], [Happy Path], [Method] = getStudentByFirstAndLastName, [Expected] = StudentDTO with matching fields")
        @Test
        void getTeacherByFirstNameAndLastName() throws Exception {
            TeacherDTO teacherDTO = createEthan();

            when(teacherService.getTeacherByFirstNameAndLastName(ETHAN.get(), COOPER.get())).thenReturn(teacherDTO);

            mockMvc.perform(get(TEACHERS + ETHAN.get() + "/" + COOPER.get())
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(teacherDTO)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.firstName", equalTo(ETHAN.get())))
                    .andExpect(jsonPath("$.lastName", equalTo(COOPER.get())));
        }

        @DisplayName("[GET], [Happy Path], [Method] = getStudentById, [Expected] = StudentDTO with matching fields")
        @Test
        void getTeacherByID() throws Exception {
            TeacherDTO teacherDTO = createEthan();

            when(teacherService.getTeacherByID(ID)).thenReturn(teacherDTO);

            mockMvc.perform(get(TEACHERS + ID)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(teacherDTO)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.firstName", equalTo(ETHAN.get())))
                    .andExpect(jsonPath("$.lastName", equalTo(COOPER.get())));
        }

        @DisplayName("[GET], [Happy Path], [Method] = getTeachersBySpecialization, [Expected] = Map<Biology(2), Math(1), Chemistry(3)>")
        @Test
        void getTeachersBySpecialization() throws Exception {
            Map<Subjects, List<TeacherDTO>> map = new HashMap<>();
            map.putIfAbsent(BIOLOGY, Arrays.asList(createBenjamin(), createAria()));
            map.putIfAbsent(PHYSICS, Collections.singletonList(createBenjamin()));
            map.putIfAbsent(CHEMISTRY, Arrays.asList(createAria(), createEthan(), createBenjamin()));

            when(teacherService.getTeachersBySpecialization()).thenReturn(map);

            mockMvc.perform(get(TEACHERS + "specializations")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(map)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.BIOLOGY", hasSize(2)))
                    .andExpect(jsonPath("$.PHYSICS", hasSize(1)))
                    .andExpect(jsonPath("$.CHEMISTRY", hasSize(3)));

        }

        @DisplayName("[GET], [Happy Path], [Method] = getTeachersByYearsOfExperience, [Expected] = Map<5 Years(1), 12 Years(2)>")
        @Test
        void getTeachersByYearsOfExperience() throws Exception {
            Map<Long, List<TeacherDTO>> map = new HashMap<>();
            map.putIfAbsent(5L, Collections.singletonList(createEthan()));
            map.putIfAbsent(12L, Arrays.asList(createAria(), createBenjamin()));

            when(teacherService.getTeachersByYearsOfExperience()).thenReturn(map);

            mockMvc.perform(get(TEACHERS + "experience")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(map)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.5", hasSize(1)))
                    .andExpect(jsonPath("$.12", hasSize(2)));
        }

        @DisplayName("[POST], [Happy Path], [Method] = createNewTeacher, [Expected] = TeacherDTO  with matching fields")
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

        @DisplayName("[PUT], [Happy Path], [Method] = updateTeacher, [Expected] = TeacherDTO with updated fields")
        @Test
        void updateTeacher() throws Exception {
            TeacherDTO teacherDTO = createEthan();
            teacherDTO.setFirstName("Updated");

            when(teacherService.updateTeacher(teacherDTO.getId(), teacherDTO)).thenReturn(teacherDTO);

            mockMvc.perform(put(TEACHERS + teacherDTO.getId())
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(teacherDTO)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.firstName", equalTo("Updated")))
                    .andExpect(jsonPath("$.lastName", equalTo(COOPER.get())));
        }

        @DisplayName("[DELETE], [Happy Path], [Method] = deleteTeacherById, [Expected] = teacherService deleting student")
        @Test
        void deleteTeacherById() throws Exception {
            mockMvc.perform(delete(TEACHERS + ID)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());

            verify(teacherService).deleteTeacherById(ID);
        }

        @DisplayName("[GET, PUT, DELETE], [Unhappy Path], [Reason] = Teacher with id 222 not found")
        @Test
        public void testNotFoundException() throws Exception {

            when(teacherService.getTeacherByID(anyLong())).thenThrow(ResourceNotFoundException.class);

            mockMvc.perform(get(TEACHERS + 222)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
        }
}
