package adrianromanski.restschool.services.exam;

import adrianromanski.restschool.model.base_entity.event.ExamDTO;

import java.util.List;

public interface ExamService  {

    List<ExamDTO> getAllExams();

    ExamDTO createNewExam(ExamDTO examDTO);

    ExamDTO updateExam(Long id, ExamDTO examDTO);

    ExamDTO getExamById(Long id);

    void deleteExamById(Long id);

}
