package adrianromanski.restschool.controllers;

import adrianromanski.restschool.controllers.exception_handler.RestResponseEntityExceptionHandler;
import adrianromanski.restschool.controllers.group.SportTeamController;
import adrianromanski.restschool.domain.base_entity.group.SportTeam;
import adrianromanski.restschool.exceptions.ResourceNotFoundException;
import adrianromanski.restschool.model.base_entity.group.SportTeamDTO;
import adrianromanski.restschool.services.group.sport_team.SportTeamService;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import static adrianromanski.restschool.controllers.AbstractRestControllerTest.asJsonString;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class SportTeamControllerTest {

    public static final String NAME = "Electric Hornets";
    public static final String PRESIDENT = "Philip";
    public static final long ID = 1L;
    public static final String SPORT_TEAMS = "/sport-teams/";

    @Mock
    SportTeamService sportTeamService;

    @InjectMocks
    SportTeamController sportTeamController;

    MockMvc mockMvc;

    SportTeamDTO initSportTeamDTO() {
        SportTeamDTO sportTeamDTO = new SportTeamDTO();
        sportTeamDTO.setName(NAME);
        sportTeamDTO.setPresident(PRESIDENT);
        sportTeamDTO.setId(ID);
        return sportTeamDTO;
    }
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(sportTeamController)
                                            .setControllerAdvice(RestResponseEntityExceptionHandler.class)
                                            .build();
    }

    @Test
    void getAllSportTeam() throws Exception {
        List<SportTeamDTO> sportTeamList = Arrays.asList(new SportTeamDTO(), new SportTeamDTO(), new SportTeamDTO());

        when(sportTeamService.getAllSportTeam()).thenReturn(sportTeamList);

        List<SportTeamDTO> returnDTO = sportTeamService.getAllSportTeam();

        mockMvc.perform(get(SPORT_TEAMS)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(sportTeamList)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sportTeamDTOList", hasSize(3)));
    }

    @Test
    void getSportTeamById() throws Exception {
        SportTeamDTO sportTeamDTO = initSportTeamDTO();

        when(sportTeamService.getSportTeamById(ID)).thenReturn(sportTeamDTO);

        mockMvc.perform(get(SPORT_TEAMS + ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(sportTeamDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(NAME)))
                .andExpect(jsonPath("$.president", equalTo(PRESIDENT)));
    }

    @Test
    void createNewSportTeam() throws Exception {
        SportTeamDTO sportTeamDTO = initSportTeamDTO();

        SportTeamDTO returnDTO = initSportTeamDTO();

        when(sportTeamService.createNewSportTeam(sportTeamDTO)).thenReturn(returnDTO);

        mockMvc.perform(post(SPORT_TEAMS)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(sportTeamDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo(NAME)))
                .andExpect(jsonPath("$.president", equalTo(PRESIDENT)));
    }

    @Test
    void updateSportTeam() throws Exception {
        SportTeamDTO sportTeamDTO = initSportTeamDTO();

        SportTeamDTO returnDTO = initSportTeamDTO();

        when(sportTeamService.updateSportTeam(sportTeamDTO, ID)).thenReturn(returnDTO);

        mockMvc.perform(put(SPORT_TEAMS + ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(sportTeamDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(NAME)))
                .andExpect(jsonPath("$.president", equalTo(PRESIDENT)));
    }

    @Test
    void deleteSportTeamById() throws Exception {
        mockMvc.perform(delete(SPORT_TEAMS + ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(sportTeamService, times(1)).deleteSportTeamById(ID);

    }

    @Test
    public void testNotFoundException() throws Exception {

        when(sportTeamService.getSportTeamById(anyLong())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(SPORT_TEAMS +"222")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}