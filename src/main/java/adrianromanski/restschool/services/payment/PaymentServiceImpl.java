package adrianromanski.restschool.services.payment;

import adrianromanski.restschool.domain.base_entity.event.Payment;
import adrianromanski.restschool.exceptions.ResourceNotFoundException;
import adrianromanski.restschool.mapper.PaymentMapper;
import adrianromanski.restschool.model.base_entity.event.PaymentDTO;
import adrianromanski.restschool.repositories.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    public PaymentServiceImpl(PaymentRepository paymentRepository, PaymentMapper paymentMapper) {
        this.paymentRepository = paymentRepository;
        this.paymentMapper = paymentMapper;
    }

    @Override
    public List<PaymentDTO> getAllPayments() {
        return paymentRepository.findAll()
                                    .stream()
                                    .map(paymentMapper::paymentToPaymentDTO)
                                    .collect(Collectors.toList());

    }

    @Override
    public PaymentDTO getPaymentById(Long id) {
        return paymentMapper.paymentToPaymentDTO(paymentRepository.findById(id)
                                                                .orElseThrow(ResourceNotFoundException::new));
    }

    @Override
    public PaymentDTO getPaymentByName(String name) {
        return paymentMapper.paymentToPaymentDTO(paymentRepository.findPaymentByName(name)
                                                                .orElseThrow(ResourceNotFoundException::new));
    }

    @Override
    public PaymentDTO createNewPayment(PaymentDTO paymentDTO) {
        return saveAndReturnDTO(paymentMapper.paymentDTOToPayment(paymentDTO));
    }

    @Override
    public PaymentDTO updatePayment(Long id, PaymentDTO paymentDTO) {
        paymentDTO.setId(id);
        return saveAndReturnDTO(paymentMapper.paymentDTOToPayment(paymentDTO));
    }

    @Override
    public void deletePaymentById(Long id) {
        paymentRepository.deleteById(id);
    }

    public PaymentDTO saveAndReturnDTO(Payment payment) {
        paymentRepository.save(payment);
        return paymentMapper.paymentToPaymentDTO(payment);
    }
}
