package adrianromanski.restschool.repositories.base_entity;

import adrianromanski.restschool.domain.base_entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
