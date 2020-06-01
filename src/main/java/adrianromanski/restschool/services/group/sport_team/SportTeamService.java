package adrianromanski.restschool.services.group.sport_team;

import adrianromanski.restschool.domain.enums.Sport;
import adrianromanski.restschool.model.group.SportTeamDTO;

import java.util.List;
import java.util.Map;

public interface SportTeamService {

    // GET
    List<SportTeamDTO> getAllSportTeam();

    SportTeamDTO getSportTeamById(Long id);

    SportTeamDTO getSportTeamByName(String name);

    List<SportTeamDTO> getSportTeamByPresident(String president);

    Map<Integer, List<SportTeamDTO>> getSportTeamsByStudentsSize();

    Map<String, List<SportTeamDTO>> getTeamsForSport(Sport sport);

    Map<Sport, Map<String, List<SportTeamDTO>>> getTeamsGroupedBySport();

    // POST
    SportTeamDTO createNewSportTeam(SportTeamDTO sportTeamDTO);

    // UPDATE
    SportTeamDTO updateSportTeam(SportTeamDTO sportTeamDTO, Long id);

    // DELETE
    void deleteSportTeamById(Long id);
}
