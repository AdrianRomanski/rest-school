package adrianromanski.restschool.repositories;

import adrianromanski.restschool.domain.base_entity.event.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
