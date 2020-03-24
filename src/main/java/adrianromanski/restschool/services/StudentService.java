package adrianromanski.restschool.services;

import adrianromanski.restschool.model.StudentDTO;

import java.util.List;

public interface StudentService {

    List<StudentDTO> getAllStudents();

    StudentDTO getStudentByLastName();

}
