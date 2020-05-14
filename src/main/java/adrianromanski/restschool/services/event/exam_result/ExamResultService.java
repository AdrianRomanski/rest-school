package adrianromanski.restschool.services.event.exam_result;

import adrianromanski.restschool.model.event.ExamResultDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface ExamResultService {

    List<ExamResultDTO> getAllExamResults();

    ExamResultDTO getExamResultByID(Long id);

    List<ExamResultDTO> getAllPassedExamResults();

    List<ExamResultDTO> getAllNotPassedExamResults();

    List<ExamResultDTO> getAllPassedForSubject(String subjectName);

    List<ExamResultDTO> getAllNotPassedForSubject(String subjectName);

    Map<String, Map<String, List<ExamResultDTO>>> getResultsGroupedByGradeAndName();

    Map<LocalDate, Map<String, List<ExamResultDTO>>> getResultGroupedByDateAndGrade();

    ExamResultDTO createExamResult(ExamResultDTO examResultDTO);

    ExamResultDTO updateExamResult(Long id, ExamResultDTO examResultDTO);

    void deleteExamResultByID(Long id);








}
