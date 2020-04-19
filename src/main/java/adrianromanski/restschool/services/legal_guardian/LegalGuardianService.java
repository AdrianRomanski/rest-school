package adrianromanski.restschool.services.legal_guardian;

import adrianromanski.restschool.domain.base_entity.person.LegalGuardian;
import adrianromanski.restschool.model.base_entity.person.LegalGuardianDTO;

import java.util.List;

public interface LegalGuardianService {

    List<LegalGuardianDTO> getAllLegalGuardians();

    LegalGuardianDTO getGuardianByID(Long id);

    LegalGuardianDTO getLegalGuardianByFirstAndLastName(String firstName, String lastName);

    LegalGuardianDTO createNewLegalGuardian(LegalGuardianDTO legalGuardianDTO);

    LegalGuardianDTO updateLegalGuardian(LegalGuardianDTO legalGuardianDTO, Long id);

    void deleteLegalGuardianByID(Long id);
}
