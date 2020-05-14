package adrianromanski.restschool.services.event.payment;

import adrianromanski.restschool.model.event.PaymentDTO;

import java.util.List;

public interface PaymentService {

    List<PaymentDTO> getAllPayments();

    PaymentDTO getPaymentById(Long id);

    PaymentDTO getPaymentByName(String name);

    PaymentDTO createNewPayment(PaymentDTO paymentDTO);

    PaymentDTO updatePayment(Long id, PaymentDTO paymentDTO);

    void deletePaymentById(Long id);
}
