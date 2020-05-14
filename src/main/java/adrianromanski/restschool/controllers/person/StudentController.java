package adrianromanski.restschool.controllers.person;

import adrianromanski.restschool.model.base_entity.AddressDTO;
import adrianromanski.restschool.model.base_entity.ContactDTO;
import adrianromanski.restschool.model.person.StudentDTO;
import adrianromanski.restschool.model.person.StudentListDTO;
import adrianromanski.restschool.services.person.student.StudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Api("Controller for Students")
@RestController
@RequestMapping("/students/")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @ApiOperation("Returns all Students sorted by age -> lastName -> firstName")
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public StudentListDTO getAllStudents() {
        return new StudentListDTO(studentService.getAllStudents());
    }

    @ApiOperation("Returns all Female Students sorted by age -> lastName -> firstName")
    @GetMapping("female")
    @ResponseStatus(HttpStatus.OK)
    public StudentListDTO getAllFemaleStudents() {
        return new StudentListDTO(studentService.getAllFemaleStudents());
    }


    @ApiOperation("Returns all Male Students sorted by age -> lastName -> firstName")
    @GetMapping("male")
    @ResponseStatus(HttpStatus.OK)
    public StudentListDTO getAllMaleStudents() {
        return new StudentListDTO(studentService.getAllMaleStudents());
    }

    @ApiOperation("Returns an Student with matching ID or else throw ResourceNotFoundException")
    @GetMapping("/getByID/student-{ID}")
    @ResponseStatus(HttpStatus.OK)
    public StudentDTO getStudentByID(@PathVariable String ID) {
        return studentService.getStudentByID(Long.valueOf(ID));
    }

    @ApiOperation("Returns a Student with matching firstName and lastName or else throw ResourceNotFoundException")
    @GetMapping("/getByName/{firstName}-{lastName}")
    @ResponseStatus(HttpStatus.OK)
    public StudentDTO getStudentByFirstAndLastName(@PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName) {
        return studentService.getStudentByName(firstName, lastName);
    }

    @ApiOperation("Create and save new Student based on StudentDTO body")
    @PostMapping("addStudent")
    @ResponseStatus(HttpStatus.CREATED)
    public StudentDTO createNewStudent(@RequestBody StudentDTO studentDTO) {
        return studentService.createNewStudent(studentDTO);
    }

    @ApiOperation("Adding Contact to Student with matching ID or else throw ResourceNotFoundException")
    @PostMapping("addContact/student-{ID}")
    @ResponseStatus(HttpStatus.CREATED)
    public ContactDTO addContactToStudent(@PathVariable String ID, @RequestBody ContactDTO contactDTO)  {
        return studentService.addContactToStudent(contactDTO, Long.valueOf(ID));
    }

    @ApiOperation("Adding Address to Student with matching ID or else throw ResourceNotFoundException")
    @PostMapping("addAddress/student-{studentID}")
    @ResponseStatus(HttpStatus.CREATED)
    public AddressDTO addAddressToStudent(@PathVariable String studentID,
                                          @RequestBody AddressDTO addressDTO)  {
        return studentService.addAddressToStudent(addressDTO, Long.valueOf(studentID));
    }

    @ApiOperation("Update an existing Student with matching ID or else throw ResourceNotFoundException")
    @PutMapping("updateStudent/student-{ID}")
    @ResponseStatus(HttpStatus.OK)
    public StudentDTO updateStudent(@PathVariable String ID, @RequestBody StudentDTO studentDTO) {
        return studentService.updateStudent(Long.valueOf(ID), studentDTO);
    }

    @ApiOperation("Update Student Contact with matching ID or else throw ResourceNotFoundException")
    @PutMapping("updateContact/student-{studentID}/contact-{contactID}")
    @ResponseStatus(HttpStatus.OK)
    public ContactDTO updateContact(@RequestBody @Valid ContactDTO contactDTO,
                                    @PathVariable  String studentID, @PathVariable String contactID) {
        return studentService.updateContact(contactDTO, Long.valueOf(studentID), Long.valueOf(contactID));
    }

    @ApiOperation("Update Student Address with matching ID or else throw ResourceNotFoundException")
    @PutMapping("updateAddress/student-{studentID}/address-{addressID}")
    @ResponseStatus(HttpStatus.OK)
    public AddressDTO updateAddress(@RequestBody @Valid AddressDTO addressDTO,
                                    @PathVariable  String studentID, @PathVariable String addressID) {
        return studentService.updateAddress(addressDTO, Long.valueOf(studentID), Long.valueOf(addressID));
    }

    @ApiOperation("Delete a student with matching ID or else throw ResourceNotFoundException")
    @DeleteMapping("deleteStudent/student-{ID}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteStudent(@PathVariable String ID) {
        studentService.deleteStudentByID(Long.valueOf(ID));
    }


    @ApiOperation("Delete contact from student with matching ID or else throw ResourceNotFoundException")
    @DeleteMapping("deleteContact/student-{ID}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteStudentContact(@PathVariable String ID) {
        studentService.deleteStudentContact(Long.valueOf(ID));
    }

    @ApiOperation("Delete address from student with matching ID or else throw ResourceNotFoundException")
    @DeleteMapping("deleteAddress/student-{ID}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteStudentAddress(@PathVariable String ID) {
        studentService.deleteStudentAddress(Long.valueOf(ID));
    }

}
