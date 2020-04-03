package adrianromanski.restschool.repositories;

import adrianromanski.restschool.domain.base_entity.group.StudentClass;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentClassRepository extends JpaRepository<StudentClass, Long> {
}
