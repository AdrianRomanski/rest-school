package adrianromanski.restschool.controllers;

import adrianromanski.restschool.controllers.exception_handler.RestResponseEntityExceptionHandler;
import adrianromanski.restschool.controllers.person.DirectorController;
import adrianromanski.restschool.exceptions.ResourceNotFoundException;
import adrianromanski.restschool.model.event.PaymentDTO;
import adrianromanski.restschool.model.person.DirectorDTO;
import adrianromanski.restschool.services.person.director.DirectorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;

import static adrianromanski.restschool.controllers.AbstractRestControllerTest.asJsonString;
import static adrianromanski.restschool.domain.enums.LastName.COOPER;
import static adrianromanski.restschool.domain.enums.MaleName.ETHAN;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DirectorControllerTest {

    public static final double BUDGET = 2241.2;
    public static final LocalDate DATE = LocalDate.now();
    public static final double AMOUNT = 223.40;
    public static final String NAME = "First Money";
    public static final String DIRECTOR = "/director/";
    @Mock
    DirectorService directorService;

    @InjectMocks
    DirectorController directorController;

    MockMvc mockMvc;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(directorController)
                                    .setControllerAdvice(RestResponseEntityExceptionHandler.class).build();
    }

    private DirectorDTO getDirectorDTO() { return DirectorDTO.builder().firstName(ETHAN.get()).lastName(COOPER.get()).budget(BUDGET).build(); }


    @DisplayName("[GET], [Happy Path], [Method] = getDirector")
    @Test
    void getDirector() throws Exception {
        DirectorDTO directorDTO = getDirectorDTO();

        when(directorService.getDirector()).thenReturn(directorDTO);

        mockMvc.perform(get(DIRECTOR)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(directorDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo(ETHAN.get())))
                .andExpect(jsonPath("$.lastName", equalTo(COOPER.get())))
                .andExpect(jsonPath("$.budget", equalTo(BUDGET)));
    }


    @DisplayName("[POST], [Happy Path], [Method] = addPaymentToTeacher")
    @Test
    void addPaymentToTeacher() throws Exception {
        PaymentDTO paymentDTO = PaymentDTO.builder().name(NAME).amount(AMOUNT).build();

        when(directorService.addPaymentToTeacher(anyLong(), any(PaymentDTO.class))).thenReturn(paymentDTO);

        mockMvc.perform(post(DIRECTOR + "addPayment/teacher-1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(paymentDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo(NAME)))
                .andExpect(jsonPath("$.amount", equalTo(AMOUNT)));
    }


    @DisplayName("[PUT], [Happy Path], [Method] = updateDirector")
    @Test
    void updateDirector() throws Exception {
        DirectorDTO directorDTO = getDirectorDTO();

        when(directorService.updateDirector(anyLong(), any(DirectorDTO.class))).thenReturn(directorDTO);

        mockMvc.perform(put(DIRECTOR + "update/director-1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(directorDTO)))
                .andExpect(status().isOk());
    }


    @DisplayName("[DELETE], [Happy Path], [Method] = deleteDirector")
    @Test
    void deleteDirectorByID() throws Exception {
        mockMvc.perform(delete(DIRECTOR + "director-1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(directorService).deleteDirectorByID(1L);
    }


    @DisplayName("[GET, PUT, DELETE], [Unhappy Path]")
    @Test
    public void testNotFoundException() throws Exception {
        DirectorDTO directorDTO = getDirectorDTO();

        when(directorService.updateDirector(anyLong(), any(DirectorDTO.class))).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(put(DIRECTOR + "update/director-1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(directorDTO)))
                .andExpect(status().isNotFound());
    }
}