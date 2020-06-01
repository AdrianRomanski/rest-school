package adrianromanski.restschool.services.group.student_class;

import adrianromanski.restschool.domain.enums.Gender;
import adrianromanski.restschool.domain.enums.Subjects;
import adrianromanski.restschool.model.group.StudentClassDTO;
import adrianromanski.restschool.model.person.StudentDTO;


import java.util.List;
import java.util.Map;

public interface StudentClassService {

    // GET
    List<StudentClassDTO> getAllStudentClasses();

    StudentClassDTO getStudentClassByID(Long id);

    List<StudentClassDTO> getStudentClassByPresident(String president);

    Map<Subjects, Map<String, List<StudentClassDTO>>> getStudentClassesGroupedBySpecialization();

    List<StudentClassDTO> getAllStudentClassForSpecialization(Subjects subjects);

    List<StudentClassDTO> getLargestStudentClass();

    List<StudentClassDTO> getSmallestStudentClass();

    Map<Gender, List<StudentDTO>> getAllStudentsForClass(Long id);

    // POST
    StudentClassDTO createNewStudentClass(StudentClassDTO studentClassDTO);

    // PUT
    StudentClassDTO updateStudentClass(Long id, StudentClassDTO studentClassDTO);

    // DELETE
    void deleteStudentClassById(Long id);

//    List<StudentClassDTO> getAllStudentClassesForSchoolYear(SchoolYear schoolYear); // i have to create SchoolYear


}

