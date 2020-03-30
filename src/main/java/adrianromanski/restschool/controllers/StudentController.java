package adrianromanski.restschool.controllers;

import adrianromanski.restschool.model.base_entity.person.StudentDTO;
import adrianromanski.restschool.model.base_entity.person.StudentListDTO;
import adrianromanski.restschool.services.student.StudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Api("Controller for Students")
@RestController()
@RequestMapping("/students/")
public class StudentController {

    private StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @ApiOperation("Returns a StudentListDTO Object that contains all Students")
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public StudentListDTO getAllStudents() {
        return new StudentListDTO(studentService.getAllStudents());
    }

    @ApiOperation("Returns an Student Object based on ID or else throw ResourceNotFoundException")
    @GetMapping("{ID}")
    @ResponseStatus(HttpStatus.OK)
    public StudentDTO getStudentByID(@PathVariable String ID) {
        return studentService.getStudentByID(Long.valueOf(ID));
    }

    @ApiOperation("Returns a Student Object based on firstName and lastName")
    @GetMapping({"{firstName}/{lastName}"})
    @ResponseStatus(HttpStatus.OK)
    public StudentDTO getStudentByFirstAndLastName(@PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName) {
        return studentService.getStudentByFirstAndLastName(firstName, lastName);
    }

    @ApiOperation("Create and save new Student based on StudentDTO body")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StudentDTO createNewStudent(@RequestBody StudentDTO studentDTO) {
        return studentService.createNewStudent(studentDTO);
    }

    @ApiOperation("Update an existing student with matching ID or create a new one")
    @PutMapping("{ID}")
    @ResponseStatus(HttpStatus.OK)
    public StudentDTO updateStudent(@PathVariable String ID, @RequestBody StudentDTO studentDTO) {
        return studentService.updateStudent(Long.valueOf(ID), studentDTO);
    }

    @ApiOperation("Delete a student based on ID")
    @DeleteMapping("{ID}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCustomer(@PathVariable String ID) {
        studentService.deleteStudentByID(Long.valueOf(ID));
    }
}
