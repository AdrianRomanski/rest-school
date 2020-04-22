package adrianromanski.restschool.services.group.sport_team;

import adrianromanski.restschool.model.base_entity.group.SportTeamDTO;

import java.util.List;

public interface SportTeamService {

    List<SportTeamDTO> getAllSportTeam();

    SportTeamDTO getSportTeamById(Long id);

    SportTeamDTO createNewSportTeam(SportTeamDTO sportTeamDTO);

    SportTeamDTO updateSportTeam(SportTeamDTO sportTeamDTO, Long id);

    void deleteSportTeamById(Long id);
}
