package adrianromanski.restschool.services;

import adrianromanski.restschool.domain.enums.Sport;
import adrianromanski.restschool.domain.group.SportTeam;
import adrianromanski.restschool.domain.person.Student;
import adrianromanski.restschool.exceptions.ResourceNotFoundException;
import adrianromanski.restschool.mapper.group.SportTeamMapper;
import adrianromanski.restschool.model.group.SportTeamDTO;
import adrianromanski.restschool.repositories.group.SportTeamRepository;
import adrianromanski.restschool.services.group.sport_team.SportTeamService;
import adrianromanski.restschool.services.group.sport_team.SportTeamServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static adrianromanski.restschool.domain.enums.MaleName.*;
import static adrianromanski.restschool.domain.enums.Sport.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SportTeamServiceImplTest {

    public static final String NAME = "Electric Hornets";
    public static final long ID = 1L;

    @Mock
    SportTeamRepository sportTeamRepository;

    SportTeamService sportTeamService;

    public SportTeam createSportTeam(String name, String president, Sport sport, Long id) {
        SportTeam sportTeam = SportTeam.builder().name(name).president(president).sport(sport).build();
        sportTeam.setId(id);
        return sportTeam;
    }

    public SportTeam createFootball()  {
        return createSportTeam(NAME, ETHAN.get(), FOOTBALL, ID);
    }

    public SportTeam createBasketball()  {
        return createSportTeam("Shiny Asteroids", ISAAC.get(), BASKETBALL, 2L);
    }

    public SportTeamDTO createSportTeamDTO()  {
        SportTeamDTO sportTeamDTO = SportTeamDTO.builder().name(NAME).president(ETHAN.get()).build();
        sportTeamDTO.setId(ID);
        return sportTeamDTO;
    }

    public List<SportTeam> createSportList() {
        return Arrays.asList(createFootball(), createFootball(), createBasketball());
    }

    @BeforeEach
    void beforeEach() {
        MockitoAnnotations.initMocks(this);

        sportTeamService = new SportTeamServiceImpl(sportTeamRepository, SportTeamMapper.INSTANCE);
    }

    @DisplayName("[Happy Path], [Method] = getAllSportTeam")
    @Test
    void getAllSportTeam() {
        List<SportTeam> sportTeamList = createSportList();

        when(sportTeamRepository.findAll()).thenReturn(sportTeamList);

        List<SportTeamDTO> returnDTO = sportTeamService.getAllSportTeam();

        assertEquals(returnDTO.size(), 3);
    }

    @DisplayName("[Happy Path], [Method] = getSportTeamById")
    @Test
    void getSportTeamById() {
        SportTeam sportTeam = createFootball();

        when(sportTeamRepository.findById(anyLong())).thenReturn(Optional.of(sportTeam));

        SportTeamDTO returnDTO = sportTeamService.getSportTeamById(anyLong());

        assertEquals(returnDTO.getName(), NAME);
        assertEquals(returnDTO.getPresident(), ETHAN.get());
        assertEquals(returnDTO.getId(), ID);
    }

    @DisplayName("[Happy Path], [Method] = getSportTeamByName")
    @Test
    void getSportTeamByName() {
        SportTeam sportTeam = createFootball();

        when(sportTeamRepository.getSportTeamByName(anyString())).thenReturn(Optional.of(sportTeam));

        SportTeamDTO returnDTO = sportTeamService.getSportTeamByName(NAME);

        assertEquals(returnDTO.getName(), NAME);
        assertEquals(returnDTO.getPresident(), ETHAN.get());
        assertEquals(returnDTO.getId(), ID);
    }

    @DisplayName("[Happy Path], [Method] = getAllTeamsForSport")
    @Test
    void getAllTeamsForSport() {
        List<SportTeam> sportTeams = createSportList();

        when(sportTeamRepository.findAll()).thenReturn(sportTeams);

        Map<Sport, Map<String, List<SportTeamDTO>>> returnDTO = sportTeamService.getTeamsForSport(FOOTBALL);

        assertEquals(returnDTO.size(), 1);
        assertTrue(returnDTO.containsKey(FOOTBALL));
        assertEquals(returnDTO.get(FOOTBALL).get(ETHAN.get()).size(), 2); // Checking if the List contains 2 Football Teams
    }

    @DisplayName("[Happy Path], [Method] = getAllTeamsGroupedBySport")
    @Test
    void getTeamsGroupedBySport() {
        List<SportTeam> sportTeams = createSportList();

        when(sportTeamRepository.findAll()).thenReturn(sportTeams);

        Map<Sport, Map<String, List<SportTeamDTO>>> returnDTO = sportTeamService.getTeamsGroupedBySport();

        assertEquals(returnDTO.size(), 2); // Expecting size of 2 - FOOTBALL, BASKETBALL
        assertTrue(returnDTO.containsKey(FOOTBALL));
        assertTrue(returnDTO.containsKey(BASKETBALL));
        assertEquals(returnDTO.get(FOOTBALL).get(ETHAN.get()).size(), 2);
        assertEquals(returnDTO.get(BASKETBALL).get(ISAAC.get()).size(), 1);
    }

    @DisplayName("[Happy Path], [Method] = getSportTeamsByStudentsSize")
    @Test
    void getSportTeamsByStudentsSize() {
        List<SportTeam> sportTeams = createSportList();
        sportTeams.get(0).getStudents().addAll(Arrays.asList(new Student(), new Student()));

        when(sportTeamRepository.findAll()).thenReturn(sportTeams);

        Map<Integer, List<SportTeamDTO>> returnDTO = sportTeamService.getSportTeamsByStudentsSize();

        assertTrue(returnDTO.containsKey(2));
        assertEquals(returnDTO.get(2).size(), 1); // one team with 2 students
        assertTrue(returnDTO.containsKey(0));
        assertEquals(returnDTO.get(0).size(), 2); // two teams without any students
    }

    @DisplayName("[Happy Path], [Method] = getSportTeamByPresident")
    @Test
    void getSportTeamByPresident() {
        List<SportTeam> sportTeams = createSportList();

        when(sportTeamRepository.findAll()).thenReturn(sportTeams);

        List<SportTeamDTO> returnDTO = sportTeamService.getSportTeamByPresident(ETHAN.get());

        assertEquals(returnDTO.size(), 2);
        assertEquals(returnDTO.get(0).getName(), NAME);
        assertEquals(returnDTO.get(0).getPresident(), ETHAN.get());
    }


    @DisplayName("[Happy Path], [Method] = createNewSportTeam")
    @Test
    void createNewSportTeam() {
        SportTeam sportTeam = createFootball();
        SportTeamDTO sportTeamDTO = createSportTeamDTO();

        when(sportTeamRepository.save(any(SportTeam.class))).thenReturn(sportTeam);

        SportTeamDTO returnDTO = sportTeamService.createNewSportTeam(sportTeamDTO);

        assertEquals(returnDTO.getName(), NAME);
        assertEquals(returnDTO.getPresident(), ETHAN.get());
        assertEquals(returnDTO.getId(), ID);
    }

    @DisplayName("[Happy Path], [Method] = updateStudentClass")
    @Test
    void updateSportTeamHappyPath() {
        SportTeam sportTeam = createFootball();
        SportTeamDTO sportTeamDTO = createSportTeamDTO();

        when(sportTeamRepository.findById(anyLong())).thenReturn(Optional.of(sportTeam));
        when(sportTeamRepository.save(any(SportTeam.class))).thenReturn(sportTeam);

        SportTeamDTO returnDTO = sportTeamService.updateSportTeam(sportTeamDTO, ID);

        assertEquals(returnDTO.getName(), NAME);
        assertEquals(returnDTO.getPresident(), ETHAN.get());
        assertEquals(returnDTO.getId(), ID);
    }

    @DisplayName("[Unhappy Path], [Method] = updateSportTeam, [Reason] = SportTeam with id 222 not found")
    @Test
    void updateSportTeamUnHappyPath() {
        SportTeamDTO sportTeamDTO = createSportTeamDTO();

        Throwable ex = catchThrowable(() -> sportTeamService.updateSportTeam(sportTeamDTO, anyLong()));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }

    @DisplayName("[Happy Path], [Method] = deleteSportTeamById")
    @Test
    void deleteSportTeamById() {
       SportTeam sportTeam = createFootball();

       when(sportTeamRepository.findById(anyLong())).thenReturn(Optional.of(sportTeam));

       sportTeamService.deleteSportTeamById(anyLong());

       verify(sportTeamRepository, times(1)).deleteById(anyLong());
    }

    @DisplayName("[Unhappy Path], [Method] = deleteSportTeamById, [Reason] = SportTeam with id 222 not found")
    @Test
    void deleteStudentClassByIdUnHappyPath() {

        Throwable ex = catchThrowable(() -> sportTeamService.deleteSportTeamById(222L));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }
}