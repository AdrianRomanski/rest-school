package adrianromanski.restschool.controllers.person;

import adrianromanski.restschool.model.person.GuardianDTO;
import adrianromanski.restschool.model.person.GuardianListDTO;
import adrianromanski.restschool.model.person.StudentListDTO;
import adrianromanski.restschool.services.person.guardian.GuardianService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api("Controller for Guardians")
@RestController
@RequestMapping("/guardians/")
public class GuardianController {

    private final GuardianService guardianService;

    public GuardianController(GuardianService legalGuardianService) {
        this.guardianService = legalGuardianService;
    }


    @ApiOperation("Returns an Guardian with matching ID")
    @GetMapping("getById/guardian-{ID}")
    @ResponseStatus(HttpStatus.OK)
    GuardianDTO getGuardianByID(@PathVariable String ID) {
        return guardianService.getGuardianByID(Long.valueOf(ID));
    }

    @ApiOperation("Returns an Guardian with matching firsName and lastName")
    @GetMapping("getByName/{firstName}-{lastName}")
    @ResponseStatus(HttpStatus.OK)
    GuardianDTO getGuardianByFirstAndLastName(@PathVariable String firstName, @PathVariable String lastName) {
        return guardianService.getGuardianByFirstAndLastName(firstName, lastName);
    }

    @ApiOperation("Returns all Guardians")
    @GetMapping({"/list", "/", "", "getAll", "findAll"})
    @ResponseStatus(HttpStatus.OK)
    GuardianListDTO getAllGuardians() {
        return new GuardianListDTO(guardianService.getAllGuardians());
    }

    @ApiOperation("Returns Guardians grouped by Age")
    @GetMapping("groupedBy/age")
    @ResponseStatus(HttpStatus.OK)
    Map<Long, List<GuardianDTO>> getGuardiansByAge() {
        return guardianService.getGuardiansByAge();
    }

    @ApiOperation("Returns all Students for Guardian")
    @GetMapping("getStudents/guardian-{ID}")
    @ResponseStatus(HttpStatus.OK)
    StudentListDTO getAllStudentsForGuardian(@PathVariable String ID) {
        return new StudentListDTO(guardianService.getAllStudentsForGuardian(Long.valueOf(ID)));
    }

    @ApiOperation("Create and save new Guardian")
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    GuardianDTO createNewGuardian(@RequestBody GuardianDTO guardianDTO) {
        return guardianService.createNewGuardian(guardianDTO);
    }

    @ApiOperation("Update an existing Guardian with matching ID and save it")
    @PutMapping("updateGuardian/guardian-{ID}")
    @ResponseStatus(HttpStatus.OK)
    GuardianDTO updateGuardian(@RequestBody GuardianDTO guardianDTO, @PathVariable String ID) {
        return guardianService.updateGuardian(guardianDTO, Long.valueOf(ID));
    }

    @ApiOperation("Delete Guardian with matching ID")
    @DeleteMapping("deleteGuardian/guardian-{ID}")
    @ResponseStatus(HttpStatus.OK)
    void deleteGuardian(@PathVariable String ID) {
        guardianService.deleteGuardianByID(Long.valueOf(ID));
    }

}
