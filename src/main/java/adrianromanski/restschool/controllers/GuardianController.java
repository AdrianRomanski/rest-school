package adrianromanski.restschool.controllers;

import adrianromanski.restschool.model.base_entity.person.GuardianDTO;
import adrianromanski.restschool.model.base_entity.person.GuardianListDTO;
import adrianromanski.restschool.services.guardian.GuardianService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation("Returns an Payment Object based on ID or else throw ResourceNotFoundException")
    @GetMapping("id-{ID}")
    @ResponseStatus(HttpStatus.OK)
    GuardianDTO getGuardianByID(@PathVariable String ID) {
        return guardianService.getGuardianByID(Long.valueOf(ID));
    }

    @ApiOperation("Returns an Payment Object based on Strings or else throw ResourceNotFoundException")
    @GetMapping("{firstName}_{lastName}")
    @ResponseStatus(HttpStatus.OK)
    GuardianDTO getGuardianByFirstAndLastName(@PathVariable String firstName, @PathVariable String lastName) {
        return guardianService.getGuardianByFirstAndLastName(firstName, lastName);
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
