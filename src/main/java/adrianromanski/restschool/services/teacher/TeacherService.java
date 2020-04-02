package adrianromanski.restschool.services.teacher;

import adrianromanski.restschool.domain.base_entity.event.Exam;
import adrianromanski.restschool.domain.base_entity.event.ExamResult;
import adrianromanski.restschool.model.base_entity.event.ExamDTO;
import adrianromanski.restschool.model.base_entity.person.StudentDTO;
import adrianromanski.restschool.model.base_entity.person.TeacherDTO;

import java.util.List;

public interface TeacherService {

    List<TeacherDTO> getAllTeachers();

    TeacherDTO getTeacherByFirstNameAndLastName(String firstName, String lastName);

    TeacherDTO getTeacherByID(Long id);

    TeacherDTO createNewTeacher(TeacherDTO teacherDTO);

    TeacherDTO updateTeacher(Long id, TeacherDTO teacherDTO);

    void deleteTeacherById(Long id);

    // To do later

//    List<StudentDTO> getAllStudentsForTeacher(Long id);
//
//    List<ExamDTO> getAllExamsForTeacher(Long id);
//
//    ExamDTO addExamForClass(Long id, ExamDTO examDTO);
//
//    ExamDTO addExamForStudent(Long teacherID, Long studentID);
//
//    ExamResult addExamResultForStudent(Long teacherID, Long studentID, Long examID, ExamResult examResult);
//
//    StudentDTO addNewStudentToClass(Long teacherID, StudentDTO studentDTO);
//
//    StudentDTO deleteStudentFromClass(Long teacherID, Long studentID);


}

