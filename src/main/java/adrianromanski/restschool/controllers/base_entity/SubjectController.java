package adrianromanski.restschool.controllers.base_entity;

import adrianromanski.restschool.model.base_entity.SubjectDTO;
import adrianromanski.restschool.model.base_entity.SubjectListDTO;
import adrianromanski.restschool.services.base_entity.subject.SubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("Controller for Subjects")
@RestController
@RequestMapping("/subjects/")
public class SubjectController {

    private final SubjectService  subjectService;

    public SubjectController(SubjectService subjectService) { this.subjectService = subjectService;}

    @ApiOperation("Returns a SubjectListDTO Object that contains all Subjects")
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public SubjectListDTO getAllSubjects() {
        return new SubjectListDTO(subjectService.getAllSubjects());
    }

    @ApiOperation("Returns a Subject Object based on ID or else throw ResourceNotFoundException")
    @GetMapping("id-{ID}")
    @ResponseStatus(HttpStatus.OK)
    public SubjectDTO getSubjectByID(@PathVariable String ID) {
        return subjectService.getSubjectByID(Long.valueOf(ID));
    }

    @ApiOperation("Create and save new Subject based on SubjectDTO body")
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public SubjectDTO createNewSubject(@RequestBody SubjectDTO subjectDTO) {
        return subjectService.createNewSubject(subjectDTO);
    }

    @ApiOperation("Update an existing subject with matching ID or create a new one")
    @PutMapping("{ID}")
    @ResponseStatus(HttpStatus.OK)
    public SubjectDTO updateSubject(@PathVariable String ID, @RequestBody SubjectDTO subjectDTO) {
        return subjectService.updateSubject(Long.valueOf(ID), subjectDTO);
    }

    @ApiOperation("Returns a Subject Object based on name")
    @GetMapping("name-{name}")
    @ResponseStatus(HttpStatus.OK)
    public SubjectDTO getSubjectByName(@PathVariable String name) {
        return subjectService.getSubjectByName(name);
    }

    @ApiOperation("Returns a SubjectListDTO that contains a subjects with full points value")
    @GetMapping("most-valuable")
    @ResponseStatus(HttpStatus.OK)
    public SubjectListDTO getSubjectsWithFullValue() {
        return new SubjectListDTO(subjectService.getSubjectsWithFullValue());
    }

    @ApiOperation("Returns a SubjectListDTO that contains a subjects with lowest points value")
    @GetMapping("less-valuable")
    @ResponseStatus(HttpStatus.OK)
    public SubjectListDTO getSubjectsWithLowestValue() {
        return new SubjectListDTO(subjectService.getSubjectsWithLowestValue());
    }

    @ApiOperation("Delete a subject based on ID")
    @DeleteMapping("{ID}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteSubjectByID(@PathVariable String ID) {
        subjectService.deleteSubjectByID(Long.valueOf(ID));
    }

}
