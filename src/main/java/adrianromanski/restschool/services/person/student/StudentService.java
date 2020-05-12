package adrianromanski.restschool.services.person.student;


import adrianromanski.restschool.domain.base_entity.Contact;
import adrianromanski.restschool.model.base_entity.AddressDTO;
import adrianromanski.restschool.model.base_entity.ContactDTO;
import adrianromanski.restschool.model.base_entity.person.StudentDTO;

import java.util.List;

public interface StudentService {

    // GET
    List<StudentDTO> getAllStudents();

    StudentDTO getStudentByID(Long id);

    List<StudentDTO> getAllFemaleStudents();

    List<StudentDTO> getAllMaleStudents();

    StudentDTO getStudentByFirstAndLastName(String firstName, String lastName);

    // POST
    StudentDTO createNewStudent(StudentDTO studentDTO);

    ContactDTO addContactToStudent(ContactDTO contactDTO, Long StudentID);

    AddressDTO addAddressToStudent(AddressDTO addressDTO, Long studentID, Long contactID);

    // PUT
    StudentDTO updateStudent(Long id, StudentDTO studentDTO);

    // DELETE
    void deleteStudentByID(Long id);

}
