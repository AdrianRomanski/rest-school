package adrianromanski.restschool.repositories.person;

import adrianromanski.restschool.domain.base_entity.person.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    Teacher getTeacherByFirstNameAndLastName(String firstName, String lastName);
}
