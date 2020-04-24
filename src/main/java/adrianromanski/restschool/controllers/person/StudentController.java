package adrianromanski.restschool.controllers.person;

import adrianromanski.restschool.model.base_entity.person.StudentDTO;
import adrianromanski.restschool.model.base_entity.person.StudentListDTO;
import adrianromanski.restschool.services.person.student.StudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@Api("Controller for Students")
@RestController
@RequestMapping("/students/")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @ApiOperation("Returns all Students sorted by lastName&firstName")
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public StudentListDTO getAllStudents() {
        return new StudentListDTO(studentService.getAllStudents());
    }

    @ApiOperation("Returns all Female Students sorted by lastName&firstName")
    @GetMapping("/female")
    @ResponseStatus(HttpStatus.OK)
    public StudentListDTO getAllFemaleStudents() {
        return new StudentListDTO(studentService.getAllFemaleStudents());
    }


    @ApiOperation("Returns all Male Students sorted by lastName&firstName")
    @GetMapping("/male")
    @ResponseStatus(HttpStatus.OK)
    public StudentListDTO getAllMaleStudents() {
        return new StudentListDTO(studentService.getAllMaleStudents());
    }

    @ApiOperation("Returns an Student with matching ID or else throw ResourceNotFoundException")
    @GetMapping("{ID}")
    @ResponseStatus(HttpStatus.OK)
    public StudentDTO getStudentByID(@PathVariable String ID) {
        return studentService.getStudentByID(Long.valueOf(ID));
    }

    @ApiOperation("Returns a Student with matching firstName and lastName or else throw ResourceNotFoundException")
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

    @ApiOperation("Update an existing Student with matching ID")
    @PutMapping("{ID}")
    @ResponseStatus(HttpStatus.OK)
    public StudentDTO updateStudent(@PathVariable String ID, @RequestBody StudentDTO studentDTO) {
        return studentService.updateStudent(Long.valueOf(ID), studentDTO);
    }

    @ApiOperation("Delete a student with matching ID")
    @DeleteMapping("{ID}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCustomer(@PathVariable String ID) {
        studentService.deleteStudentByID(Long.valueOf(ID));
    }

}
