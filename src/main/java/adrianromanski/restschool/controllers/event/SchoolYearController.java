package adrianromanski.restschool.controllers.event;

import adrianromanski.restschool.model.event.SchoolYearDTO;
import adrianromanski.restschool.services.event.school_year.SchoolYearService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@Api("Controller for School Year")
@RequestMapping("/schoolYears/")
public class SchoolYearController {

    private final SchoolYearService schoolYearService;

    public SchoolYearController(SchoolYearService schoolYearService) {
        this.schoolYearService = schoolYearService;
    }

    @ApiOperation("Returns School Year with matching ID")
    @GetMapping("ID-{ID}")
    @ResponseStatus(HttpStatus.OK)
    public SchoolYearDTO getSchoolYearByID(@PathVariable String ID) {
        return schoolYearService.getSchoolYearByID(Long.valueOf(ID));
    }


    @ApiOperation("Creates new SchoolYear")
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public SchoolYearDTO createSchoolYear(@RequestBody SchoolYearDTO schoolYearDTO) {
        return schoolYearService.createSchoolYear(schoolYearDTO);
    }


    @ApiOperation("Updates existing SchoolYear with matching ID")
    @PutMapping("ID-{ID}")
    @ResponseStatus(HttpStatus.OK)
    public SchoolYearDTO updateSchoolYear(@PathVariable String ID, @RequestBody SchoolYearDTO schoolYearDTO) {
        return schoolYearService.updateSchoolYear(Long.valueOf(ID), schoolYearDTO);
    }


    @ApiOperation("Deletes School Year with matching ID")
    @DeleteMapping("ID-{ID}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteSchoolYear(@PathVariable String ID) {
        schoolYearService.deleteSchoolYear(Long.valueOf(ID));
    }
}
