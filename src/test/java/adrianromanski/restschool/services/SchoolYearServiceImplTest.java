package adrianromanski.restschool.services;

import adrianromanski.restschool.domain.event.SchoolYear;
import adrianromanski.restschool.exceptions.ResourceNotFoundException;
import adrianromanski.restschool.mapper.event.SchoolYearMapper;
import adrianromanski.restschool.model.event.SchoolYearDTO;
import adrianromanski.restschool.repositories.event.SchoolYearRepository;
import adrianromanski.restschool.services.event.school_year.SchoolYearService;
import adrianromanski.restschool.services.event.school_year.SchoolYearServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class SchoolYearServiceImplTest {

    public static final String NAME = "Year of solitude";
    public static final LocalDate DATE = LocalDate.of(2020, 10, 10);
    @Mock
    SchoolYearRepository schoolYearRepository;

    SchoolYearService schoolYearService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        schoolYearService = new SchoolYearServiceImpl(schoolYearRepository, SchoolYearMapper.INSTANCE);
    }

    private SchoolYear getSchoolYear() { return SchoolYear.builder().name(NAME).date(DATE).build(); }
    private SchoolYearDTO getSchoolYearDTO() { return SchoolYearDTO.builder().name(NAME).date(DATE).build(); }


    @DisplayName("[Happy Path], [Method] = getByID")
    @Test
    void getSchoolYearByIDHappyPath() {
        SchoolYear schoolYear = getSchoolYear();

        when(schoolYearRepository.findById(anyLong())).thenReturn(Optional.of(schoolYear));

        SchoolYearDTO returnDTO = schoolYearService.getSchoolYearByID(1L);

        assertEquals(returnDTO.getName(), NAME);
        assertEquals(returnDTO.getDate(), DATE);
    }


    @DisplayName("[UnHappy Path], [Method] = getByID")
    @Test
    void getSchoolYearByIDUnHappyPath() {
        Throwable ex = catchThrowable(() -> schoolYearService.getSchoolYearByID(1L));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }


    @DisplayName("[Happy Path], [Method] = createSchoolYear")
    @Test
    void createSchoolYearHappyPath() {
        SchoolYearDTO schoolYearDTO = getSchoolYearDTO();

        SchoolYearDTO returnDTO = schoolYearService.createSchoolYear(schoolYearDTO);

        assertEquals(returnDTO.getName(), NAME);
        assertEquals(returnDTO.getDate(), DATE);
    }


    @DisplayName("[Happy Path], [Method] = updateSchoolYear")
    @Test
    void updateSchoolYearHappyPath() {
        SchoolYear schoolYear = getSchoolYear();
        SchoolYearDTO schoolYearDTO = getSchoolYearDTO();

        when(schoolYearRepository.findById(anyLong())).thenReturn(Optional.of(schoolYear));

        SchoolYearDTO returnDTO = schoolYearService.updateSchoolYear(1L, schoolYearDTO);

        assertEquals(returnDTO.getName(), NAME);
        assertEquals(returnDTO.getDate(), DATE);
    }


    @DisplayName("[UnHappy Path], [Method] = updateSchoolYear")
    @Test
    void updateSchoolYearUnHappyPath() {
        SchoolYearDTO schoolYearDTO = getSchoolYearDTO();

        Throwable ex = catchThrowable(() -> schoolYearService.updateSchoolYear(1L, schoolYearDTO));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }


    @DisplayName("[Happy Path], [Method] = deleteSchoolYear")
    @Test
    void deleteSchoolYearHappyPath() {
        SchoolYear schoolYear = getSchoolYear();

        when(schoolYearRepository.findById(anyLong())).thenReturn(Optional.of(schoolYear));

        schoolYearService.deleteSchoolYear(1L);

         verify(schoolYearRepository, times(1)).deleteById(anyLong());
    }


    @DisplayName("[UnHappy Path], [Method] = deleteSchoolYear")
    @Test
    void deleteSchoolYearUnHappyPath() {
        Throwable ex = catchThrowable(() -> schoolYearService.deleteSchoolYear(222L));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }
}