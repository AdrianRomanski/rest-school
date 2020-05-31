package adrianromanski.restschool.services;

import adrianromanski.restschool.domain.event.Payment;
import adrianromanski.restschool.exceptions.ResourceNotFoundException;
import adrianromanski.restschool.mapper.event.PaymentMapper;
import adrianromanski.restschool.model.event.PaymentDTO;
import adrianromanski.restschool.repositories.event.PaymentRepository;
import adrianromanski.restschool.services.event.payment.PaymentService;
import adrianromanski.restschool.services.event.payment.PaymentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertEquals;
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

    Payment createPayment() {
        Payment payment = Payment.builder().name(NAME).date(DATE).amount(AMOUNT).build();
        payment.setId(ID);
        return payment;
    }

    PaymentDTO createPaymentDTO() {
        PaymentDTO paymentDTO = PaymentDTO.builder().name(NAME).date(DATE).amount(AMOUNT).build();
        paymentDTO.setId(ID);
        return paymentDTO;
    }

    @DisplayName("[Happy Path], [Method] = getAllPayments")
    @Test
    void getAllPayments() {
        List<Payment> payments = Arrays.asList(new Payment(), new Payment(), new Payment());

        when(paymentRepository.findAll()).thenReturn(payments);

        List<PaymentDTO> paymentsDTO = paymentService.getAllPayments();

        assertEquals(paymentsDTO.size(),payments.size());
    }


    @DisplayName("[Happy Path], [Method] = getPaymentById")
    @Test
    void getPaymentByIdHappyPath() {
        Payment payment = createPayment();

        when(paymentRepository.findById(payment.getId())).thenReturn(Optional.of(payment));

        PaymentDTO paymentDTO = paymentService.getPaymentById(ID);

        assertEquals(paymentDTO.getName(), NAME);
        assertEquals(paymentDTO.getId(), ID);
        assertEquals(paymentDTO.getDate(), DATE);
        assertEquals(paymentDTO.getAmount(), AMOUNT);
    }


    @DisplayName("[Unhappy Path], [Method] = getPaymentById")
    @Test
    void getPaymentByIdUnHappyPath() {
        Throwable ex = catchThrowable(() -> paymentService.getPaymentById(222L));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }


    @DisplayName("[Happy Path], [Method] = getPaymentByName")
    @Test
    void getPaymentByNameHappyPath() {
        Payment payment = createPayment();

        when(paymentRepository.findPaymentByName(NAME)).thenReturn(Optional.of(payment));

        PaymentDTO paymentDTO = paymentService.getPaymentByName(NAME);

        assertEquals(paymentDTO.getName(), NAME);
        assertEquals(paymentDTO.getId(), ID);
        assertEquals(paymentDTO.getDate(), DATE);
        assertEquals(paymentDTO.getAmount(), AMOUNT);
    }


    @DisplayName("[Unhappy Path], [Method] = getPaymentByName")
    @Test
    void getPaymentByNameUnHappyPath() {
        Throwable ex = catchThrowable(() -> paymentService.getPaymentByName("Banana"));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }


    @DisplayName("[Happy Path], [Method] = createNewPayment")
    @Test
    void createNewPayment() {
        PaymentDTO paymentDTO = createPaymentDTO();
        Payment payment = createPayment();

        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

        PaymentDTO returnDTO = paymentService.createNewPayment(paymentDTO);

        assertEquals(returnDTO.getAmount(), payment.getAmount());
        assertEquals(returnDTO.getDate(), payment.getDate());
        assertEquals(returnDTO.getId(), payment.getId());
        assertEquals(returnDTO.getName(), payment.getName());
    }


    @DisplayName("[Happy Path], [Method] = updatePayment")
    @Test
    void updatePaymentHappyPath() {
        PaymentDTO paymentDTO = createPaymentDTO();
        Payment payment = createPayment();

        when(paymentRepository.findById(anyLong())).thenReturn(Optional.of(payment));

        PaymentDTO returnDTO = paymentService.updatePayment(paymentDTO.getId(), paymentDTO);

        assertEquals(returnDTO.getAmount(), payment.getAmount());
        assertEquals(returnDTO.getDate(), payment.getDate());
        assertEquals(returnDTO.getId(), payment.getId());
        assertEquals(returnDTO.getName(), payment.getName());
    }


    @DisplayName("[Unhappy Path], [Method] = updatePayment")
    @Test
    void updatePaymentUnHappyPath() {
        PaymentDTO paymentDTO = createPaymentDTO();

        Throwable ex = catchThrowable(() -> paymentService.updatePayment(1L, paymentDTO));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }

    @DisplayName("[Happy Path], [Method] = deletePaymentById")
    @Test
    void deletePaymentByIdHappyPath() {
        Payment payment = createPayment();

        when(paymentRepository.findById(anyLong())).thenReturn(Optional.of(payment));

        paymentService.deletePaymentById(ID);

        verify(paymentRepository, times(1)).deleteById(ID);
    }

    @DisplayName("[Unhappy Path], [Method] = deletePaymentById")
    @Test
    void deletePaymentByIdUnHappyPath() {
        Throwable ex = catchThrowable(() -> paymentService.deletePaymentById(1L));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }


}