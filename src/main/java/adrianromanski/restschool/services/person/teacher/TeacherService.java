package adrianromanski.restschool.services.person.teacher;

import adrianromanski.restschool.domain.base_entity.enums.Subjects;
import adrianromanski.restschool.model.base_entity.event.ExamDTO;
import adrianromanski.restschool.model.base_entity.person.StudentDTO;
import adrianromanski.restschool.model.base_entity.person.TeacherDTO;

import java.util.List;
import java.util.Map;


public interface TeacherService {

    List<TeacherDTO> getAllTeachers();

    TeacherDTO getTeacherByFirstNameAndLastName(String firstName, String lastName);

    TeacherDTO getTeacherByID(Long id);

    Map<Subjects, List<TeacherDTO>> getTeachersBySpecialization();

    Map<Long, List<TeacherDTO>> getTeachersByYearsOfExperience();

    ExamDTO addExamForClass(Long teacherID, ExamDTO examDTO);

    ExamDTO addCorrectionExamForStudent(Long teacherID, Long studentID, ExamDTO examDTO);

    StudentDTO addNewStudentToClass(Long teacherID, StudentDTO studentDTO);

    TeacherDTO createNewTeacher(TeacherDTO teacherDTO);

    TeacherDTO updateTeacher(Long id, TeacherDTO teacherDTO);

    void deleteTeacherById(Long id);


    // To do later

//    StudentDTO deleteStudentFromClass(Long teacherID, Long studentID);


}

