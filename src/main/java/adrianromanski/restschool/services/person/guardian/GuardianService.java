package adrianromanski.restschool.services.person.guardian;

import adrianromanski.restschool.model.base_entity.address.GuardianAddressDTO;
import adrianromanski.restschool.model.base_entity.contact.GuardianContactDTO;
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

    GuardianAddressDTO addAddress(Long id, GuardianAddressDTO addressDTO);

    GuardianContactDTO addContact(Long id, GuardianContactDTO contactDTO);

    // PUT
    GuardianDTO updateGuardian(GuardianDTO legalGuardianDTO, Long id);

    GuardianAddressDTO updateAddress(Long id, GuardianAddressDTO addressDTO);

    GuardianContactDTO updateContact(Long id, GuardianContactDTO contactDTO);

    // DELETE
    void deleteGuardianByID(Long id);

    void deleteAddress(Long id);

    void deleteContact(Long id);

}
