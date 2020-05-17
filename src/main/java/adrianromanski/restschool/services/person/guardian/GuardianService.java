package adrianromanski.restschool.services.person.guardian;

import adrianromanski.restschool.model.person.GuardianDTO;
import adrianromanski.restschool.model.person.StudentDTO;

import java.util.List;
import java.util.Map;

public interface GuardianService {

    // GET
    GuardianDTO getGuardianByID(Long id);

    GuardianDTO getGuardianByFirstAndLastName(String firstName, String lastName);

    List<GuardianDTO> getAllGuardians();

    List<StudentDTO> getAllStudentsForGuardian(Long id);

    Map<Long, List<GuardianDTO>> getGuardiansByAge();

    // POST
    GuardianDTO createNewGuardian(GuardianDTO legalGuardianDTO);

    // PUT
    GuardianDTO updateGuardian(GuardianDTO legalGuardianDTO, Long id);

    // DELETE
    void deleteGuardianByID(Long id);

}
