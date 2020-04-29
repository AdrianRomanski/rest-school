package adrianromanski.restschool.services;

import adrianromanski.restschool.domain.base_entity.group.SportTeam;
import adrianromanski.restschool.exceptions.ResourceNotFoundException;
import adrianromanski.restschool.mapper.group.SportTeamMapper;
import adrianromanski.restschool.model.base_entity.group.SportTeamDTO;
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
import java.util.Optional;

import static adrianromanski.restschool.domain.base_entity.enums.MaleName.*;
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

    public SportTeam createSportTeam()  {
        SportTeam sportTeam = SportTeam.builder().name(NAME).president(ETHAN.get()).build();
        sportTeam.setId(ID);
        return sportTeam;
    }

    public SportTeamDTO createSportTeamDTO()  {
        SportTeamDTO sportTeamDTO = SportTeamDTO.builder().name(NAME).president(ETHAN.get()).build();
        sportTeamDTO.setId(ID);
        return sportTeamDTO;
    }

    @BeforeEach
    void beforeEach() {
        MockitoAnnotations.initMocks(this);

        sportTeamService = new SportTeamServiceImpl(sportTeamRepository, SportTeamMapper.INSTANCE);
    }

    @DisplayName("[Happy Path], [Method] = getAllSportTeam, [Expected] = List containing 3 Sport Teams")
    @Test
    void getAllSportTeam() {
        List<SportTeam> sportTeamList = Arrays.asList(createSportTeam(), createSportTeam(), createSportTeam());

        when(sportTeamRepository.findAll()).thenReturn(sportTeamList);

        List<SportTeamDTO> returnDTO = sportTeamService.getAllSportTeam();

        assertEquals(returnDTO.size(), 3);
    }

    @DisplayName("[Happy Path], [Method] = getSportTeamById, [Expected] = SportTeamDTO with matching fields")
    @Test
    void getSportTeamById() {
        SportTeam sportTeam = createSportTeam();

        when(sportTeamRepository.findById(anyLong())).thenReturn(Optional.of(sportTeam));

        SportTeamDTO returnDTO = sportTeamService.getSportTeamById(anyLong());

        assertEquals(returnDTO.getName(), NAME);
        assertEquals(returnDTO.getPresident(), ETHAN.get());
        assertEquals(returnDTO.getId(), ID);
    }

    @DisplayName("[Happy Path], [Method] = createNewSportTeam, [Expected] = sportTeamDTO with matching fields")
    @Test
    void createNewSportTeam() {
        SportTeam sportTeam = createSportTeam();
        SportTeamDTO sportTeamDTO = createSportTeamDTO();

        when(sportTeamRepository.save(any(SportTeam.class))).thenReturn(sportTeam);

        SportTeamDTO returnDTO = sportTeamService.createNewSportTeam(sportTeamDTO);

        assertEquals(returnDTO.getName(), NAME);
        assertEquals(returnDTO.getPresident(), ETHAN.get());
        assertEquals(returnDTO.getId(), ID);
    }

    @DisplayName("[Happy Path], [Method] = updateStudentClass, [Expected] = SportTeamDTO with updated fields")
    @Test
    void updateSportTeamHappyPath() {
        SportTeam sportTeam = createSportTeam();
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

    @DisplayName("[Happy Path], [Method] = deleteSportTeamById, [Expected] = sportTeamRepository deleting")
    @Test
    void deleteSportTeamById() {
       SportTeam sportTeam = createSportTeam();

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