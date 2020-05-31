package adrianromanski.restschool.services.event.payment;

import adrianromanski.restschool.domain.event.Payment;
import adrianromanski.restschool.exceptions.ResourceNotFoundException;
import adrianromanski.restschool.mapper.event.PaymentMapper;
import adrianromanski.restschool.model.event.PaymentDTO;
import adrianromanski.restschool.repositories.event.PaymentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PaymentServiceImpl implements PaymentService {

    private  final PaymentRepository paymentRepository;
    private  final PaymentMapper paymentMapper;

    public PaymentServiceImpl(PaymentRepository paymentRepository, PaymentMapper paymentMapper) {
        this.paymentRepository = paymentRepository;
        this.paymentMapper = paymentMapper;
    }


    /**
     * @return all Payments
     */
    @Override
    public List<PaymentDTO> getAllPayments() {
        return paymentRepository.findAll()
                .stream()
                .map(paymentMapper::paymentToPaymentDTO)
                .collect(Collectors.toList());
    }


    /**
     * @param id of the Payment to be found
     * @return Payment with matching id
     * @throws ResourceNotFoundException if not found
     */
    @Override
    public PaymentDTO getPaymentById(Long id) {
        return paymentRepository
                .findById(id)
                .map(paymentMapper::paymentToPaymentDTO)
                .orElseThrow(() -> new ResourceNotFoundException(id, Payment.class));
    }


    /**
     * @param name of the Payment to be found
     * @return Payment with matching name
     * @throws ResourceNotFoundException if not found
     */
    @Override
    public PaymentDTO getPaymentByName(String name) {
        return paymentRepository
                .findPaymentByName(name)
                .map(paymentMapper::paymentToPaymentDTO)
                .orElseThrow(() -> new ResourceNotFoundException(name, Payment.class));
    }


    /**
     * @param paymentDTO
     * Save Payment to Database
     * @return Payment after saving it to database
     */
    @Override
    public PaymentDTO createNewPayment(PaymentDTO paymentDTO) {
        paymentRepository.save(paymentMapper.paymentDTOToPayment(paymentDTO));
        log.info("Payment with id: " + paymentDTO.getId() + "successfully saved to database");
        return paymentDTO;

    }


    /**
     * @param id of Payment to be updated
     * @param paymentDTO body to save
     * @return Updated Payment if successfully saved
     * @throws ResourceNotFoundException if not found
     */
    @Override
    public PaymentDTO updatePayment(Long id, PaymentDTO paymentDTO) {
        paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id, Payment.class));
        Payment updatedPayment = paymentMapper.paymentDTOToPayment(paymentDTO);
        updatedPayment.setId(id);
        paymentRepository.save(updatedPayment);
        log.info("Payment with id: " + id + "successfully updated");
        return paymentMapper.paymentToPaymentDTO(updatedPayment);
    }



    /**
     * @param id of Payment to be deleted
     * @throws ResourceNotFoundException if not found
     */
    @Override
    public void deletePaymentById(Long id) {
        paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id, Payment.class));
        paymentRepository.deleteById(id);
        log.info("Payment with id: " + id + "successfully deleted");
    }
}
