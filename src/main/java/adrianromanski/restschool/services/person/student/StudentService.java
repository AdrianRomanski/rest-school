package adrianromanski.restschool.services.person.student;

import adrianromanski.restschool.model.base_entity.ContactDTO;
import adrianromanski.restschool.model.base_entity.address.StudentAddressDTO;
import adrianromanski.restschool.model.person.StudentDTO;

import java.util.List;
import java.util.Map;

public interface StudentService {

    // GET

    StudentDTO getStudentByID(Long studentID);

    StudentDTO getStudentByName(String firstName, String lastName);

    List<StudentDTO> getAllStudents();

    List<StudentDTO> getAllFemaleStudents();

    List<StudentDTO> getAllMaleStudents();

    Map<Long, List<StudentDTO>> getStudentsByAge();

    Map<String, Map<String, List<StudentDTO>>> getStudentsByLocation();

    // POST
    StudentDTO createNewStudent(StudentDTO studentDTO);

    ContactDTO addContactToStudent(ContactDTO contactDTO, Long StudentID);

    StudentAddressDTO addAddressToStudent(StudentAddressDTO addressDTO, Long studentID);

    // PUT
    StudentDTO updateStudent(Long id, StudentDTO studentDTO);

    ContactDTO updateContact(ContactDTO contactDTO, Long studentID, Long contactID);

    StudentAddressDTO updateAddress(StudentAddressDTO addressDTO, Long studentID, Long addressID);

    // DELETE
    void deleteStudentByID(Long studentID);

    void deleteStudentContact(Long studentID);

    void deleteStudentAddress(Long addressID);
}
