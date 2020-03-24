package adrianromanski.restschool.repositories;

import adrianromanski.restschool.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
