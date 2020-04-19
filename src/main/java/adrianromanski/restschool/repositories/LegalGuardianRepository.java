package adrianromanski.restschool.repositories;

import adrianromanski.restschool.domain.base_entity.person.LegalGuardian;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LegalGuardianRepository extends JpaRepository<LegalGuardian, Long> {
}
