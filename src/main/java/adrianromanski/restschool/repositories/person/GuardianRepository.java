package adrianromanski.restschool.repositories.person;

import adrianromanski.restschool.domain.base_entity.person.Guardian;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GuardianRepository extends JpaRepository<Guardian, Long> {

    Optional<Guardian> getGuardianByFirstNameAndLastName(String firstName, String lastName);
}
