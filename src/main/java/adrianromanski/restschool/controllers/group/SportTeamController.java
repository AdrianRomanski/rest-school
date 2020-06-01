package adrianromanski.restschool.controllers.group;

import adrianromanski.restschool.domain.enums.Sport;
import adrianromanski.restschool.model.group.SportTeamDTO;
import adrianromanski.restschool.model.group.SportTeamListDTO;
import adrianromanski.restschool.services.group.sport_team.SportTeamService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api("Controller for Sport Team")
@RestController
@RequestMapping("/sport-teams/")
public class SportTeamController {

    private final SportTeamService sportTeamService;

    public SportTeamController(SportTeamService sportTeamService) {
        this.sportTeamService = sportTeamService;
    }

    @ApiOperation("Returns a SportTeamListDTO Object that contains all Sport Teams")
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public SportTeamListDTO getAllSportTeam() {
        return new SportTeamListDTO(sportTeamService.getAllSportTeam());
    }

    @ApiOperation("Returns an SportTeam Object based on ID or else throw ResourceNotFoundException")
    @GetMapping("{ID}")
    @ResponseStatus(HttpStatus.OK)
    public SportTeamDTO getSportTeamById(@PathVariable String ID) {
        return sportTeamService.getSportTeamById(Long.valueOf(ID));
    }

    @ApiOperation("Returns Sport Team with matching name or else throw ResourceNotFoundException")
    @GetMapping("name-{name}")
    @ResponseStatus(HttpStatus.OK)
    public SportTeamDTO getSportTeamByName(@PathVariable String name) {
        return sportTeamService.getSportTeamByName(name);
    }

    @ApiOperation("Returns List of SportTeams with matching president or else throw ResourceNotFoundException")
    @GetMapping("president-{president}")
    @ResponseStatus(HttpStatus.OK)
    public List<SportTeamDTO> getSportTeamByPresident(@PathVariable String president) {
        return sportTeamService.getSportTeamByPresident(president);
    }

    @ApiOperation("Returns Map where they key is Matching Sport and values Lists of Sport Teams grouped by President")
    @GetMapping("sport-{sport}")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, List<SportTeamDTO>> getTeamsForSport(@PathVariable String sport) {
        return sportTeamService.getTeamsForSport(Sport.valueOf(sport));
    }

    @ApiOperation("Returns Map where the keys are Sports and values Lists of Sport Teams grouped by President")
    @GetMapping("sports")
    @ResponseStatus(HttpStatus.OK)
    public Map<Sport, Map<String, List<SportTeamDTO>>> getTeamsGroupedBySport() {
        return sportTeamService.getTeamsGroupedBySport();
    }

    @ApiOperation("Returns Map where the keys are numbers of Students and values Lists of Sport Teams")
    @GetMapping("size")
    @ResponseStatus(HttpStatus.OK)
    public Map<Integer, List<SportTeamDTO>> getSportTeamsByStudentsSize() {
        return sportTeamService.getSportTeamsByStudentsSize();
    }
    
    @ApiOperation("Create and save new SportTeam based on StudentClassDTO body")
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public SportTeamDTO createNewSportTeam(@RequestBody SportTeamDTO sportTeamDTO) {
        return sportTeamService.createNewSportTeam(sportTeamDTO);
    }

    @ApiOperation("Update an existing SportTeam with matching ID or create a new one")
    @PutMapping("{ID}")
    @ResponseStatus(HttpStatus.OK)
    public SportTeamDTO updateSportTeam(@RequestBody SportTeamDTO sportTeamDTO, @PathVariable String ID) {
        return sportTeamService.updateSportTeam(sportTeamDTO, Long.valueOf(ID));
    }

    @ApiOperation("Delete a Student Class with matching ID")
    @DeleteMapping("{ID}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteSportTeamById(@PathVariable String ID) {
        sportTeamService.deleteSportTeamById(Long.valueOf(ID));
    }

}
