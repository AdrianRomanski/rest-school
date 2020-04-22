package adrianromanski.restschool.controllers.event;

import adrianromanski.restschool.model.base_entity.event.ExamDTO;
import adrianromanski.restschool.model.base_entity.event.ExamListDTO;
import adrianromanski.restschool.services.event.exam.ExamService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation("Returns an Exam Object based on ID or else throw ResourceNotFoundException")
    @GetMapping("{ID}")
    @ResponseStatus(HttpStatus.OK)
    public ExamDTO getExamById(@PathVariable String ID) {
        return examService.getExamById(Long.valueOf(ID));
    }
    
    @ApiOperation("Delete an Exam based on ID")
    @DeleteMapping("{ID}")
    @ResponseStatus(HttpStatus.OK)
    public void  deleteExamById(@PathVariable String ID) {
        examService.deleteExamById(Long.valueOf(ID));
    }
}