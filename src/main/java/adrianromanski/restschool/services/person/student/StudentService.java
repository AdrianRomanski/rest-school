package adrianromanski.restschool.services.person.student;


import adrianromanski.restschool.model.base_entity.AddressDTO;
import adrianromanski.restschool.model.base_entity.ContactDTO;
import adrianromanski.restschool.model.person.StudentDTO;

import java.util.List;
import java.util.Map;

public interface StudentService {

    // GET
    List<StudentDTO> getAllStudents();

    StudentDTO getStudentByID(Long studentID);

    List<StudentDTO> getAllFemaleStudents();

    List<StudentDTO> getAllMaleStudents();

    StudentDTO getStudentByName(String firstName, String lastName);

    Map<String, Map<String, List<StudentDTO>>> getStudentsByLocation();

    // POST
    StudentDTO createNewStudent(StudentDTO studentDTO);

    ContactDTO addContactToStudent(ContactDTO contactDTO, Long StudentID);

    AddressDTO addAddressToStudent(AddressDTO addressDTO, Long studentID);

    // PUT
    StudentDTO updateStudent(Long id, StudentDTO studentDTO);

    ContactDTO updateContact(ContactDTO contactDTO, Long studentID, Long contactID);

    AddressDTO updateAddress(AddressDTO addressDTO, Long studentID, Long addressID);

    // DELETE
    void deleteStudentByID(Long studentID);

    void deleteStudentContact(Long studentID);

    void deleteStudentAddress(Long addressID);

}
