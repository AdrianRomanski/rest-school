package adrianromanski.restschool.services.event.exam;

import adrianromanski.restschool.domain.base_entity.event.Exam;
import adrianromanski.restschool.exceptions.ResourceNotFoundException;
import adrianromanski.restschool.mapper.event.ExamMapper;
import adrianromanski.restschool.model.base_entity.event.ExamDTO;
import adrianromanski.restschool.repositories.event.ExamRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ExamServiceImpl implements ExamService {

    private final ExamMapper examMapper;
    private final ExamRepository examRepository;

    public ExamServiceImpl(ExamMapper examMapper, ExamRepository examRepository) {
        this.examMapper = examMapper;
        this.examRepository = examRepository;
    }


    /**
     * @return all Exams
     */
    @Override
    public List<ExamDTO> getAllExams() {
        return examRepository.findAll()
                .stream()
                .map(examMapper::examToExamDTO)
                .collect(Collectors.toList());
    }


    /**
     * @return Exam with matching id
     * @throws ResourceNotFoundException if not found
     */
    @Override
    public ExamDTO getExamById(Long id) {
        return examRepository.findById(id)
                .map(examMapper::examToExamDTO)
                .orElseThrow(ResourceNotFoundException::new);
    }


    /**
     * Converts DTO Object and Save it to Database
     * @return ExamDTO object
     */
    @Override
    public ExamDTO createNewExam(ExamDTO examDTO) {
        examRepository.save(examMapper.examDTOToExam(examDTO));
        return examDTO;
    }


    /**
     * Converts DTO Object, Update Exam with Matching ID and save it to Database
     * @return ExamDTO object if the Sport Team  was successfully saved
     * @throws ResourceNotFoundException if not found
     */
    @Override
    public ExamDTO updateExam(Long id, ExamDTO examDTO) {
        if (examRepository.findById(id).isPresent()) {
            Exam exam = examRepository.findById(id).get();
            exam.setId(id);
            examRepository.save(exam);
            log.info("Exam with id: " + id + " successfully updated");
            return examMapper.examToExamDTO(exam);
        } else {
            log.debug("Exam with id: " + id + " not found");
            throw new ResourceNotFoundException("Exam with id: " + id + " not found");
        }
    }


    /**
     * Delete Exam with matching id
     * @throws ResourceNotFoundException if not found
     */
    @Override
    public void deleteExamById(Long id) {
        if (examRepository.findById(id).isPresent()) {
            examRepository.deleteById(id);
            log.info("Exam with id: " + id + " successfully deleted");
        } else {
            log.debug("Exam with id: " + id + " not found");
            throw new ResourceNotFoundException("Exam with id: " + id + " not found");
        }
    }
}