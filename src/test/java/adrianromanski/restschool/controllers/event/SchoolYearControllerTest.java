package adrianromanski.restschool.controllers.event;

import adrianromanski.restschool.controllers.exception_handler.RestResponseEntityExceptionHandler;
import adrianromanski.restschool.exceptions.ResourceNotFoundException;
import adrianromanski.restschool.model.event.SchoolYearDTO;
import adrianromanski.restschool.model.person.DirectorDTO;
import adrianromanski.restschool.services.event.school_year.SchoolYearService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;

import static adrianromanski.restschool.controllers.AbstractRestControllerTest.asJsonString;
import static adrianromanski.restschool.domain.enums.LastName.COOPER;
import static adrianromanski.restschool.domain.enums.MaleName.ETHAN;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SchoolYearControllerTest {

    public static final String NAME = "Shinny Hornets";
    public static final String SCHOOL_YEARS = "/schoolYears/";

    @Mock
    SchoolYearService schoolYearService;

    @InjectMocks
    SchoolYearController schoolYearController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(schoolYearController)
                                .setControllerAdvice(RestResponseEntityExceptionHandler.class).build();
    }

    private SchoolYearDTO getSchoolYearDTO() { return SchoolYearDTO.builder().name(NAME).build(); }


    @DisplayName("[GET], [Happy Path], [Method] = getDirector")
    @Test
    void getSchoolYearByID() throws Exception {
        SchoolYearDTO schoolYearDTO  = getSchoolYearDTO();

        when(schoolYearService.getSchoolYearByID(anyLong())).thenReturn(schoolYearDTO);

        mockMvc.perform(get(SCHOOL_YEARS + "ID-1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(schoolYearDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(NAME)));
    }


    @DisplayName("[POST], [Happy Path], [Method] = createNewSchoolYear")
    @Test
    void createSchoolYear() throws Exception {
        SchoolYearDTO schoolYearDTO  = getSchoolYearDTO();

        mockMvc.perform(post(SCHOOL_YEARS)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(schoolYearDTO)))
                .andExpect(status().isCreated());
    }


    @DisplayName("[PUT], [Happy Path], [Method] = updateSchoolYear")
    @Test
    void updateSchoolYear() throws Exception {
        SchoolYearDTO schoolYearDTO  = getSchoolYearDTO();

        mockMvc.perform(put(SCHOOL_YEARS + "ID-1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(schoolYearDTO)))
                .andExpect(status().isOk());
    }


    @DisplayName("[DELETE], [Happy Path], [Method] = deleteSchoolYear")
    @Test
    void deleteSchoolYear() throws Exception {
        mockMvc.perform(delete(SCHOOL_YEARS + "ID-1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    @DisplayName("[GET, PUT, DELETE], [Unhappy Path]")
    @Test
    public void testNotFoundException() throws Exception {
        SchoolYearDTO schoolYearDTO = getSchoolYearDTO();

        when(schoolYearService.updateSchoolYear(anyLong(), any(SchoolYearDTO.class))).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(put(SCHOOL_YEARS + "id-1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(schoolYearDTO)))
                .andExpect(status().isNotFound());
    }
}