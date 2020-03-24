package adrianromanski.restschool.services;

import adrianromanski.restschool.model.StudentDTO;

import java.util.List;

public interface StudentService {

    List<StudentDTO> getAllStudents();

    StudentDTO getStudentByFirstAndLastName(String firstName, String lastName);

    StudentDTO createNewStudent(StudentDTO studentDTO);

    void deleteStudentByID(Long id);

    StudentDTO saveStudentByDTO(Long id, StudentDTO studentDTO);

    StudentDTO getStudentByID(Long id);

}
