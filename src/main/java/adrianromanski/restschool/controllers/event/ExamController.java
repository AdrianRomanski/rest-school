package adrianromanski.restschool.controllers.event;

import adrianromanski.restschool.domain.enums.Subjects;
import adrianromanski.restschool.model.event.ExamDTO;
import adrianromanski.restschool.model.event.ExamListDTO;
import adrianromanski.restschool.services.event.exam.ExamService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api("Controller for Exams")
@RestController
@RequestMapping("/exams/")
public class ExamController {

   private final ExamService examService;

    public ExamController(ExamService examService) {
        this.examService = examService;
    }

    @ApiOperation("Returns a ExamListDTO Object that contains all Exams")
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ExamListDTO getAllExams() {
        return  new ExamListDTO(examService.getAllExams());
    }

    @ApiOperation("Returns an Exam Object with matching ID or else throw ResourceNotFoundException")
    @GetMapping("{ID}")
    @ResponseStatus(HttpStatus.OK)
    public ExamDTO getExamById(@PathVariable String ID) {
        return examService.getExamById(Long.valueOf(ID));
    }

    @ApiOperation("Returns an Exam Object with matching name or else throw ResourceNotFoundException")
    @GetMapping("name-{name}")
    @ResponseStatus(HttpStatus.OK)
    public ExamDTO getExamByName(@PathVariable String name) {
        return examService.getExamByName(name);
    }


    @ApiOperation("Returns an List of Exams for Teacher with matching firstName and lastName")
    @GetMapping("teacher-{firstName}/{lastName}")
    @ResponseStatus(HttpStatus.OK)
    public List<ExamDTO> getAllExamsForTeacher(@PathVariable String firstName, @PathVariable String lastName) {
         return examService.getAllExamsForTeacher(firstName, lastName);
    }

    @ApiOperation("Returns a Map where the Key is matching Subject and values List of Exams")
    @GetMapping("subject-{subject}")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, List<ExamDTO>> getExamsForSubject (@PathVariable String subject) {
        return examService.getExamsForSubject(Subjects.valueOf(subject));
    }

    @ApiOperation("Returns Map where the Keys are Subjects and values Maps where they Keys are Teachers and values List of exams")
    @GetMapping("grouped/subjects-teachers")
    @ResponseStatus(HttpStatus.OK)
    Map<String, Map<String, List<ExamDTO>>> getAllExamsBySubjectsAndTeachers() {
       return examService.getAllExamsBySubjectsAndTeachers();
    }

    @ApiOperation("Returns Map where the Keys are Number of Students and values Maps where they Keys are Subjects and values List of exams")
    @GetMapping("grouped/students-subjects")
    @ResponseStatus(HttpStatus.OK)
    Map<Integer, Map<String, List<ExamDTO>>> getAllExamsByStudentsAndSubjects() {
       return examService.getAllExamsByStudentsAndSubjects();
    }

    @ApiOperation("Create and save new Exam based on ExamDTO body")
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ExamDTO createNewExam(@RequestBody ExamDTO examDTO) {
        return examService.createNewExam(examDTO);
    }

    @ApiOperation("Update an existing exam with matching ID or create a new one")
    @PutMapping("{ID}")
    @ResponseStatus(HttpStatus.OK)
    public ExamDTO updateExam(@RequestBody ExamDTO examDTO, @PathVariable String ID) {
        return examService.updateExam(Long.valueOf(ID), examDTO);
    }


    
    @ApiOperation("Delete an Exam based on ID")
    @DeleteMapping("{ID}")
    @ResponseStatus(HttpStatus.OK)
    public void  deleteExamById(@PathVariable String ID) {
        examService.deleteExamById(Long.valueOf(ID));
    }
}