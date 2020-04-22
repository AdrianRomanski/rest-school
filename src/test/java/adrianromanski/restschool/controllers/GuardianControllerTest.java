package adrianromanski.restschool.controllers;

import adrianromanski.restschool.controllers.exception_handler.RestResponseEntityExceptionHandler;
import adrianromanski.restschool.controllers.person.GuardianController;
import adrianromanski.restschool.domain.base_entity.person.Guardian;
import adrianromanski.restschool.exceptions.ResourceNotFoundException;
import adrianromanski.restschool.model.base_entity.person.GuardianDTO;
import adrianromanski.restschool.services.person.guardian.GuardianService;
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
import static org.hamcrest.Matchers.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class GuardianControllerTest {

    public static final String FIRST_NAME = "Bruce";
    public static final String LAST_NAME = "Wayne";
    public static final String EMAIL = "WayneEnterprise@Gotham.com";
    public static final String NUMBER = "543-352-332";
    public static final long ID = 1L;
    public static final String GUARDIANS = "/guardians/";
    MockMvc mockMvc;

    @Mock
    GuardianService guardianService;

    @InjectMocks
    GuardianController guardianController;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(guardianController)
                                        .setControllerAdvice(RestResponseEntityExceptionHandler.class)
                                        .build();
    }


    GuardianDTO initGuardianDTO() {
        GuardianDTO guardianDTO = new GuardianDTO();
        guardianDTO.setFirstName(FIRST_NAME);
        guardianDTO.setLastName(LAST_NAME);
        guardianDTO.setEmail(EMAIL);
        guardianDTO.setTelephoneNumber(NUMBER);
        guardianDTO.setId(ID);
        return guardianDTO;
    }

    Guardian initGuardian() {
        Guardian guardian = new Guardian();
        guardian.setFirstName(FIRST_NAME);
        guardian.setLastName(LAST_NAME);
        guardian.setEmail(EMAIL);
        guardian.setTelephoneNumber(NUMBER);
        guardian.setId(ID);
        return guardian;
    }

    @Test
    void getAllGuardians() throws Exception {
        List<GuardianDTO> guardianDTOList = Arrays.asList(new GuardianDTO(), new GuardianDTO(), new GuardianDTO());

        when(guardianService.getAllGuardians()).thenReturn(guardianDTOList);

        mockMvc.perform(get(GUARDIANS)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(guardianDTOList)))
                .andExpect(jsonPath("$.guardianDTOList", hasSize(3)));
    }

    @Test
    void getGuardianByID() throws Exception {
        GuardianDTO guardianDTO = initGuardianDTO();

        when(guardianService.getGuardianByID(ID)).thenReturn(guardianDTO);

        mockMvc.perform(get(GUARDIANS+ "id-" + ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(guardianDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo(FIRST_NAME)))
                .andExpect(jsonPath("$.lastName", equalTo(LAST_NAME)))
                .andExpect(jsonPath("$.email", equalTo(EMAIL)))
                .andExpect(jsonPath("$.telephoneNumber", equalTo(NUMBER)));
    }

    @Test
    void getGuardianByFirstAndLastName() throws Exception {
        GuardianDTO guardianDTO = initGuardianDTO();

        when(guardianService.getGuardianByFirstAndLastName(FIRST_NAME, LAST_NAME)).thenReturn(guardianDTO);

        mockMvc.perform(get(GUARDIANS + FIRST_NAME + "_" + LAST_NAME)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(guardianDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo(FIRST_NAME)))
                .andExpect(jsonPath("$.lastName", equalTo(LAST_NAME)))
                .andExpect(jsonPath("$.email", equalTo(EMAIL)))
                .andExpect(jsonPath("$.telephoneNumber", equalTo(NUMBER)));
    }

    @Test
    void createNewGuardian() throws Exception {
        GuardianDTO guardianDTO = initGuardianDTO();

        when(guardianService.createNewGuardian(guardianDTO)).thenReturn(guardianDTO);

        mockMvc.perform(post(GUARDIANS)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(guardianDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", equalTo(FIRST_NAME)))
                .andExpect(jsonPath("$.lastName", equalTo(LAST_NAME)))
                .andExpect(jsonPath("$.email", equalTo(EMAIL)))
                .andExpect(jsonPath("$.telephoneNumber", equalTo(NUMBER)));
    }

    @Test
    void updateGuardian() throws Exception {

        GuardianDTO guardianDTO = initGuardianDTO();
        guardianDTO.setEmail("HotHotEmail@BruceWayne.com");

        when(guardianService.updateGuardian(guardianDTO, 1L)).thenReturn(guardianDTO);

        mockMvc.perform(put("/guardians/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(guardianDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo(FIRST_NAME)))
                .andExpect(jsonPath("$.lastName", equalTo(LAST_NAME)))
                .andExpect(jsonPath("$.email", equalTo("HotHotEmail@BruceWayne.com")))
                .andExpect(jsonPath("$.telephoneNumber", equalTo(NUMBER)));
    }

    @Test
    void deleteGuardian() throws Exception {
        mockMvc.perform(delete(GUARDIANS + ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(guardianService).deleteGuardianByID(ID);
    }


    @Test
    public void testNotFoundException() throws Exception {

        when(guardianService.getGuardianByID(anyLong())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(GUARDIANS + "id-222")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}