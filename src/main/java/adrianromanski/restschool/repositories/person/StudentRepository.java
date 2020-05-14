package adrianromanski.restschool.repositories.person;

import adrianromanski.restschool.domain.person.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByFirstNameAndLastName(String lastName, String firstName);

}
