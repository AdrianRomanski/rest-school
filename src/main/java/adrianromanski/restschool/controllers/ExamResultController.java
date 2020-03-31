package adrianromanski.restschool.controllers;

import adrianromanski.restschool.model.base_entity.event.ExamResultDTO;
import adrianromanski.restschool.model.base_entity.event.ExamResultListDTO;
import adrianromanski.restschool.services.exam_result.ExamResultService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


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
