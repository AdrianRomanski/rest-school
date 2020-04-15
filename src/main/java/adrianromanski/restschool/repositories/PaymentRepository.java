package adrianromanski.restschool.repositories;

import adrianromanski.restschool.domain.base_entity.event.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findPaymentByName(String name);
}
