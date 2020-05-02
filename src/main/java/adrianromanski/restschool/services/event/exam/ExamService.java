package adrianromanski.restschool.services.event.exam;

import adrianromanski.restschool.domain.base_entity.enums.Subjects;
import adrianromanski.restschool.model.base_entity.event.ExamDTO;

import java.util.List;
import java.util.Map;

public interface ExamService  {

    List<ExamDTO> getAllExams();

    ExamDTO getExamById(Long id);

    ExamDTO getExamByName(String name);

    List<ExamDTO> getAllExamsForTeacher(String firstName, String lastName);

    Map<String, List<ExamDTO>> getExamsForSubject(Subjects subjects);

    Map<String, Map<String, List<ExamDTO>>> getAllExamsBySubjectsAndTeachers();

    Map<Integer, Map<String, List<ExamDTO>>> getAllExamsByStudentsAndSubjects();

    ExamDTO createNewExam(ExamDTO examDTO);

    ExamDTO updateExam(Long id, ExamDTO examDTO);

    void deleteExamById(Long id);

}
