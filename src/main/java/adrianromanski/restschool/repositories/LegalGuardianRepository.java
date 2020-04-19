package adrianromanski.restschool.repositories;

import adrianromanski.restschool.domain.base_entity.person.LegalGuardian;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LegalGuardianRepository extends JpaRepository<LegalGuardian, Long> {

    Optional<LegalGuardian> getLegalGuardianByFirstNameAndLastName(String firstName, String lastName);
}
