package adrianromanski.restschool.repositories.event;

import adrianromanski.restschool.domain.base_entity.event.ExamResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamResultRepository extends JpaRepository<ExamResult, Long> {
}
