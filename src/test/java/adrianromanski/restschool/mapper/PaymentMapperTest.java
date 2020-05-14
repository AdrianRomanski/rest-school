package adrianromanski.restschool.mapper;

import adrianromanski.restschool.domain.event.Payment;
import adrianromanski.restschool.domain.person.Teacher;
import adrianromanski.restschool.mapper.event.PaymentMapper;
import adrianromanski.restschool.model.event.PaymentDTO;
import adrianromanski.restschool.model.person.TeacherDTO;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PaymentMapperTest {

    public static final String WALTER = "Walter";
    public static final String WHITE = "White";
    public static final double AMOUNT = 1000.42;
    public static final LocalDate DATE = LocalDate.now();
    public static final long ID = 1L;
    public static final String SALARY = "Salary for April";

    PaymentMapper paymentMapper = PaymentMapper.INSTANCE;

    @Test
    void paymentToPaymentDTO() {
        Payment payment = new Payment();
        payment.setAmount(AMOUNT);
        payment.setDate(DATE);
        payment.setId(ID);
        payment.setName(SALARY);

        Teacher teacher = new Teacher();
        teacher.setFirstName(WALTER);
        teacher.setLastName(WHITE);
        teacher.setId(ID);

        payment.setTeacher(teacher);
        teacher.getPayments().add(payment);

        PaymentDTO paymentDTO = paymentMapper.paymentToPaymentDTO(payment);

        // Checking payment
        assertEquals(paymentDTO.getAmount(), AMOUNT);
        assertEquals(paymentDTO.getDate(), DATE);
        assertEquals(paymentDTO.getId(), ID);
        assertEquals(paymentDTO.getName(), SALARY);

        // Checking teacher
        assertEquals(paymentDTO.getTeacherDTO().getFirstName(), WALTER);
        assertEquals(paymentDTO.getTeacherDTO().getLastName(), WHITE);

        }

    @Test
    void paymentDTOToPayment() {
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setAmount(AMOUNT);
        paymentDTO.setDate(DATE);
        paymentDTO.setId(ID);
        paymentDTO.setName(SALARY);

        TeacherDTO teacherDTO = new TeacherDTO();
        teacherDTO.setFirstName(WALTER);
        teacherDTO.setLastName(WHITE);
        teacherDTO.setId(ID);

        paymentDTO.setTeacherDTO(teacherDTO);
        teacherDTO.getPaymentsDTO().add(paymentDTO);

        Payment payment = paymentMapper.paymentDTOToPayment(paymentDTO);

        // Checking payment
        assertEquals(payment.getAmount(), AMOUNT);
        assertEquals(payment.getDate(), DATE);
        assertEquals(payment.getId(), ID);
        assertEquals(payment.getName(), SALARY);

        // Checking teacher
        assertEquals(payment.getTeacher().getFirstName(), WALTER);
        assertEquals(payment.getTeacher().getLastName(), WHITE);
    }
}