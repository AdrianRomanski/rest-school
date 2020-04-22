package adrianromanski.restschool.services;

import adrianromanski.restschool.domain.base_entity.group.SportTeam;
import adrianromanski.restschool.mapper.group.SportTeamMapper;
import adrianromanski.restschool.model.base_entity.group.SportTeamDTO;
import adrianromanski.restschool.repositories.group.SportTeamRepository;
import adrianromanski.restschool.services.group.sport_team.SportTeamService;
import adrianromanski.restschool.services.group.sport_team.SportTeamServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SportTeamServiceImplTest {


    public static final String NAME = "Electric Hornets";
    public static final String PRESIDENT = "Philip";
    public static final long ID = 1L;

    @Mock
    SportTeamRepository sportTeamRepository;

    SportTeamService sportTeamService;

    public SportTeam initSportTeam()  {
        SportTeam sportTeam = new SportTeam();
        sportTeam.setPresident(PRESIDENT);
        sportTeam.setName(NAME);
        sportTeam.setId(ID);
        return sportTeam;
    }

    public SportTeamDTO initSportTeamDTO()  {
        SportTeamDTO sportTeamDTO = new SportTeamDTO();
        sportTeamDTO.setPresident(PRESIDENT);
        sportTeamDTO.setName(NAME);
        sportTeamDTO.setId(ID);
        return sportTeamDTO;
    }

    @BeforeEach
    void beforeEach() {
        MockitoAnnotations.initMocks(this);

        sportTeamService = new SportTeamServiceImpl(sportTeamRepository, SportTeamMapper.INSTANCE);
    }

    @Test
    void getAllSportTeam() {
        List<SportTeam> sportTeamList = Arrays.asList(new SportTeam(), new SportTeam(), new SportTeam());

        when(sportTeamRepository.findAll()).thenReturn(sportTeamList);

        List<SportTeamDTO> returnDTO = sportTeamService.getAllSportTeam();

        assertEquals(returnDTO.size(), sportTeamList.size());
    }

    @Test
    void getSportTeamById() {
        SportTeam sportTeam = initSportTeam();

        when(sportTeamRepository.findById(ID)).thenReturn(Optional.of(sportTeam));

        SportTeamDTO returnDTO = sportTeamService.getSportTeamById(ID);

        assertEquals(returnDTO.getName(), NAME);
        assertEquals(returnDTO.getPresident(), PRESIDENT);
        assertEquals(returnDTO.getId(), ID);
    }

    @Test
    void createNewSportTeam() {
        SportTeam sportTeam = initSportTeam();

        SportTeamDTO sportTeamDTO = initSportTeamDTO();

        when(sportTeamRepository.save(any(SportTeam.class))).thenReturn(sportTeam);

        SportTeamDTO returnDTO = sportTeamService.createNewSportTeam(sportTeamDTO);

        assertEquals(returnDTO.getName(), NAME);
        assertEquals(returnDTO.getPresident(), PRESIDENT);
        assertEquals(returnDTO.getId(), ID);
    }

    @Test
    void updateSportTeam() {
        SportTeam sportTeam = initSportTeam();

        SportTeamDTO sportTeamDTO = initSportTeamDTO();

        when(sportTeamRepository.save(any(SportTeam.class))).thenReturn(sportTeam);

        SportTeamDTO returnDTO = sportTeamService.updateSportTeam(sportTeamDTO, ID);

        assertEquals(returnDTO.getName(), NAME);
        assertEquals(returnDTO.getPresident(), PRESIDENT);
        assertEquals(returnDTO.getId(), ID);
    }

    @Test
    void deleteSportTeamById() {
        sportTeamRepository.deleteById(ID);
        verify(sportTeamRepository, times(1)).deleteById(ID);
    }
}