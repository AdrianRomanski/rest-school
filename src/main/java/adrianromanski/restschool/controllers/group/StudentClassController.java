package adrianromanski.restschool.controllers.group;

import adrianromanski.restschool.domain.base_entity.enums.Gender;
import adrianromanski.restschool.domain.base_entity.enums.Subjects;
import adrianromanski.restschool.model.base_entity.group.StudentClassDTO;
import adrianromanski.restschool.model.base_entity.group.StudentClassListDTO;
import adrianromanski.restschool.model.base_entity.person.StudentDTO;
import adrianromanski.restschool.services.group.student_class.StudentClassService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api("Controller for Student Class")
@RestController()
@RequestMapping("/student-class/")
public class StudentClassController {

    private final StudentClassService studentClassService;

    public StudentClassController(StudentClassService studentClassService) {
        this.studentClassService = studentClassService;
    }

    @ApiOperation("Returns a StudentClassListDTO Object that contains all Student Classes")
    @GetMapping()
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

    @ApiOperation("Return Map where the keys are Specializations and values maps containing Student Classes grouped by name")
    @GetMapping("specializations")
    @ResponseStatus(HttpStatus.OK)
    public Map<Subjects, Map<String, List<StudentClassDTO>>> getStudentClassesBySpecialization() {
        return studentClassService.getStudentClassesGroupedBySpecialization();
    }

    @ApiOperation("Returns a list of Student Classes with matching specialization")
    @GetMapping("specialization/{specialization}")
    @ResponseStatus(HttpStatus.OK)
    public List<StudentClassDTO> getAllStudentClassForSpecialization(@PathVariable String specialization) {
        return studentClassService.getAllStudentClassForSpecialization(Subjects.valueOf(specialization));
    }

    @ApiOperation("Returns an Student Class Object based on ID or else throw ResourceNotFoundException")
    @GetMapping("ID-{ID}/students")
    @ResponseStatus(HttpStatus.OK)
    public Map<Gender, List<StudentDTO>> getAllStudentsForClass(@PathVariable String ID) {
        return studentClassService.getAllStudentsForClass(Long.valueOf(ID));
    }

    @ApiOperation("Returns a list of Student Classes with largest number of students")
    @GetMapping("largest")
    @ResponseStatus(HttpStatus.OK)
    public List<StudentClassDTO>  getLargestStudentClass() {
        return studentClassService.getLargestStudentClass();
    }

    @ApiOperation("Returns a list of Student Classes with largest number of students")
    @GetMapping("smallest")
    @ResponseStatus(HttpStatus.OK)
    public List<StudentClassDTO>  getSmallestStudentClass() {
        return studentClassService.getSmallestStudentClass();
    }

    @ApiOperation("Returns a List of Student Classes with matching president")
    @GetMapping("president-{president}")
    @ResponseStatus(HttpStatus.OK)
    public List<StudentClassDTO>  getStudentClassByPresident(@PathVariable String president) {
        return studentClassService.getStudentClassByPresident(president);
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
