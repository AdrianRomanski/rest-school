package adrianromanski.restschool.repositories.base_entity;

import adrianromanski.restschool.domain.base_entity.Subject;
import adrianromanski.restschool.domain.enums.Subjects;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubjectRepository extends JpaRepository<Subject, Long> {

    Optional<Subject> findByName(Subjects name);
}
