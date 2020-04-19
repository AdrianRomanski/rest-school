package adrianromanski.restschool.services.guardian;

import adrianromanski.restschool.model.base_entity.person.GuardianDTO;

import java.util.List;

public interface GuardianService {

    List<GuardianDTO> getAllGuardians();

    GuardianDTO getGuardianByID(Long id);

    GuardianDTO getGuardianByFirstAndLastName(String firstName, String lastName);

    GuardianDTO createNewGuardian(GuardianDTO legalGuardianDTO);

    GuardianDTO updateGuardian(GuardianDTO legalGuardianDTO, Long id);

    void deleteGuardianByID(Long id);
}
