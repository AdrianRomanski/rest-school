package adrianromanski.restschool.services.event.exam;

import adrianromanski.restschool.domain.base_entity.enums.Subjects;
import adrianromanski.restschool.domain.base_entity.event.Exam;
import adrianromanski.restschool.exceptions.ResourceNotFoundException;
import adrianromanski.restschool.mapper.event.ExamMapper;
import adrianromanski.restschool.model.base_entity.event.ExamDTO;
import adrianromanski.restschool.repositories.event.ExamRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@Slf4j
@Service
public class ExamServiceImpl implements ExamService {

    public static final Function<ExamDTO, String> GROUPING_BY_SUBJECT = e -> e.getSubjectDTO().getName().name();
    public static final Function<ExamDTO, String> GROUPING_BY_TEACHER = e -> e.getTeacherDTO().getLastName() + " " + e.getTeacherDTO().getFirstName();
    public static final Function<ExamDTO, Integer> GROUPING_BY_STUDENTS = e -> e.getStudentsDTO().size();
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
                .collect(toList());
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
     * @return Exam with matching name
     * @throws ResourceNotFoundException if not found
     */
    @Override
    public ExamDTO getExamByName(String name) {
        return examRepository.getByName(name)
                .map(examMapper::examToExamDTO)
                .orElseThrow(ResourceNotFoundException::new);
    }

    /**
     * @return List of Exams for Teacher with matching firstName and lastName
     */
    @Override
    public List<ExamDTO> getAllExamsForTeacher(String firstName, String lastName) {
        return examRepository.findAll()
                .stream()
                .filter(e -> e.getTeacher().getFirstName().equals(firstName))
                .filter(e -> e.getTeacher().getLastName().equals(lastName))
                .map(examMapper::examToExamDTO)
                .collect(Collectors.toList());
    }


    /**
     * @return Map where the Key is matching Subject and values List of Exams
     */
    @Override
    public Map<String, List<ExamDTO>> getExamsForSubject(Subjects subjects) {
        return examRepository.findAll()
                .stream()
                .map(examMapper::examToExamDTO)
                .filter(e -> e.getSubjectDTO().getName().equals(subjects))
                .collect(
                        groupingBy(
                                GROUPING_BY_SUBJECT
                        )
                );
    }

    /**
     * @return Map where the Keys are Subjects and values Maps
     * where they Keys are Teachers and values List of exams
     */
    @Override
    public Map<String, Map<String, List<ExamDTO>>> getAllExamsBySubjectsAndTeachers() {
        return examRepository.findAll()
                .stream()
                .map(examMapper::examToExamDTO)
                .collect(
                        groupingBy(GROUPING_BY_SUBJECT,
                                groupingBy(GROUPING_BY_TEACHER
                                )
                        )
                );
    }

    /**
     * @return Map where the Keys are Number of Students and
     * values Maps where they Keys are Subjects and values List of exams
     */
    @Override
    public Map<Integer, Map<String, List<ExamDTO>>> getAllExamsByStudentsAndSubjects() {
        return  examRepository.findAll()
                .stream()
                .map(examMapper::examToExamDTO)
                .collect(
                        groupingBy(GROUPING_BY_STUDENTS,
                               groupingBy(GROUPING_BY_SUBJECT
                               )
                        )
                );
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
            Exam exam = examMapper.examDTOToExam(examDTO);
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