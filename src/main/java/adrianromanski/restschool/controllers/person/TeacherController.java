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

    @ApiOperation("Returns Teacher with matching ID")
    @GetMapping("getByID/teacher-{ID}")
    @ResponseStatus(HttpStatus.OK)
    public TeacherDTO getTeacherByID(@PathVariable String ID) {
        return teacherService.getTeacherByID(Long.valueOf(ID));
    }


    @ApiOperation("Returns Teacher with matching firstName and lastName")
    @GetMapping("getByName/{firstName}-{lastName}")
    @ResponseStatus(HttpStatus.OK)
    public TeacherDTO getTeacherByFirstNameAndLastName(@PathVariable String firstName, @PathVariable String lastName) {
        return teacherService.getTeacherByFirstNameAndLastName(firstName,lastName);
    }

    @ApiOperation("Returns Teachers sorted by Specialization -> yearsOfExperience")
    @GetMapping({"/list", "/", "", "getAll", "findAll"})
    @ResponseStatus(HttpStatus.OK)
    public TeacherListDTO getAllTeachers() {
        return new TeacherListDTO(teacherService.getAllTeachers());
    }

    @ApiOperation("Returns Teachers grouped by Specialization")
    @GetMapping("groupedBy/specializations")
    @ResponseStatus(HttpStatus.OK)
    public Map<Subjects, List<TeacherDTO>> getTeachersBySpecialization() {
        return teacherService.getTeachersBySpecialization();
    }

    @ApiOperation("Returns Teachers grouped by experience")
    @GetMapping("groupedBy/experience")
    @ResponseStatus(HttpStatus.OK)
    public Map<Long, List<TeacherDTO>> getTeachersByYearsOfExperience() {
        return teacherService.getTeachersByYearsOfExperience();
    }

    @ApiOperation("Adding Exam to every Student in the Class")
    @PostMapping("addExamToClass/teacher-{id}")
    @ResponseStatus(HttpStatus.OK)
    ExamDTO addExamToClass(@PathVariable String id, @RequestBody ExamDTO examDTO) {
        return teacherService.addExamForClass(Long.valueOf(id), examDTO);
    }

    @ApiOperation("Adding Correction Exam to Student with matching id")
    @PostMapping("addExamToStudent/teacher-{teacherID}/student-{studentID}")
    @ResponseStatus(HttpStatus.OK)
    ExamDTO addCorrectionExamToStudent(@PathVariable String teacherID, @PathVariable String studentID,
                                        @RequestBody ExamDTO examDTO) {
        return teacherService.addCorrectionExamToStudent(Long.valueOf(teacherID), Long.valueOf(studentID), examDTO);
    }

    @ApiOperation("Adding new Student to the Class")
    @PostMapping("addStudent/teacher-{id}")
    @ResponseStatus(HttpStatus.OK)
    StudentDTO addNewStudentToClass(@PathVariable String id, @RequestBody StudentDTO studentDTO) {
        return teacherService.addNewStudentToClass(Long.valueOf(id), studentDTO);
    }

    @ApiOperation("Create and save new Teacher to Database")
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public TeacherDTO createNewTeacher(@RequestBody TeacherDTO teacherDTO) {
        return teacherService.createNewTeacher(teacherDTO);
    }

    @ApiOperation("Changing class president")
    @PutMapping("changePresident/teacher-{teacherID}/student-{studentID}")
    @ResponseStatus(HttpStatus.OK)
    public TeacherDTO changeClassPresident(@PathVariable String teacherID, @PathVariable String studentID) {
        return teacherService.changeClassPresident(Long.valueOf(teacherID), Long.valueOf(studentID));
    }

    @ApiOperation("Update an existing Teacher with matching ID or create a new one")
    @PutMapping("updateTeacher/{ID}")
    @ResponseStatus(HttpStatus.OK)
    public TeacherDTO updateTeacher(@PathVariable String ID, @RequestBody TeacherDTO teacherDTO) {
        return teacherService.updateTeacher(Long.valueOf(ID), teacherDTO);
    }

    @ApiOperation("Removes a Student from the Class")
    @DeleteMapping("removeStudent/teacher-{teacherID}/student-{studentID}")
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
