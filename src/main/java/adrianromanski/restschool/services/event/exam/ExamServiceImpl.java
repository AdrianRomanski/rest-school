package adrianromanski.restschool.services.event.exam;

import adrianromanski.restschool.domain.enums.Subjects;
import adrianromanski.restschool.domain.event.Exam;
import adrianromanski.restschool.exceptions.ResourceNotFoundException;
import adrianromanski.restschool.mapper.event.ExamMapper;
import adrianromanski.restschool.model.event.ExamDTO;
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
        return examRepository
                .findById(id)
                .map(examMapper::examToExamDTO)
                .orElseThrow(() -> new ResourceNotFoundException(id, Exam.class));
    }

    /**
     * @return Exam with matching name
     * @throws ResourceNotFoundException if not found
     */
    @Override
    public ExamDTO getExamByName(String name) {
        return examRepository
                .getByName(name)
                .map(examMapper::examToExamDTO)
                .orElseThrow(() -> new ResourceNotFoundException(name, Exam.class));
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
     * @return All Exams For Subjects
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
     * @return All Exams grouped by Subjects and Teachers
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
     * @return All Exams grouped by number of Students and Subjects
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
     * @param examDTO
     * Save Subject to Database
     * @return ExamDTO after saving it to database
     */
    @Override
    public ExamDTO createNewExam(ExamDTO examDTO) {
        examRepository.save(examMapper.examDTOToExam(examDTO));
        return examDTO;
    }


    /**
     * Update Exam with Matching ID and save it to Database
     * @return ExamDTO object if successfully saved
     * @throws ResourceNotFoundException if not found
     */
    @Override
    public ExamDTO updateExam(Long id, ExamDTO examDTO) {
        examRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id, Exam.class));
        Exam updatedExam = examMapper.examDTOToExam(examDTO);
        updatedExam.setId(id);
        examRepository.save(updatedExam);
        log.info("Exam with id: " + id + " successfully updated");
        return examMapper.examToExamDTO(updatedExam);
        }


    /**
     * Delete Exam with matching id
     * @throws ResourceNotFoundException if not found
     */
    @Override
    public void deleteExamById(Long id) {
        examRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id, Exam.class));
        examRepository.deleteById(id);
        log.info("Exam with id: " + id + " successfully deleted");
    }
}