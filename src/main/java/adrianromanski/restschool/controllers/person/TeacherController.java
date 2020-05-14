package adrianromanski.restschool.controllers.person;

import adrianromanski.restschool.domain.enums.Subjects;
import adrianromanski.restschool.model.event.ExamDTO;
import adrianromanski.restschool.model.person.StudentDTO;
import adrianromanski.restschool.model.person.TeacherDTO;
import adrianromanski.restschool.model.person.TeacherListDTO;
import adrianromanski.restschool.services.person.teacher.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api("Controller for teachers")
@RestController
@RequestMapping("/teachers/")
public class TeacherController {

    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @ApiOperation("Returns a TeachListDTO Object that contains all Teachers")
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public TeacherListDTO getAllTeachers() {
        return new TeacherListDTO(teacherService.getAllTeachers());
    }

    @ApiOperation("Returns a Teacher Object based on firstName and lastName")
    @GetMapping("{firstName}/{lastName}")
    @ResponseStatus(HttpStatus.OK)
    public TeacherDTO getTeacherByFirstNameAndLastName(@PathVariable String firstName, @PathVariable String lastName) {
        return teacherService.getTeacherByFirstNameAndLastName(firstName,lastName);
    }

    @ApiOperation("Returns an Teacher Object based on ID or else throw ResourceNotFoundException")
    @GetMapping("{ID}")
    @ResponseStatus(HttpStatus.OK)
    public TeacherDTO getTeacherByID(@PathVariable String ID) {
        return teacherService.getTeacherByID(Long.valueOf(ID));
    }

    @ApiOperation("Returns a Map where the keys are Specializations and values List of Teachers")
    @GetMapping("/specializations")
    @ResponseStatus(HttpStatus.OK)
    public Map<Subjects, List<TeacherDTO>> getTeachersBySpecialization() {
        return teacherService.getTeachersBySpecialization();
    }

    @ApiOperation("Returns a Map where the keys are years of experience and values List of Teachers")
    @GetMapping("/experience")
    @ResponseStatus(HttpStatus.OK)
    public Map<Long, List<TeacherDTO>> getTeachersByYearsOfExperience() {
        return teacherService.getTeachersByYearsOfExperience();
    }

    @ApiOperation("Adding Exam to every Student in the Class")
    @PostMapping("/teacher-{id}/addExam")
    @ResponseStatus(HttpStatus.OK)
    ExamDTO addExamForClass(@PathVariable String id, @RequestBody ExamDTO examDTO) {
        return teacherService.addExamForClass(Long.valueOf(id), examDTO);
    }

    @ApiOperation("Adding Correction Exam to Student with matching id")
    @PostMapping("/teacher-{teacherID}/student-{studentID}/addExam")
    @ResponseStatus(HttpStatus.OK)
    ExamDTO addCorrectionExamForStudent(@PathVariable String teacherID, @PathVariable String studentID,
                                        @RequestBody ExamDTO examDTO) {
        return teacherService.addCorrectionExamForStudent(Long.valueOf(teacherID), Long.valueOf(studentID), examDTO);
    }

    @ApiOperation("Adding new Student to the Class")
    @PostMapping("/teacher-{id}/addStudent")
    @ResponseStatus(HttpStatus.OK)
    StudentDTO addNewStudentToClass(@PathVariable String id, @RequestBody StudentDTO studentDTO) {
        return teacherService.addNewStudentToClass(Long.valueOf(id), studentDTO);
    }

    @ApiOperation("Create and save new Teacher based on TeacherDTO body")
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public TeacherDTO createNewTeacher(@RequestBody TeacherDTO teacherDTO) {
        return teacherService.createNewTeacher(teacherDTO);
    }

    @ApiOperation("Changing class president")
    @PutMapping("teacher-{teacherID}/student-{studentID}/changeClassPresident")
    @ResponseStatus(HttpStatus.OK)
    public TeacherDTO changeClassPresident(@PathVariable String teacherID, @PathVariable String studentID) {
        return teacherService.changeClassPresident(Long.valueOf(teacherID), Long.valueOf(studentID));
    }

    @ApiOperation("Update an existing Teacher with matching ID or create a new one")
    @PutMapping("{ID}")
    @ResponseStatus(HttpStatus.OK)
    public TeacherDTO updateTeacher(@PathVariable String ID, @RequestBody TeacherDTO teacherDTO) {
        return teacherService.updateTeacher(Long.valueOf(ID), teacherDTO);
    }

    @ApiOperation("Removes a Student from the Class")
    @DeleteMapping("teacher-{teacherID}/student-{studentID}/removeFromTheClass")
    @ResponseStatus(HttpStatus.OK)
    public void removeStudentFromClass(@PathVariable String teacherID, @PathVariable String studentID) {
        teacherService.removeStudentFromClass(Long.valueOf(teacherID), Long.valueOf(studentID));
    }

    @ApiOperation("Delete a Teacher based on ID")
    @DeleteMapping("{ID}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTeacherById(@PathVariable String ID) {
        teacherService.deleteTeacherById(Long.valueOf(ID));
    }
}
