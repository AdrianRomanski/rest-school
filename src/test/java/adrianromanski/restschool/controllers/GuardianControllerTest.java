package adrianromanski.restschool.controllers;

import adrianromanski.restschool.controllers.exception_handler.RestResponseEntityExceptionHandler;
import adrianromanski.restschool.controllers.person.GuardianController;
import adrianromanski.restschool.exceptions.ResourceNotFoundException;
import adrianromanski.restschool.model.base_entity.address.GuardianAddressDTO;
import adrianromanski.restschool.model.person.GuardianDTO;
import adrianromanski.restschool.model.person.StudentDTO;
import adrianromanski.restschool.services.person.guardian.GuardianService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;

import static adrianromanski.restschool.controllers.AbstractRestControllerTest.asJsonString;
import static adrianromanski.restschool.domain.enums.LastName.COOPER;
import static adrianromanski.restschool.domain.enums.LastName.HENDERSON;
import static adrianromanski.restschool.domain.enums.MaleName.ETHAN;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class GuardianControllerTest {


    public static final long ID = 1L;
    public static final String GUARDIANS = "/guardians/";
    public static final String COUNTRY = "Poland";
    public static final String CITY = "Warsaw";
    public static final String POSTAL_CODE = "22-44";
    public static final String STREET_NAME = "Sesame";

    MockMvc mockMvc;

    @Mock
    GuardianService guardianService;

    @InjectMocks
    GuardianController guardianController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(guardianController)
                                        .setControllerAdvice(RestResponseEntityExceptionHandler.class)
                                        .build();
    }

    private GuardianDTO createEthan() { return GuardianDTO.builder().firstName(ETHAN.get()).lastName(HENDERSON.get()).build(); }

    private List<GuardianDTO> getGuardians() { return Arrays.asList(createEthan(), createEthan(), createEthan()); }

    private GuardianAddressDTO getAddressDTO() { return GuardianAddressDTO.builder().country(COUNTRY).city(CITY).postalCode(POSTAL_CODE).streetName(STREET_NAME).build(); }



    @DisplayName("[GET], [Happy Path], [Method] = getAllGuardians")
    @Test
    void getAllGuardians() throws Exception {
        List<GuardianDTO> guardianDTOList = getGuardians();

        when(guardianService.getAllGuardians()).thenReturn(guardianDTOList);

        mockMvc.perform(get(GUARDIANS)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(guardianDTOList)))
                .andExpect(jsonPath("$.guardianDTOList", hasSize(3)));
    }


    @DisplayName("[GET], [Happy Path], [Method] = getGuardianByID")
    @Test
    void getGuardianByID() throws Exception {
        GuardianDTO guardianDTO = createEthan();

        when(guardianService.getGuardianByID(ID)).thenReturn(guardianDTO);

        mockMvc.perform(get(GUARDIANS+ "getById/guardian-1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(guardianDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo(ETHAN.get())))
                .andExpect(jsonPath("$.lastName", equalTo(HENDERSON.get())));
    }


    @DisplayName("[GET], [Happy Path], [Method] = getGuardianByFirstAndLastName")
    @Test
    void getGuardianByFirstAndLastName() throws Exception {
        GuardianDTO guardianDTO = createEthan();

        when(guardianService.getGuardianByFirstAndLastName(anyString(), anyString())).thenReturn(guardianDTO);

        mockMvc.perform(get(GUARDIANS + "getByName/" + ETHAN.get() + "-" + COOPER.get())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(guardianDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo(ETHAN.get())))
                .andExpect(jsonPath("$.lastName", equalTo(HENDERSON.get())));
    }


    @DisplayName("[GET], [Happy Path], [Method] = getGuardiansByAge")
    @Test
    void getGuardiansByAge() throws Exception {
        Map<Long, List<GuardianDTO>> map = new HashMap<>();

        map.putIfAbsent(27L, Arrays.asList(createEthan(), createEthan()));
        map.putIfAbsent(24L, Collections.singletonList(createEthan()));
        map.putIfAbsent(22L, getGuardians());

        when(guardianService.getGuardiansByAge()).thenReturn(map);

        mockMvc.perform(get(GUARDIANS + "groupedBy/age")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(map)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.27", hasSize(2)))
                .andExpect(jsonPath("$.24", hasSize(1)))
                .andExpect(jsonPath("$.22", hasSize(3)));
    }


    @DisplayName("[GET], [Happy Path], [Method] = getAllStudentsForGuardian")
    @Test
    void getStudentsForGuardian() throws Exception {
        List<StudentDTO> studentDTOS = Arrays.asList(new StudentDTO(), new StudentDTO());

        when(guardianService.getAllStudentsForGuardian(anyLong())).thenReturn(studentDTOS);

        mockMvc.perform(get(GUARDIANS + "getStudents/guardian-1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(studentDTOS)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.students", hasSize(2)));
    }


    @DisplayName("[POST], [Happy Path], [Method] = createNewGuardian")
    @Test
    void createNewGuardian() throws Exception {
        GuardianDTO guardianDTO = createEthan();

        when(guardianService.createNewGuardian(any(GuardianDTO.class))).thenReturn(guardianDTO);

        mockMvc.perform(post(GUARDIANS)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(guardianDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", equalTo(ETHAN.get())))
                .andExpect(jsonPath("$.lastName", equalTo(HENDERSON.get())));
    }


    @DisplayName("[POST], [Happy Path], [Method] = addAddress")
    @Test
    void addAddress() throws Exception {
        GuardianAddressDTO addressDTO = getAddressDTO();

        when(guardianService.addAddress(anyLong(), any(GuardianAddressDTO.class))).thenReturn(addressDTO);

        mockMvc.perform(post(GUARDIANS + "addAddress/guardian-1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(addressDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.country", equalTo(COUNTRY)))
                .andExpect(jsonPath("$.city", equalTo(CITY)))
                .andExpect(jsonPath("$.postalCode", equalTo(POSTAL_CODE)))
                .andExpect(jsonPath("$.streetName", equalTo(STREET_NAME)));
    }


    @DisplayName("[PUT], [Happy Path], [Method] = updateGuardian")
    @Test
    void updateGuardian() throws Exception {

        GuardianDTO guardianDTO = createEthan();


        when(guardianService.updateGuardian(any(GuardianDTO.class), anyLong())).thenReturn(guardianDTO);

        mockMvc.perform(put(GUARDIANS + "updateGuardian/guardian-1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(guardianDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo(ETHAN.get())))
                .andExpect(jsonPath("$.lastName", equalTo(HENDERSON.get())));
    }


    @DisplayName("[PUT], [Happy Path], [Method] = updateAddress")
    @Test
    void updateAddress() throws Exception {
        GuardianAddressDTO addressDTO = getAddressDTO();

        when(guardianService.updateAddress(anyLong(), any(GuardianAddressDTO.class))).thenReturn(addressDTO);

        mockMvc.perform(put(GUARDIANS + "updateAddress/guardian-1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(addressDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.country", equalTo(COUNTRY)))
                .andExpect(jsonPath("$.city", equalTo(CITY)))
                .andExpect(jsonPath("$.postalCode", equalTo(POSTAL_CODE)))
                .andExpect(jsonPath("$.streetName", equalTo(STREET_NAME)));
    }


    @DisplayName("[DELETE], [Happy Path], [Method] = deleteGuardianByID")
    @Test
    void deleteGuardian() throws Exception {
        mockMvc.perform(delete(GUARDIANS + "deleteGuardian/guardian-1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(guardianService).deleteGuardianByID(ID);
    }


    @DisplayName("[GET, PUT, DELETE], [Unhappy Path], [Reason] = Guardian with id 222 not found")
    @Test
    public void testNotFoundException() throws Exception {

        when(guardianService.getGuardianByID(anyLong())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(GUARDIANS + "id-222")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}