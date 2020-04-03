    package adrianromanski.restschool.controllers;

    import adrianromanski.restschool.exceptions.ResourceNotFoundException;
    import adrianromanski.restschool.model.base_entity.person.TeacherDTO;
    import adrianromanski.restschool.services.teacher.TeacherService;
    import org.junit.jupiter.api.BeforeEach;
    import org.junit.jupiter.api.Test;
    import org.mockito.InjectMocks;
    import org.mockito.Mock;
    import org.mockito.MockitoAnnotations;
    import org.springframework.http.MediaType;
    import org.springframework.test.web.servlet.MockMvc;
    import org.springframework.test.web.servlet.setup.MockMvcBuilders;

    import java.util.Arrays;
    import java.util.List;

    import static adrianromanski.restschool.controllers.AbstractRestControllerTest.asJsonString;
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

        private TeacherDTO initTeacherDTO() {
            TeacherDTO teacherDTO = new TeacherDTO();
            teacherDTO.setFirstName(WALTER);
            teacherDTO.setLastName(WHITE);
            teacherDTO.setId(ID);
            return teacherDTO;
        }

        @Test
        void getAllTeachers() throws Exception {
            List<TeacherDTO> teacherDTOList = Arrays.asList(new TeacherDTO(), new TeacherDTO(), new TeacherDTO());

            when(teacherService.getAllTeachers()).thenReturn(teacherDTOList);

            mockMvc.perform(get(TEACHERS)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(teacherDTOList)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.teachers", hasSize(3)));
        }

        @Test
        void getTeacherByFirstNameAndLastName() throws Exception {
            TeacherDTO teacherDTO = initTeacherDTO();

            when(teacherService.getTeacherByFirstNameAndLastName(WALTER, WHITE)).thenReturn(teacherDTO);

            mockMvc.perform(get(TEACHERS + WALTER + "/" + WHITE)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(teacherDTO)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.firstName", equalTo(WALTER)))
                    .andExpect(jsonPath("$.lastName", equalTo(WHITE)));
        }

        @Test
        void getTeacherByID() throws Exception {
            TeacherDTO teacherDTO = initTeacherDTO();

            when(teacherService.getTeacherByID(ID)).thenReturn(teacherDTO);

            mockMvc.perform(get(TEACHERS + ID)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(teacherDTO)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.firstName", equalTo(WALTER)))
                    .andExpect(jsonPath("$.lastName", equalTo(WHITE)));
        }

        @Test
        void createNewTeacher() throws Exception {
            TeacherDTO teacherDTO = initTeacherDTO();

            when(teacherService.createNewTeacher(teacherDTO)).thenReturn(teacherDTO);

            mockMvc.perform(post(TEACHERS)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(asJsonString(teacherDTO)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.firstName", equalTo(WALTER)))
                    .andExpect(jsonPath("$.lastName", equalTo(WHITE)));
        }

        @Test
        void updateTeacher() throws Exception {
            TeacherDTO teacherDTO = initTeacherDTO();

            when(teacherService.updateTeacher(teacherDTO.getId(), teacherDTO)).thenReturn(teacherDTO);

            mockMvc.perform(put(TEACHERS + teacherDTO.getId())
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(teacherDTO)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.firstName", equalTo(WALTER)))
                    .andExpect(jsonPath("$.lastName", equalTo(WHITE)));
        }

        @Test
        void deleteTeacherById() throws Exception {
            mockMvc.perform(delete(TEACHERS + ID)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());

            verify(teacherService).deleteTeacherById(ID);
        }

        @Test
        public void testNotFoundException() throws Exception {

            when(teacherService.getTeacherByID(anyLong())).thenThrow(ResourceNotFoundException.class);

            mockMvc.perform(get(TEACHERS + 222)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
        }
}
