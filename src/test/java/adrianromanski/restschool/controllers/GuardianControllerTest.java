package adrianromanski.restschool.controllers;

import adrianromanski.restschool.controllers.exception_handler.RestResponseEntityExceptionHandler;
import adrianromanski.restschool.controllers.person.GuardianController;
import adrianromanski.restschool.exceptions.ResourceNotFoundException;
import adrianromanski.restschool.model.person.GuardianDTO;
import adrianromanski.restschool.model.person.StudentDTO;
import adrianromanski.restschool.services.person.guardian.GuardianService;
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
import static adrianromanski.restschool.domain.enums.LastName.COOPER;
import static adrianromanski.restschool.domain.enums.LastName.HENDERSON;
import static adrianromanski.restschool.domain.enums.MaleName.ETHAN;
import static org.hamcrest.Matchers.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class GuardianControllerTest {


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

    GuardianDTO createEthan() {
        return GuardianDTO.builder().firstName(ETHAN.get()).lastName(HENDERSON.get()).build();
    }


    @DisplayName("[GET], [Happy Path], [Method] = getAllGuardians, [Expected] = List containing 3 Guardians")
    @Test
    void getAllGuardians() throws Exception {
        List<GuardianDTO> guardianDTOList = Arrays.asList(createEthan(), createEthan(), createEthan());

        when(guardianService.getAllGuardians()).thenReturn(guardianDTOList);

        mockMvc.perform(get(GUARDIANS)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(guardianDTOList)))
                .andExpect(jsonPath("$.guardianDTOList", hasSize(3)));
    }

    @DisplayName("[GET], [Happy Path], [Method] = getGuardianByID, [Expected] = GuardianDTO with matching fields")
    @Test
    void getGuardianByID() throws Exception {
        GuardianDTO guardianDTO = createEthan();

        when(guardianService.getGuardianByID(ID)).thenReturn(guardianDTO);

        mockMvc.perform(get(GUARDIANS+ "id-" + ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(guardianDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo(ETHAN.get())))
                .andExpect(jsonPath("$.lastName", equalTo(HENDERSON.get())));
    }

    @DisplayName("[GET], [Happy Path], [Method] = getGuardiansByAge, [Expected] = Map with 3 Keys (27,24,22)")
    @Test
    void getGuardiansByAge() throws Exception {
        Map<Long, List<GuardianDTO>> map = new HashMap<>();

        map.putIfAbsent(27L, Arrays.asList(createEthan(), createEthan()));
        map.putIfAbsent(24L, Collections.singletonList(createEthan()));
        map.putIfAbsent(22L, Arrays.asList(createEthan(), createEthan(), createEthan()));

        when(guardianService.getGuardiansByAge()).thenReturn(map);

        mockMvc.perform(get(GUARDIANS + "age")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(map)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.27", hasSize(2)))
                .andExpect(jsonPath("$.24", hasSize(1)))
                .andExpect(jsonPath("$.22", hasSize(3)));
    }

    @DisplayName("[GET], [Happy Path], [Method] = getAllStudentsForGuardian, [Expected] = List of size 2")
    @Test
    void getAllStudentsForGuardian() throws Exception {
        List<StudentDTO> studentDTOS = Arrays.asList(new StudentDTO(), new StudentDTO());

        when(guardianService.getAllStudentsForGuardian(anyLong())).thenReturn(studentDTOS);

        mockMvc.perform(get(GUARDIANS + "id-1/getStudents")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(studentDTOS)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.students", hasSize(2)));
    }

    @DisplayName("[GET], [Happy Path], [Method] = getGuardianByFirstAndLastName, [Expected] = GuardianDTO with matching fields")
    @Test
    void getGuardianByFirstAndLastName() throws Exception {
        GuardianDTO guardianDTO = createEthan();

        when(guardianService.getGuardianByFirstAndLastName(anyString(), anyString())).thenReturn(guardianDTO);

        mockMvc.perform(get(GUARDIANS + ETHAN.get() + "_" + COOPER.get())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(guardianDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo(ETHAN.get())))
                .andExpect(jsonPath("$.lastName", equalTo(HENDERSON.get())));
    }

    @DisplayName("[POST], [Happy Path], [Method] = createNewGuardian, [Expected] = GuardianDTO with matching fields")
    @Test
    void createNewGuardian() throws Exception {
        GuardianDTO guardianDTO = createEthan();

        when(guardianService.createNewGuardian(guardianDTO)).thenReturn(guardianDTO);

        mockMvc.perform(post(GUARDIANS)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(guardianDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", equalTo(ETHAN.get())))
                .andExpect(jsonPath("$.lastName", equalTo(HENDERSON.get())));
    }

    @DisplayName("[PUT], [Happy Path], [Method] = updateGuardian, [Expected] = GuardianDTO with updated fields")
    @Test
    void updateGuardian() throws Exception {

        GuardianDTO guardianDTO = createEthan();


        when(guardianService.updateGuardian(any(GuardianDTO.class), anyLong())).thenReturn(guardianDTO);

        mockMvc.perform(put("/guardians/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(guardianDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo(ETHAN.get())))
                .andExpect(jsonPath("$.lastName", equalTo(HENDERSON.get())));
    }

    @DisplayName("[DELETE], [Happy Path], [Method] = deleteGuardianByID, [Expected] = guardianService deleting student")
    @Test
    void deleteGuardian() throws Exception {
        mockMvc.perform(delete(GUARDIANS + ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(guardianService).deleteGuardianByID(ID);
    }

    @DisplayName("[GET, PUT, DELETE], [Unhappy Path], [Reason] = Guardian with id 222 not found")
    @Test
    public void testNotFoundException() throws Exception {

        when(guardianService.getGuardianByID(anyLong())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(GUARDIANS + "id-222")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}