package adrianromanski.restschool.services.person.teacher;

import adrianromanski.restschool.domain.base_entity.person.Teacher;
import adrianromanski.restschool.model.base_entity.person.TeacherDTO;

import java.util.List;
import java.util.Map;


public interface TeacherService {

    List<TeacherDTO> getAllTeachers();

    TeacherDTO getTeacherByFirstNameAndLastName(String firstName, String lastName);

    TeacherDTO getTeacherByID(Long id);

    Map<String, List<TeacherDTO>> getTeachersBySpecialization();

    Map<Long, List<TeacherDTO>> getTeachersByYearsOfExperience();

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

