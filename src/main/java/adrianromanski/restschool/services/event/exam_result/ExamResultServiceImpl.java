package adrianromanski.restschool.services.event.exam_result;

import adrianromanski.restschool.domain.base_entity.event.ExamResult;
import adrianromanski.restschool.exceptions.ResourceNotFoundException;
import adrianromanski.restschool.mapper.event.ExamResultMapper;
import adrianromanski.restschool.model.base_entity.event.ExamResultDTO;
import adrianromanski.restschool.repositories.event.ExamResultRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExamResultServiceImpl implements ExamResultService {

    private final ExamResultRepository examResultRepository;
    private final ExamResultMapper examResultMapper;

    public ExamResultServiceImpl(ExamResultRepository examResultRepository, ExamResultMapper examResultMapper) {
        this.examResultRepository = examResultRepository;
        this.examResultMapper = examResultMapper;
    }

    @Override
    public List<ExamResultDTO> getAllExamResults() {
        return examResultRepository.findAll()
                .stream()
                .map(examResultMapper::examResultToExamResultDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ExamResultDTO getExamResultByID(Long id) {
        return examResultMapper
                            .examResultToExamResultDTO(examResultRepository.findById(id)
                            .orElseThrow(ResourceNotFoundException::new));

    }

    @Override
    public ExamResultDTO createExamResult(ExamResultDTO examResultDTO) {
        return saveAndReturnDTO(examResultMapper.examResultDTOToExamResult(examResultDTO));
    }

    @Override
    public ExamResultDTO updateExamResult(Long id, ExamResultDTO examResultDTO) {
        ExamResult examResult = examResultMapper.examResultDTOToExamResult(examResultDTO);
        examResult.setId(id);
        return saveAndReturnDTO(examResult);
    }

    @Override
    public void deleteExamResultByID(Long id) {
        examResultRepository.deleteById(id);
    }

    ExamResultDTO saveAndReturnDTO(ExamResult examResult) {
        examResultRepository.save(examResult);
        return examResultMapper.examResultToExamResultDTO(examResult);
    }
}
