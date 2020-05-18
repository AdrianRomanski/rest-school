package adrianromanski.restschool.services.person.teacher;

import adrianromanski.restschool.domain.enums.Subjects;
import adrianromanski.restschool.model.base_entity.address.TeacherAddressDTO;
import adrianromanski.restschool.model.event.ExamDTO;
import adrianromanski.restschool.model.person.StudentDTO;
import adrianromanski.restschool.model.person.TeacherDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;


public interface TeacherService {

    // GET
    TeacherDTO getTeacherByFirstNameAndLastName(String firstName, String lastName);

    TeacherDTO getTeacherByID(Long id);

    List<TeacherDTO> getAllTeachers();

    Map<Subjects, List<TeacherDTO>> getTeachersBySpecialization();

    Map<Long, List<TeacherDTO>> getTeachersByYearsOfExperience();

    // POST
    ExamDTO addExamForClass(Long teacherID, ExamDTO examDTO);

    ExamDTO addCorrectionExamToStudent(Long teacherID, Long studentID, ExamDTO examDTO);

    StudentDTO addNewStudentToClass(Long teacherID, StudentDTO studentDTO);

    TeacherDTO createNewTeacher(TeacherDTO teacherDTO);

    TeacherAddressDTO addAddressToTeacher(Long teacherID, TeacherAddressDTO teacherAddressDTO);

    // PUT
    ExamDTO moveExamToAnotherDay(Long teacherID, Integer examID, LocalDate date);

    TeacherDTO changeClassPresident(Long teacherID, Long studentID);

    TeacherDTO updateTeacher(Long id, TeacherDTO teacherDTO);

    TeacherAddressDTO updateAddress(Long teacherID, TeacherAddressDTO addressDTO);

    // DELETE
    void deleteTeacherById(Long id);

    void removeStudentFromClass(Long teacherID, Long studentID);
}

