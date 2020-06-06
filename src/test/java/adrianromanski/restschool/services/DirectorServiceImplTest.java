package adrianromanski.restschool.services;

import adrianromanski.restschool.domain.event.Payment;
import adrianromanski.restschool.domain.person.Director;
import adrianromanski.restschool.domain.person.Teacher;
import adrianromanski.restschool.exceptions.ResourceNotFoundException;
import adrianromanski.restschool.mapper.event.PaymentMapper;
import adrianromanski.restschool.mapper.person.DirectorMapper;
import adrianromanski.restschool.mapper.person.StudentMapper;
import adrianromanski.restschool.mapper.person.TeacherMapper;
import adrianromanski.restschool.model.event.PaymentDTO;
import adrianromanski.restschool.model.person.DirectorDTO;
import adrianromanski.restschool.repositories.event.PaymentRepository;
import adrianromanski.restschool.repositories.person.DirectorRepository;
import adrianromanski.restschool.repositories.person.StudentRepository;
import adrianromanski.restschool.repositories.person.TeacherRepository;
import adrianromanski.restschool.services.person.director.DirectorService;
import adrianromanski.restschool.services.person.director.DirectorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static adrianromanski.restschool.domain.enums.MaleName.ETHAN;
import static adrianromanski.restschool.domain.enums.MaleName.LOGAN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class DirectorServiceImplTest {

    @Mock
    DirectorRepository directorRepository;

    @Mock
    TeacherRepository teacherRepository;

    @Mock
    StudentRepository studentRepository;

    @Mock
    PaymentRepository paymentRepository;

    DirectorService directorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        directorService = new DirectorServiceImpl(directorRepository, teacherRepository, studentRepository, paymentRepository,
                DirectorMapper.INSTANCE, TeacherMapper.INSTANCE, StudentMapper.INSTANCE, PaymentMapper.INSTANCE);
    }

    private Director getDirector() { return Director.builder().firstName(ETHAN.get()).lastName(LOGAN.get()).build(); }

    private DirectorDTO getDirectorDTO() { return DirectorDTO.builder().firstName(ETHAN.get()).lastName(LOGAN.get()).build(); }


    @DisplayName("[Happy Path], [Method] = getDirector")
    @Test
    void getDirectorHappyPath() {
        Director director = getDirector();
        List<Director> list = Collections.singletonList(director);

        when(directorRepository.findAll()).thenReturn(list);

        DirectorDTO returnDTO = directorService.getDirector();

        assertEquals(returnDTO.getFirstName(), ETHAN.get());
        assertEquals(returnDTO.getLastName(), LOGAN.get());
    }


    @DisplayName("[UnHappy Path], [Method] = getDirector")
    @Test
    void getDirectorUnhappyPath() {
        Throwable ex = catchThrowable(() -> directorService.getDirector());

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }


    @DisplayName("[Happy Path], [Method] = addPaymentToTeacher")
    @Test
    void addPaymentForWorkerHappyPath() {
        PaymentDTO paymentDTO = PaymentDTO.builder().amount(2241.20).date(LocalDate.now()).name("Salary for May").build();

        Teacher teacher = Teacher.builder().firstName(ETHAN.get()).lastName(LOGAN.get()).build();

        when(teacherRepository.findById(anyLong())).thenReturn(Optional.of(teacher));

        PaymentDTO returnDTO = directorService.addPaymentToTeacher(1L, paymentDTO);

        assertEquals(returnDTO.getTeacherDTO().getFirstName(), ETHAN.get());
        assertEquals(returnDTO.getTeacherDTO().getLastName(), LOGAN.get());

        verify(teacherRepository, times(1)).save(any(Teacher.class));
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }


    @DisplayName("[UnHappy Path], [Method] = addPaymentToTeacher")
    @Test
    void addPaymentForWorkerUnhappyPath() {
        PaymentDTO paymentDTO = PaymentDTO.builder().amount(2241.20).date(LocalDate.now()).name("Salary for May").build();

        Throwable ex = catchThrowable(() -> directorService.addPaymentToTeacher(223L, paymentDTO));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }


    @DisplayName("[Happy Path], [Method] = updateDirector")
    @Test
    void updateDirectorHappyPath() {
        Director director = getDirector();
        DirectorDTO directorDTO = getDirectorDTO();
        directorDTO.setFirstName("Updating");

        when(directorRepository.findById(anyLong())).thenReturn(Optional.of(director));

        DirectorDTO returnDTO = directorService.updateDirector(1L, directorDTO);

        assertEquals(returnDTO.getLastName(), LOGAN.get());
        assertEquals(returnDTO.getFirstName(), "Updating");

        verify(directorRepository, times(1)).save(any(Director.class));
    }


    @DisplayName("[UnHappy Path], [Method] = updateDirector")
    @Test
    void updateDirectorUnhappyPath() {
        DirectorDTO directorDTO = getDirectorDTO();

        Throwable ex = catchThrowable(() -> directorService.updateDirector(223L, directorDTO));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }


    @DisplayName("[Happy Path], [Method] = deleteDirectorByID")
    @Test
    void deleteDirectorByIDHappyPath() {
        Director director = getDirector();

        when(directorRepository.findById(anyLong())).thenReturn(Optional.of(director));

        directorService.deleteDirectorByID(1L);

        verify(directorRepository, times(1)).deleteById(anyLong());
    }


    @DisplayName("[UnHappy Path], [Method] = deleteDirectorByID")
    @Test
    void deleteDirectorByIDUnHappyPath() {
        Throwable ex = catchThrowable(() -> directorService.deleteDirectorByID(223L));

        assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
    }

}