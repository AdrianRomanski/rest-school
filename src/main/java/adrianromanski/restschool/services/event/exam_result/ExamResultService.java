package adrianromanski.restschool.services.event.exam_result;

import adrianromanski.restschool.model.event.ExamResultDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface ExamResultService {

    // GET
    List<ExamResultDTO> getAllExamResults();

    ExamResultDTO getExamResultByID(Long id);

    List<ExamResultDTO> getAllPassedExamResults();

    List<ExamResultDTO> getAllNotPassedExamResults();

    List<ExamResultDTO> getAllPassedForSubject(String subjectName);

    List<ExamResultDTO> getAllNotPassedForSubject(String subjectName);

    Map<String, Map<String, List<ExamResultDTO>>> getResultsGroupedByGradeAndName();

    Map<LocalDate, Map<String, List<ExamResultDTO>>> getResultGroupedByDateAndGrade();

    // POST
    ExamResultDTO createExamResult(ExamResultDTO examResultDTO);

    // PUT
    ExamResultDTO updateExamResult(Long id, ExamResultDTO examResultDTO);

    // DELETE
    void deleteExamResultByID(Long id);








}
