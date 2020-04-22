package adrianromanski.restschool.services.event.exam_result;



import adrianromanski.restschool.model.base_entity.event.ExamResultDTO;

import java.util.List;

public interface ExamResultService {

    List<ExamResultDTO> getAllExamResults();

    ExamResultDTO getExamResultByID(Long id);

    ExamResultDTO createExamResult(ExamResultDTO examResultDTO);

    ExamResultDTO updateExamResult(Long id, ExamResultDTO examResultDTO);

    void deleteExamResultByID(Long id);

//    List<ExamResultDTO> getAllExamResultsForStudent(String firstName, String lastName);
//
//    List<ExamResultDTO> getAllExamResultsForSubject(String subjectName);
//
//    List<ExamResultDTO> getAllPassedExamResults();
//
//    List<ExamResultDTO> getAllNotPassedExamResults();
//
//    List<ExamResultDTO> getAllNotPassedForStudent(String firstName, String lastName);
//
//    List<ExamResultDTO> getAllPassedForStudent(String firstName, String lastName);
//
//    List<ExamResultDTO> getAllPassedForSubject(String subjectName);
//
//    List<ExamResultDTO> getAllNotPassedForSubject(String subjectName);






}
