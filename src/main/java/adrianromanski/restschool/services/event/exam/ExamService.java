package adrianromanski.restschool.services.event.exam;

import adrianromanski.restschool.domain.enums.Subjects;
import adrianromanski.restschool.model.event.ExamDTO;

import java.util.List;
import java.util.Map;

public interface ExamService  {

    // GET
    List<ExamDTO> getAllExams();

    ExamDTO getExamById(Long id);

    ExamDTO getExamByName(String name);

    List<ExamDTO> getAllExamsForTeacher(String firstName, String lastName);

    Map<String, List<ExamDTO>> getExamsForSubject(Subjects subjects);

    Map<String, Map<String, List<ExamDTO>>> getAllExamsBySubjectsAndTeachers();

    Map<Integer, Map<String, List<ExamDTO>>> getAllExamsByStudentsAndSubjects();

    // POST
    ExamDTO createNewExam(ExamDTO examDTO);

    // PUT
    ExamDTO updateExam(Long id, ExamDTO examDTO);

    // DELETE
    void deleteExamById(Long id);

}
