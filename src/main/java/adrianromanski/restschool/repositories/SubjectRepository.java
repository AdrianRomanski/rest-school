package adrianromanski.restschool.repositories;

import adrianromanski.restschool.domain.base_entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Long> {

    Subject findByName(String name);
}
