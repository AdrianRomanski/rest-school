package adrianromanski.restschool.services.person.student;


import adrianromanski.restschool.model.base_entity.person.StudentDTO;

import java.util.List;

public interface StudentService {

    List<StudentDTO> getAllStudents();

    List<StudentDTO> getAllFemaleStudents();

    List<StudentDTO> getAllMaleStudents();

    StudentDTO getStudentByFirstAndLastName(String firstName, String lastName);

    StudentDTO createNewStudent(StudentDTO studentDTO);

    void deleteStudentByID(Long id);

    StudentDTO updateStudent(Long id, StudentDTO studentDTO);

    StudentDTO getStudentByID(Long id);

}
