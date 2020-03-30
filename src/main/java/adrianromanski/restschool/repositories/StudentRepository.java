package adrianromanski.restschool.repositories;

import adrianromanski.restschool.domain.base_entity.person.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Student findByFirstNameAndLastName(String lastName, String firstName);

}
