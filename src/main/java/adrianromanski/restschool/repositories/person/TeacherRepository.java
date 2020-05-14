package adrianromanski.restschool.repositories.person;

import adrianromanski.restschool.domain.person.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    Optional<Teacher> getTeacherByFirstNameAndLastName(String firstName, String lastName);
}
