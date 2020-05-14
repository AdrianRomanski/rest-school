package adrianromanski.restschool.controllers;

import adrianromanski.restschool.controllers.event.PaymentController;
import adrianromanski.restschool.controllers.exception_handler.RestResponseEntityExceptionHandler;
import adrianromanski.restschool.exceptions.ResourceNotFoundException;
import adrianromanski.restschool.model.event.PaymentDTO;
import adrianromanski.restschool.services.event.payment.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.Arrays;
import java.util.List;


import static adrianromanski.restschool.controllers.AbstractRestControllerTest.asJsonString;
import static org.hamcrest.Matchers.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class PaymentControllerTest {

    public static final String PAYMENTS = "/payments/";
    public static final double AMOUNT = 2240.21;
    public static final String NAME = "Salary for December";
    public static final long ID = 1L;

    @Mock
    PaymentService paymentService;

    @InjectMocks
    PaymentController paymentController;

    MockMvc mockMvc;

    PaymentDTO initPaymentDTO() {
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setAmount(AMOUNT);
        paymentDTO.setName(NAME);
        paymentDTO.setId(ID);
        return paymentDTO;
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(paymentController)
                                    .setControllerAdvice(RestResponseEntityExceptionHandler.class)
                                    .build();
    }

    @Test
    void getAllPayments() throws Exception {
        List<PaymentDTO> paymentDTOList = Arrays.asList(new PaymentDTO(), new PaymentDTO(), new PaymentDTO());

        when(paymentService.getAllPayments()).thenReturn(paymentDTOList);

        mockMvc.perform(get(PAYMENTS)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(paymentDTOList)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.paymentDTOList", hasSize(3)));

    }

    @Test
    void getPaymentById() throws Exception {
        PaymentDTO paymentDTO = initPaymentDTO();

        when(paymentService.getPaymentById(ID)).thenReturn(paymentDTO);

        mockMvc.perform(get(PAYMENTS + "id-1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(paymentDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(NAME)))
                .andExpect(jsonPath("$.amount", equalTo(AMOUNT)));
    }

    @Test
    void getPaymentByName() throws Exception {
        PaymentDTO paymentDTO = initPaymentDTO();

        when(paymentService.getPaymentByName(NAME)).thenReturn(paymentDTO);

        mockMvc.perform(get(PAYMENTS + "name-" + NAME)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(paymentDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(NAME)))
                .andExpect(jsonPath("$.amount", equalTo(AMOUNT)));
    }

    @Test
    void createNewPayment() throws Exception {
        PaymentDTO paymentDTO = initPaymentDTO();

        PaymentDTO returnDTO = new PaymentDTO();
        returnDTO.setName(paymentDTO.getName());
        returnDTO.setAmount(paymentDTO.getAmount());

        when(paymentService.createNewPayment(any(PaymentDTO.class))).thenReturn(returnDTO);

        mockMvc.perform(post(PAYMENTS)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(paymentDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo(NAME)))
                .andExpect(jsonPath("$.amount", equalTo(AMOUNT)));
    }

    @Test
    void updatePayment() throws Exception {
        PaymentDTO paymentDTO = initPaymentDTO();

        PaymentDTO returnDTO = new PaymentDTO();
        returnDTO.setName(paymentDTO.getName());
        returnDTO.setAmount(paymentDTO.getAmount());


        when(paymentService.updatePayment(ID, paymentDTO)).thenReturn(returnDTO);

        mockMvc.perform(put(PAYMENTS + 1)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(paymentDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(NAME)))
                .andExpect(jsonPath("$.amount", equalTo(AMOUNT)));
    }


    @Test
    void deletePaymentById() throws Exception {
        mockMvc.perform(delete("/payments/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(paymentService).deletePaymentById(ID);
    }

    @Test
    public void testNotFoundException() throws Exception {

        when(paymentService.getPaymentById(anyLong())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(PAYMENTS + "id-" + 222)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}