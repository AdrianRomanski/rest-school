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

    @ApiOperation("Returns a GuardianListDTO Object that contains all Payments")
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    GuardianListDTO getAllLegalGuardians() {
        return new GuardianListDTO(guardianService.getAllGuardians());
    }

    @ApiOperation("Returns an Guardian Object based on ID or else throw ResourceNotFoundException")
    @GetMapping("id-{ID}")
    @ResponseStatus(HttpStatus.OK)
    GuardianDTO getGuardianByID(@PathVariable String ID) {
        return guardianService.getGuardianByID(Long.valueOf(ID));
    }

    @ApiOperation("Returns an Guardian Object based on Strings or else throw ResourceNotFoundException")
    @GetMapping("{firstName}_{lastName}")
    @ResponseStatus(HttpStatus.OK)
    GuardianDTO getGuardianByFirstAndLastName(@PathVariable String firstName, @PathVariable String lastName) {
        return guardianService.getGuardianByFirstAndLastName(firstName, lastName);
    }

    @ApiOperation("Returns an Guardian Object based on ID or else throw ResourceNotFoundException")
    @GetMapping("/age")
    @ResponseStatus(HttpStatus.OK)
    Map<Long, List<GuardianDTO>> getGuardiansByAge() {
        return guardianService.getGuardiansByAge();
    }

    @ApiOperation("Returns an Guardian Object based on ID or else throw ResourceNotFoundException")
    @GetMapping("id-{ID}/getStudents")
    @ResponseStatus(HttpStatus.OK)
    StudentListDTO getAllStudentsForGuardian(@PathVariable String ID) {
        return new StudentListDTO(guardianService.getAllStudentsForGuardian(Long.valueOf(ID)));
    }

    @ApiOperation("Create and save new Guardian based on GuardianDTO body")
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    GuardianDTO createNewGuardian(@RequestBody GuardianDTO guardianDTO) {
        return guardianService.createNewGuardian(guardianDTO);
    }

    @ApiOperation("Update an existing Guardian with matching ID or create a new one")
    @PutMapping("{ID}")
    @ResponseStatus(HttpStatus.OK)
    GuardianDTO updateGuardian(@RequestBody GuardianDTO guardianDTO, @PathVariable String ID) {
        return guardianService.updateGuardian(guardianDTO, Long.valueOf(ID));
    }

    @ApiOperation("Delete an Guardian based on ID")
    @DeleteMapping("{ID}")
    @ResponseStatus(HttpStatus.OK)
    void deleteGuardian(@PathVariable String ID) {
        guardianService.deleteGuardianByID(Long.valueOf(ID));
    }

}
