package adrianromanski.restschool.repositories;

import adrianromanski.restschool.domain.base_entity.person.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
}
