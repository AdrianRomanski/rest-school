package adrianromanski.restschool.services.group.sport_team;

import adrianromanski.restschool.domain.base_entity.enums.Sport;
import adrianromanski.restschool.domain.base_entity.group.SportTeam;
import adrianromanski.restschool.model.base_entity.group.SportTeamDTO;

import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;

public interface SportTeamService {

    List<SportTeamDTO> getAllSportTeam();

    SportTeamDTO getSportTeamById(Long id);

    SportTeamDTO getSportTeamByName(String name);

    Map<Sport, Map<String, List<SportTeamDTO>>> getTeamsForSport(Sport sport);

    Map<Sport, Map<String, List<SportTeamDTO>>> getTeamsGroupedBySport();

    Map<Integer, List<SportTeamDTO>> getSportTeamsByStudentsSize();

    List<SportTeamDTO> getSportTeamByPresident(String president);

    SportTeamDTO createNewSportTeam(SportTeamDTO sportTeamDTO);

    SportTeamDTO updateSportTeam(SportTeamDTO sportTeamDTO, Long id);

    void deleteSportTeamById(Long id);
}
