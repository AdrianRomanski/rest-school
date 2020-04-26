    package adrianromanski.restschool.controllers;

    import adrianromanski.restschool.controllers.exception_handler.RestResponseEntityExceptionHandler;
    import adrianromanski.restschool.controllers.person.TeacherController;
    import adrianromanski.restschool.domain.base_entity.person.Teacher;
    import adrianromanski.restschool.domain.base_entity.person.enums.Gender;
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

    import java.time.LocalDate;
    import java.util.*;

    import static adrianromanski.restschool.controllers.AbstractRestControllerTest.asJsonString;
    import static adrianromanski.restschool.domain.base_entity.person.enums.Gender.FEMALE;
    import static adrianromanski.restschool.domain.base_entity.person.enums.Gender.MALE;
    import static org.hamcrest.Matchers.equalTo;
    import static org.hamcrest.Matchers.hasSize;

    import static org.mockito.Mockito.*;
    import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
    import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
    import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

    class TeacherControllerTest {

        public static final String WALTER = "Walter";
        public static final String WHITE = "White";
        public static final String TEACHERS = "/teachers/";
        public static final long ID = 1L;
        public static final String CHEMISTRY = "Chemistry";
        public static final String BIOLOGY = "Biology";
        public static final String PHYSICS = "Physics";

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

        TeacherDTO createTeacherDTO(Long id, String firstName, String lastName, Gender gender, String specialization) {
            TeacherDTO teacherDTO = TeacherDTO.builder().firstName(firstName).lastName(lastName).gender(gender).build();
            teacherDTO.setId(id);
            teacherDTO.setSpecialization(specialization);
            return teacherDTO;
        }

        TeacherDTO createWalter() {
            return createTeacherDTO(ID, WALTER, WHITE, MALE, CHEMISTRY);
        }

        TeacherDTO createPeter() {
            return createTeacherDTO(2L, "Peter", "Parker", MALE, BIOLOGY);
        }

        TeacherDTO createMarie() {
            return createTeacherDTO(3L, "Marie", "Curie", FEMALE, PHYSICS);
        }

        @DisplayName("[GET], [Happy Path], [Method] = getAllStudents, [Expected] = List containing 3 Students")
        @Test
        void getAllTeachers() throws Exception {
            List<TeacherDTO> teacherDTOList = Arrays.asList(createWalter(), createPeter(), createMarie());

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
            TeacherDTO teacherDTO = createWalter();

            when(teacherService.getTeacherByFirstNameAndLastName(WALTER, WHITE)).thenReturn(teacherDTO);

            mockMvc.perform(get(TEACHERS + WALTER + "/" + WHITE)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(teacherDTO)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.firstName", equalTo(WALTER)))
                    .andExpect(jsonPath("$.lastName", equalTo(WHITE)));
        }

        @DisplayName("[GET], [Happy Path], [Method] = getStudentById, [Expected] = StudentDTO with matching fields")
        @Test
        void getTeacherByID() throws Exception {
            TeacherDTO teacherDTO = createWalter();

            when(teacherService.getTeacherByID(ID)).thenReturn(teacherDTO);

            mockMvc.perform(get(TEACHERS + ID)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(teacherDTO)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.firstName", equalTo(WALTER)))
                    .andExpect(jsonPath("$.lastName", equalTo(WHITE)));
        }

        @DisplayName("[GET], [Happy Path], [Method] = getTeachersBySpecialization, [Expected] = Map<Biology(2), Math(1), Chemistry(3)>")
        @Test
        void getTeachersBySpecialization() throws Exception {
            Map<String, List<TeacherDTO>> map = new HashMap<>();
            map.putIfAbsent("Biology", Arrays.asList(createPeter(), createPeter()));
            map.putIfAbsent("Physics", Collections.singletonList(createMarie()));
            map.putIfAbsent("Chemistry", Arrays.asList(createWalter(), createWalter(), createWalter()));

            when(teacherService.getTeachersBySpecialization()).thenReturn(map);

            mockMvc.perform(get(TEACHERS + "specializations")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(map)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.Biology", hasSize(2)))
                    .andExpect(jsonPath("$.Physics", hasSize(1)))
                    .andExpect(jsonPath("$.Chemistry", hasSize(3)));

        }

        @DisplayName("[GET], [Happy Path], [Method] = getTeachersByYearsOfExperience, [Expected] = Map<5 Years(1), 12 Years(2)>")
        @Test
        void getTeachersByYearsOfExperience() throws Exception {
            Map<Long, List<TeacherDTO>> map = new HashMap<>();
            map.putIfAbsent(5L, Collections.singletonList(createWalter()));
            map.putIfAbsent(12L, Arrays.asList(createMarie(), createPeter()));

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
            TeacherDTO teacherDTO = createWalter();

            when(teacherService.createNewTeacher(any(TeacherDTO.class))).thenReturn(teacherDTO);

            mockMvc.perform(post(TEACHERS)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(asJsonString(teacherDTO)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.firstName", equalTo(WALTER)))
                    .andExpect(jsonPath("$.lastName", equalTo(WHITE)));
        }

        @DisplayName("[PUT], [Happy Path], [Method] = updateTeacher, [Expected] = TeacherDTO with updated fields")
        @Test
        void updateTeacher() throws Exception {
            TeacherDTO teacherDTO = createWalter();
            teacherDTO.setFirstName("Updated");

            when(teacherService.updateTeacher(teacherDTO.getId(), teacherDTO)).thenReturn(teacherDTO);

            mockMvc.perform(put(TEACHERS + teacherDTO.getId())
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(teacherDTO)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.firstName", equalTo("Updated")))
                    .andExpect(jsonPath("$.lastName", equalTo(WHITE)));
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
