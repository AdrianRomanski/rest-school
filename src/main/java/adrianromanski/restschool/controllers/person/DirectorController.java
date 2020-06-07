package adrianromanski.restschool.controllers.person;

import adrianromanski.restschool.model.event.PaymentDTO;
import adrianromanski.restschool.model.person.DirectorDTO;
import adrianromanski.restschool.services.person.director.DirectorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Api("Controller for Director")
@RestController
@RequestMapping("/director/")
public class DirectorController {

    private final DirectorService directorService;

    public DirectorController(DirectorService directorService) {
        this.directorService = directorService;
    }

    @ApiOperation("Returns Director")
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    DirectorDTO getDirector() {
        return directorService.getDirector();
    }


    @ApiOperation("Add Payment To Teacher with Matching id")
    @PostMapping("addPayment/teacher-{ID}")
    @ResponseStatus(HttpStatus.CREATED)
    PaymentDTO addPaymentToTeacher(@PathVariable String ID, @RequestBody PaymentDTO paymentDTO) {
        return directorService.addPaymentToTeacher(Long.valueOf(ID), paymentDTO);
    }


    @ApiOperation("Updating Director basic informations")
    @PutMapping("update/director-{ID}")
    @ResponseStatus(HttpStatus.OK)
    DirectorDTO updateDirector(@PathVariable String ID, @RequestBody DirectorDTO directorDTO) {
        return directorService.updateDirector(Long.valueOf(ID), directorDTO);
    }


    @ApiOperation("Deletes Director with matching ID")
    @DeleteMapping("director-{ID}")
    @ResponseStatus(HttpStatus.OK)
    void deleteDirectorByID(@PathVariable String ID) {
        directorService.deleteDirectorByID(Long.valueOf(ID));
    }
}
