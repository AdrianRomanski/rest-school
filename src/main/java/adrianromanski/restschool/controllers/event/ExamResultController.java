package adrianromanski.restschool.controllers.event;

import adrianromanski.restschool.model.event.ExamResultDTO;
import adrianromanski.restschool.model.event.ExamResultListDTO;
import adrianromanski.restschool.services.event.exam_result.ExamResultService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;


@Api("Controller for Exam Results")
@RestController
@RequestMapping("/exam-results/")
public class ExamResultController {

    private final ExamResultService examResultService;

    public ExamResultController(ExamResultService examResultService) {
        this.examResultService = examResultService;
    }

    @ApiOperation("Returns a ExamResultListDTO Object that contains all Exam Results")
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ExamResultListDTO getAllExamResults() {
        return new ExamResultListDTO(examResultService.getAllExamResults());
    }

    @ApiOperation("Returns an ExamResult Object based on ID or else throw ResourceNotFoundException")
    @GetMapping("{ID}")
    @ResponseStatus(HttpStatus.OK)
    public ExamResultDTO getExamResultByID(@PathVariable String ID) {
        return examResultService.getExamResultByID(Long.valueOf(ID));
    }

    @ApiOperation("Returns All Exam Result with Grade higher than F")
    @GetMapping("passed")
    @ResponseStatus(HttpStatus.OK)
    public List<ExamResultDTO> getAllPassedExamResults() {
        return examResultService.getAllPassedExamResults();
    }

    @ApiOperation("Returns All Exam Result with Grade F")
    @GetMapping("failed")
    @ResponseStatus(HttpStatus.OK)
    public List<ExamResultDTO> getAllNotPassedExamResults() {
        return examResultService.getAllNotPassedExamResults();
    }

    @ApiOperation("Returns all Passed Exams with matching Subject")
    @GetMapping("subject-{subject}/passed")
    @ResponseStatus(HttpStatus.OK)
    public List<ExamResultDTO> getAllPassedForSubject(@PathVariable String subject) {
        return examResultService.getAllPassedForSubject(subject);
    }

    @ApiOperation("Returns all Failed Exams with matching Subject")
    @GetMapping("subject-{subject}/failed")
    @ResponseStatus(HttpStatus.OK)
    public List<ExamResultDTO> getAllNotPassedExamResults(@PathVariable String subject) {
        return examResultService.getAllNotPassedForSubject(subject);
    }

    @ApiOperation("Returns Exam Results grouped by -> Grade -> Exam Name")
    @GetMapping("groupedBy/grade-name")
    @ResponseStatus(HttpStatus.OK)
    Map<String, Map<String, List<ExamResultDTO>>> getResultsGroupedByGradeAndName() {
        return examResultService.getResultsGroupedByGradeAndName();
    }

    @ApiOperation("Returns Exam Results grouped by -> Date -> Grade")
    @GetMapping("groupedBy/date-grade")
    @ResponseStatus(HttpStatus.OK)
    Map<LocalDate, Map<String, List<ExamResultDTO>>> getResultGroupedByDateAndGrade() {
        return examResultService.getResultGroupedByDateAndGrade();
    }

    @ApiOperation("Create and save new ExamResult based on ExamResultDTO body")
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ExamResultDTO createExamResult(@RequestBody ExamResultDTO examResultDTO) {
        return examResultService.createExamResult(examResultDTO);
    }

    @ApiOperation("Update an existing ExamResult with matching ID or create a new one")
    @PutMapping("{ID}")
    @ResponseStatus(HttpStatus.OK)
    public ExamResultDTO updateExamResult(@PathVariable String ID, @RequestBody ExamResultDTO examResultDTO) {
        return examResultService.updateExamResult(Long.valueOf(ID), examResultDTO);
    }

    @ApiOperation("Delete an ExamResult based on ID")
    @DeleteMapping("{ID}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteExamResultByID(@PathVariable String ID) {
        examResultService.deleteExamResultByID(Long.valueOf(ID));
    }
}
