package adrianromanski.restschool.controllers.group;

import adrianromanski.restschool.model.base_entity.group.SportTeamDTO;
import adrianromanski.restschool.model.base_entity.group.SportTeamListDTO;
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
