package adrianromanski.restschool.repositories.event;

import adrianromanski.restschool.domain.event.SchoolYear;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolYearRepository extends JpaRepository<SchoolYear, Long> {
}
