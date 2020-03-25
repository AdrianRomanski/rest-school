package adrianromanski.restschool.repositories;

import adrianromanski.restschool.domain.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Long> {

    Subject findByName(String name);
}
