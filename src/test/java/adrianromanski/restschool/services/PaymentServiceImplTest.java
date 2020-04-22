package adrianromanski.restschool.services;

import adrianromanski.restschool.domain.base_entity.event.Payment;
import adrianromanski.restschool.mapper.event.PaymentMapper;
import adrianromanski.restschool.model.base_entity.event.PaymentDTO;
import adrianromanski.restschool.repositories.event.PaymentRepository;
import adrianromanski.restschool.services.event.payment.PaymentService;
import adrianromanski.restschool.services.event.payment.PaymentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PaymentServiceImplTest {

    public static final String NAME = "Salary for March";
    public static final double AMOUNT = 2445.20;
    public static final long ID = 1L;
    public static final LocalDate DATE = LocalDate.now();
    PaymentService paymentService;

    @Mock
    PaymentRepository paymentRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        paymentService = new PaymentServiceImpl(paymentRepository, PaymentMapper.INSTANCE);
    }

    Payment initPayment() {
        Payment payment = new Payment();
        payment.setName(NAME);
        payment.setDate(DATE);
        payment.setAmount(AMOUNT);
        payment.setId(ID);
        return payment;
    }


    PaymentDTO initPaymentDTO() {
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setName(NAME);
        paymentDTO.setDate(DATE);
        paymentDTO.setAmount(AMOUNT);
        paymentDTO.setId(ID);
        return paymentDTO;
    }

    @Test
    void getAllPayments() {
        List<Payment> payments = Arrays.asList(new Payment(), new Payment(), new Payment());

        when(paymentRepository.findAll()).thenReturn(payments);

        List<PaymentDTO> paymentsDTO = paymentService.getAllPayments();

        assertEquals(paymentsDTO.size(),payments.size());
    }

    @Test
    void getPaymentById() {
        Payment payment = initPayment();

        when(paymentRepository.findById(payment.getId())).thenReturn(Optional.of(payment));

        PaymentDTO paymentDTO = paymentService.getPaymentById(ID);

        assertEquals(paymentDTO.getName(), NAME);
        assertEquals(paymentDTO.getId(), ID);
        assertEquals(paymentDTO.getDate(), DATE);
        assertEquals(paymentDTO.getAmount(), AMOUNT);

    }

    @Test
    void getPaymentByName() {
        Payment payment = initPayment();

        when(paymentRepository.findPaymentByName(NAME)).thenReturn(Optional.of(payment));

        PaymentDTO paymentDTO = paymentService.getPaymentByName(NAME);

        assertEquals(paymentDTO.getName(), NAME);
        assertEquals(paymentDTO.getId(), ID);
        assertEquals(paymentDTO.getDate(), DATE);
        assertEquals(paymentDTO.getAmount(), AMOUNT);
    }

    @Test
    void createNewPayment() {
        PaymentDTO paymentDTO = initPaymentDTO();

        Payment savedPayment = initPayment();

        when(paymentRepository.save(any(Payment.class))).thenReturn(savedPayment);

        PaymentDTO returnPaymentDTO = paymentService.createNewPayment(paymentDTO);

        assertEquals(savedPayment.getAmount(), returnPaymentDTO.getAmount());
        assertEquals(savedPayment.getDate(), returnPaymentDTO.getDate());
        assertEquals(savedPayment.getId(), returnPaymentDTO.getId());
        assertEquals(savedPayment.getName(), returnPaymentDTO.getName());
    }

    @Test
    void updatePayment() {
        PaymentDTO paymentDTO = initPaymentDTO();

        Payment savedPayment = initPayment();

        when(paymentRepository.save(any(Payment.class))).thenReturn(savedPayment);

        PaymentDTO returnPaymentDTO = paymentService.updatePayment(paymentDTO.getId(), paymentDTO);

        assertEquals(savedPayment.getAmount(), returnPaymentDTO.getAmount());
        assertEquals(savedPayment.getDate(), returnPaymentDTO.getDate());
        assertEquals(savedPayment.getId(), returnPaymentDTO.getId());
        assertEquals(savedPayment.getName(), returnPaymentDTO.getName());
    }

    @Test
    void deletePaymentById() {
        paymentRepository.deleteById(ID);
        verify(paymentRepository, times(1)).deleteById(ID);
    }
}