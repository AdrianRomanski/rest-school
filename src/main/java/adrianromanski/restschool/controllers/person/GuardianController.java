package adrianromanski.restschool.controllers.person;

import adrianromanski.restschool.model.base_entity.address.GuardianAddressDTO;
import adrianromanski.restschool.model.base_entity.contact.GuardianContactDTO;
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


    @ApiOperation("Add address to Guardian")
    @PostMapping("addAddress/guardian-{ID}")
    @ResponseStatus(HttpStatus.CREATED)
    GuardianAddressDTO addAddress(@PathVariable String ID, @RequestBody GuardianAddressDTO addressDTO) {
        return guardianService.addAddress(Long.valueOf(ID), addressDTO);
    }


    @ApiOperation("Add contact to Guardian")
    @PostMapping("addContact/guardian-{ID}")
    @ResponseStatus(HttpStatus.CREATED)
    GuardianContactDTO addContact(@PathVariable String ID, @RequestBody GuardianContactDTO contactDTO) {
        return guardianService.addContact(Long.valueOf(ID), contactDTO);
    }


    @ApiOperation("Update an existing Guardian with matching ID and save it")
    @PutMapping("updateGuardian/guardian-{ID}")
    @ResponseStatus(HttpStatus.OK)
    GuardianDTO updateGuardian(@PathVariable String ID, @RequestBody GuardianDTO guardianDTO) {
        return guardianService.updateGuardian(guardianDTO, Long.valueOf(ID));
    }


    @ApiOperation("Update Address of Guardian with matching ID and save it")
    @PutMapping("updateAddress/guardian-{ID}")
    @ResponseStatus(HttpStatus.OK)
    GuardianAddressDTO updateAddress(@PathVariable String ID, @RequestBody GuardianAddressDTO addressDTO) {
        return guardianService.updateAddress(Long.valueOf(ID), addressDTO);
    }


    @ApiOperation("Update Contact of Guardian with matching ID and save it")
    @PutMapping("updateContact/guardian-{ID}")
    @ResponseStatus(HttpStatus.OK)
    GuardianContactDTO updateContact(@PathVariable String ID, @RequestBody GuardianContactDTO contactDTO)  {
        return guardianService.updateContact(Long.valueOf(ID), contactDTO);
    }


    @ApiOperation("Delete Guardian with matching ID")
    @DeleteMapping("deleteGuardian/guardian-{ID}")
    @ResponseStatus(HttpStatus.OK)
    void deleteGuardian(@PathVariable String ID) {
        guardianService.deleteGuardianByID(Long.valueOf(ID));
    }
}