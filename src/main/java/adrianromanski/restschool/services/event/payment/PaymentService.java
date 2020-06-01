package adrianromanski.restschool.services.event.payment;

import adrianromanski.restschool.model.event.PaymentDTO;

import java.util.List;

public interface PaymentService {

    // GET
    List<PaymentDTO> getAllPayments();

    PaymentDTO getPaymentById(Long id);

    PaymentDTO getPaymentByName(String name);

    // POST
    PaymentDTO createNewPayment(PaymentDTO paymentDTO);

    // PUT
    PaymentDTO updatePayment(Long id, PaymentDTO paymentDTO);

    // DELETE
    void deletePaymentById(Long id);
}
