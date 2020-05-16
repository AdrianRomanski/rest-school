package adrianromanski.restschool.repositories.base_entity;

import adrianromanski.restschool.domain.base_entity.contact.StudentContact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentContactRepository extends JpaRepository<StudentContact, Long> {

}
