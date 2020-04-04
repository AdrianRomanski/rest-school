package adrianromanski.restschool.services.student_class;

import adrianromanski.restschool.domain.base_entity.group.StudentClass;
import adrianromanski.restschool.model.base_entity.group.StudentClassDTO;
import adrianromanski.restschool.model.base_entity.person.StudentDTO;
import adrianromanski.restschool.model.base_entity.person.TeacherDTO;

import java.util.List;

public interface StudentClassService {

    List<StudentClassDTO> getAllStudentClasses();

    StudentClassDTO getStudentClassByID(Long id);

    StudentClassDTO createNewStudentClass(StudentClassDTO studentClassDTO);

    StudentClassDTO updateStudentClass(Long id, StudentClassDTO studentClassDTO);

    TeacherDTO getStudentClassTeacher(StudentClassDTO studentClassDTO);

    void deleteStudentClassById(Long id);

    // To do Later

//    StudentDTO getStudentClassPresident(StudentClassDTO studentClassDTO);

//    StudentClassDTO getStudentClassForTeacher(TeacherDTO teacherDTO);
//
//    StudentClassDTO getStudentClassForStudent(StudentDTO studentDTO);
//
//    List<StudentDTO> getAllStudentsForClass(StudentClassDTO studentClassDTO);
//
//    List<StudentClassDTO> getAllStudentClassesForSchoolYear(SchoolYear schoolYear);




}

