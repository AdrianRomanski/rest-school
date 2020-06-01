package adrianromanski.restschool.controllers;

import adrianromanski.restschool.controllers.exception_handler.RestResponseEntityExceptionHandler;
import adrianromanski.restschool.controllers.group.SportTeamController;
import adrianromanski.restschool.domain.enums.Sport;
import adrianromanski.restschool.exceptions.ResourceNotFoundException;
import adrianromanski.restschool.model.group.SportTeamDTO;
import adrianromanski.restschool.services.group.sport_team.SportTeamService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static adrianromanski.restschool.controllers.AbstractRestControllerTest.asJsonString;
import static adrianromanski.restschool.domain.enums.MaleName.*;
import static adrianromanski.restschool.domain.enums.Sport.*;
import static org.hamcrest.Matchers.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class SportTeamControllerTest {

    public static final String NAME = "Electric Hornets";
    public static final long ID = 1L;
    public static final String SPORT_TEAMS = "/sport-teams/";

    @Mock
    SportTeamService sportTeamService;

    @InjectMocks
    SportTeamController sportTeamController;

    MockMvc mockMvc;

    SportTeamDTO initSportTeamDTO() {
        SportTeamDTO sportTeamDTO = SportTeamDTO.builder().name(NAME).president(ISAAC.get()).build();
        sportTeamDTO.setId(ID);
        return sportTeamDTO;
    }

    private List<SportTeamDTO> getListOfTeams() {
        return Arrays.asList(initSportTeamDTO(), initSportTeamDTO());
    }

    private Map<String, List<SportTeamDTO>> getSportTeamMapSmall() {
        Map<String, List<SportTeamDTO>> map = new HashMap<>();
        map.put(ISAAC.get(), getListOfTeams());
        return map;
    }

    private Map<Sport, Map<String, List<SportTeamDTO>>> getSportTeamMapBig() {
        Map<Sport, Map<String, List<SportTeamDTO>>> map = new HashMap<>();
        Map<String, List<SportTeamDTO>> nestedMapFootball = new HashMap<>();
        Map<String, List<SportTeamDTO>> nestedMapBasketball = new HashMap<>();
        nestedMapFootball.put(ISAAC.get(), getListOfTeams());
        nestedMapBasketball.put(SEBASTIAN.get(), Arrays.asList(initSportTeamDTO(), initSportTeamDTO(), initSportTeamDTO()));
        map.put(FOOTBALL, nestedMapFootball);
        map.put(BASKETBALL, nestedMapBasketball);
        return map;
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(sportTeamController)
                                            .setControllerAdvice(RestResponseEntityExceptionHandler.class)
                                            .build();
    }


    @DisplayName("[GET], [Happy Path], [Method] = getAllSportTeam")
    @Test
    void getAllSportTeam() throws Exception {
        List<SportTeamDTO> sportTeamList = Arrays.asList(initSportTeamDTO(), initSportTeamDTO(), initSportTeamDTO());

        when(sportTeamService.getAllSportTeam()).thenReturn(sportTeamList);

        mockMvc.perform(get(SPORT_TEAMS)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(sportTeamList)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sportTeamDTOList", hasSize(3)));
    }


    @DisplayName("[GET], [Happy Path], [Method] = getSportTeamById")
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
                .andExpect(jsonPath("$.president", equalTo(ISAAC.get())));
    }


    @DisplayName("[GET], [Happy Path], [Method] = getSportTeamByName")
    @Test
    void getSportTeamByName() throws Exception {
        SportTeamDTO sportTeamDTO = initSportTeamDTO();

        when(sportTeamService.getSportTeamByName(anyString())).thenReturn(sportTeamDTO);

        mockMvc.perform(get(SPORT_TEAMS + "name-" + NAME)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(sportTeamDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(NAME)))
                .andExpect(jsonPath("$.president", equalTo(ISAAC.get())));
    }


    @DisplayName("[GET], [Happy Path], [Method] = getSportTeamByPresident")
    @Test
    void getSportTeamByPresident() throws Exception {
        List<SportTeamDTO> list = getListOfTeams();

        when(sportTeamService.getSportTeamByPresident(anyString())).thenReturn(list);

        mockMvc.perform(get(SPORT_TEAMS + "president-" + ISAAC.get())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(list)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }


    @DisplayName("[GET], [Happy Path], [Method] = getTeamsForSport")
    @Test
    void getTeamsForSport() throws Exception {
        Map<String, List<SportTeamDTO>> map = getSportTeamMapSmall();

        when(sportTeamService.getTeamsForSport(any(Sport.class))).thenReturn(map);

        mockMvc.perform(get(SPORT_TEAMS + "sport-" + FOOTBALL)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(map)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Isaac", hasSize(2)));
    }


    @DisplayName("[GET], [Happy Path], [Method] = getTeamsGroupedBySport")
    @Test
    void getTeamsGroupedBySport() throws Exception {
        Map<Sport, Map<String, List<SportTeamDTO>>> map = getSportTeamMapBig();

        when(sportTeamService.getTeamsGroupedBySport()).thenReturn(map);

        mockMvc.perform(get(SPORT_TEAMS + "sports")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(map)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.FOOTBALL.Isaac", hasSize(2)))
                .andExpect(jsonPath("$.BASKETBALL.Sebastian", hasSize(3)));
    }


    @DisplayName("[GET], [Happy Path], [Method] = getSportTeamsByStudentsSize")
    @Test
    void getSportTeamsByStudentsSize() throws Exception {
        Map<Integer, List<SportTeamDTO>> map = new HashMap<>();
        map.put(10, getListOfTeams()); // 2 teams with 10 students

        when(sportTeamService.getSportTeamsByStudentsSize()).thenReturn(map);

        mockMvc.perform(get(SPORT_TEAMS + "size")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(map)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.10", hasSize(2)));
    }


    @DisplayName("[POST], [Happy Path], [Method] = createNewSportTeam")
    @Test
    void createNewSportTeam() throws Exception {
        SportTeamDTO returnDTO = initSportTeamDTO();

        when(sportTeamService.createNewSportTeam(any(SportTeamDTO.class))).thenReturn(returnDTO);

        mockMvc.perform(post(SPORT_TEAMS)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(returnDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo(NAME)))
                .andExpect(jsonPath("$.president", equalTo(ISAAC.get())));
    }


    @DisplayName("[PUT], [Happy Path], [Method] = updateSportTeam")
    @Test
    void updateSportTeam() throws Exception {
        SportTeamDTO returnDTO = initSportTeamDTO();

        when(sportTeamService.updateSportTeam(any(SportTeamDTO.class), anyLong())).thenReturn(returnDTO);

        mockMvc.perform(put(SPORT_TEAMS + ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(returnDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(NAME)))
                .andExpect(jsonPath("$.president", equalTo(ISAAC.get())));
    }


    @DisplayName("[DELETE], [Happy Path], [Method] = deleteGuardianByID")
    @Test
    void deleteSportTeamById() throws Exception {

        mockMvc.perform(delete(SPORT_TEAMS + ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(sportTeamService, times(1)).deleteSportTeamById(ID);
    }


    @DisplayName("[GET, PUT, DELETE], [Unhappy Path]")
    @Test
    public void testNotFoundException() throws Exception {

        when(sportTeamService.getSportTeamById(anyLong())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(SPORT_TEAMS +"222")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}