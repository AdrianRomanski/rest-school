package adrianromanski.restschool.services.group.student_class;

import adrianromanski.restschool.domain.enums.Gender;
import adrianromanski.restschool.domain.enums.Subjects;
import adrianromanski.restschool.model.group.StudentClassDTO;
import adrianromanski.restschool.model.person.StudentDTO;


import java.util.List;
import java.util.Map;

public interface StudentClassService {

    List<StudentClassDTO> getAllStudentClasses();

    StudentClassDTO getStudentClassByID(Long id);

    List<StudentClassDTO> getStudentClassByPresident(String president);

    Map<Subjects, Map<String, List<StudentClassDTO>>> getStudentClassesGroupedBySpecialization();

    List<StudentClassDTO> getAllStudentClassForSpecialization(Subjects subjects);

    List<StudentClassDTO> getLargestStudentClass();

    List<StudentClassDTO> getSmallestStudentClass();

    Map<Gender, List<StudentDTO>> getAllStudentsForClass(Long id);

    StudentClassDTO createNewStudentClass(StudentClassDTO studentClassDTO);

    StudentClassDTO updateStudentClass(Long id, StudentClassDTO studentClassDTO);

    void deleteStudentClassById(Long id);

    // To do Later

//    StudentClassDTO getStudentClassForTeacher(TeacherDTO teacherDTO);

//    StudentClassDTO getStudentClassForStudent(StudentDTO studentDTO);

//    List<StudentClassDTO> getAllStudentClassesForSchoolYear(SchoolYear schoolYear); // i have to create SchoolYear




}

