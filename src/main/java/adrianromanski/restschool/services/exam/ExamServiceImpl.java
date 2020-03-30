package adrianromanski.restschool.services.exam;

import adrianromanski.restschool.domain.base_entity.event.Exam;
import adrianromanski.restschool.exceptions.ResourceNotFoundException;
import adrianromanski.restschool.mapper.ExamMapper;
import adrianromanski.restschool.model.base_entity.event.ExamDTO;
import adrianromanski.restschool.repositories.ExamRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExamServiceImpl implements ExamService {

    private ExamMapper examMapper;
    private ExamRepository examRepository;

    public ExamServiceImpl(ExamMapper examMapper, ExamRepository examRepository) {
        this.examMapper = examMapper;
        this.examRepository = examRepository;
    }

    @Override
    public List<ExamDTO> getAllExams() {
        return examRepository.findAll()
                .stream()
                .map(examMapper::examToExamDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ExamDTO createNewExam(ExamDTO examDTO) {
        return savedAndReturnDto(examMapper.examDTOToExam(examDTO));
    }

    @Override
    public ExamDTO updateExam(Long id, ExamDTO examDTO) {
        Exam exam = examMapper.examDTOToExam(examDTO);
        exam.setId(id);
        return savedAndReturnDto(exam);
    }

    @Override
    public ExamDTO getExamById(Long id) {
        return examMapper.examToExamDTO(examRepository.findById(id).
                                            orElseThrow(ResourceNotFoundException::new));
    }

    @Override
    public void deleteExamById(Long id) {
        examRepository.deleteById(id);
    }


    public ExamDTO savedAndReturnDto(Exam exam) {
        examRepository.save(exam);
        return examMapper.examToExamDTO(exam);
    }
}
