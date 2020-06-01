package adrianromanski.restschool.repositories.person;

import adrianromanski.restschool.domain.person.Director;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DirectorRepository extends JpaRepository<Director, Long> {
}
