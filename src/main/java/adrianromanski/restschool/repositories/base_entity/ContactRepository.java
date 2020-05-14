package adrianromanski.restschool.repositories.base_entity;

import adrianromanski.restschool.domain.base_entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContactRepository extends JpaRepository<Contact, Long> {

}
