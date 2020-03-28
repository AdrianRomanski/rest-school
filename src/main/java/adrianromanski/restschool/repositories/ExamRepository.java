package adrianromanski.restschool.repositories;

import adrianromanski.restschool.domain.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamRepository extends JpaRepository<Exam, Long> {
}
