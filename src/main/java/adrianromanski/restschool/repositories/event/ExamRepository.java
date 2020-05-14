package adrianromanski.restschool.repositories.event;

import adrianromanski.restschool.domain.event.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExamRepository extends JpaRepository<Exam, Long> {

    Optional<Exam> getByName(String name);

}
