package adrianromanski.restschool.services.person.director;

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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DirectorServiceImpl implements DirectorService {

    private final DirectorRepository directorRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final PaymentRepository paymentRepository;
    private final DirectorMapper directorMapper;
    private final TeacherMapper teacherMapper;
    private final StudentMapper studentMapper;
    private final PaymentMapper paymentMapper;

    public DirectorServiceImpl(DirectorRepository directorRepository, TeacherRepository teacherRepository,
                               StudentRepository studentRepository, PaymentRepository paymentRepository,
                               DirectorMapper directorMapper, TeacherMapper teacherMapper,
                               StudentMapper studentMapper, PaymentMapper paymentMapper) {
        this.directorRepository = directorRepository;
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
        this.paymentRepository = paymentRepository;
        this.directorMapper = directorMapper;
        this.teacherMapper = teacherMapper;
        this.studentMapper = studentMapper;
        this.paymentMapper = paymentMapper;
    }


    /**
     * @return Director
     * @throws ResourceNotFoundException if not found
     */
    @Override
    public DirectorDTO getDirector() {
        return directorRepository.findAll()
                .stream()
                .map(directorMapper::directorToDirectorDTO)
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Can't find director"));
    }


    /**
     * @param teacherID of the Teacher to add
     * @param paymentDTO to add it to the list of the Payments
     * At the moment i have only one type of worker later i'll do something more generic
     * @return Payment if successfully added
     * @throws ResourceNotFoundException if not found
     */
    @Override
    public PaymentDTO addPaymentToTeacher(Long teacherID, PaymentDTO paymentDTO) {
        Teacher teacher = teacherRepository.findById(teacherID)
                .orElseThrow(() -> new ResourceNotFoundException(teacherID, Teacher.class));
        Payment payment = paymentMapper.paymentDTOToPayment(paymentDTO);
        teacher.getPayments().add(payment);
        payment.setTeacher(teacher);
        teacherRepository.save(teacher);
        paymentRepository.save(payment);
        log.info("Teacher with id: " + teacherID + " have payment set up");
        return paymentMapper.paymentToPaymentDTO(payment);
    }


    /**
     * @param directorID of the Director to add
     * @param directorDTO for update Director information's
     * @return Director if successfully updated
     * @throws ResourceNotFoundException if not found
     */
    @Override
    public DirectorDTO updateDirector(Long directorID, DirectorDTO directorDTO) {
        directorRepository.findById(directorID)
                .orElseThrow(() -> new ResourceNotFoundException(directorID, Director.class));
        Director updatedDirector = directorMapper.directorDTOToDirector(directorDTO);
        updatedDirector.setId(directorID);
        directorRepository.save(updatedDirector);
        log.info("Director with id: " + directorID + " successfully updated");
        return directorMapper.directorToDirectorDTO(updatedDirector);
    }


    /**
     * @param directorID of the Director to delete
     * @throws ResourceNotFoundException if not found
     */
    @Override
    public void deleteDirectorByID(Long directorID) {
        directorRepository.findById(directorID)
                .orElseThrow(() -> new ResourceNotFoundException(directorID, Director.class));
        directorRepository.deleteById(directorID);
        log.info("Director with id: " + directorID + " successfully deleted");
    }
}
