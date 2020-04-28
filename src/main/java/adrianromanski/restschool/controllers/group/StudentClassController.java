package adrianromanski.restschool.controllers.group;

import adrianromanski.restschool.model.base_entity.group.StudentClassDTO;
import adrianromanski.restschool.model.base_entity.group.StudentClassListDTO;
import adrianromanski.restschool.model.base_entity.person.TeacherDTO;
import adrianromanski.restschool.services.group.student_class.StudentClassService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Api("Controller for Student Class")
@RestController()
@RequestMapping("/student-class/")
public class StudentClassController {

    private StudentClassService studentClassService;

    public StudentClassController(StudentClassService studentClassService) {
        this.studentClassService = studentClassService;
    }


    @ApiOperation("Returns a StudentClassListDTO Object that contains all Student Classes")
    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    public StudentClassListDTO getAllStudentClass() {
        return new StudentClassListDTO(studentClassService.getAllStudentClasses());
    }

    @ApiOperation("Returns an Student Class Object based on ID or else throw ResourceNotFoundException")
    @GetMapping("{ID}")
    @ResponseStatus(HttpStatus.OK)
    public StudentClassDTO getStudentClassByID(@PathVariable String ID) {
        return studentClassService.getStudentClassByID(Long.valueOf(ID));
    }

    @ApiOperation("Create and save new Student Class based on StudentClassDTO body")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StudentClassDTO createNewStudentClass(@RequestBody StudentClassDTO studentClassDTO) {
        return studentClassService.createNewStudentClass(studentClassDTO);
    }

    @ApiOperation("Update an existing Student Class with matching ID or create a new one")
    @PutMapping("{ID}")
    @ResponseStatus(HttpStatus.OK)
    public StudentClassDTO updateStudentClass(@PathVariable String ID, @RequestBody StudentClassDTO studentClassDTO) {
        return studentClassService.updateStudentClass(Long.valueOf(ID), studentClassDTO);
    }

    @ApiOperation("Delete a Student Class with matching ID")
    @DeleteMapping("{ID}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteStudentClassById(@PathVariable String ID) {
        studentClassService.deleteStudentClassById(Long.valueOf(ID));
    }

}
