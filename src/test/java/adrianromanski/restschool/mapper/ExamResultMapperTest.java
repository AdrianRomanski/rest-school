package adrianromanski.restschool.mapper;

import adrianromanski.restschool.domain.Exam;
import adrianromanski.restschool.domain.ExamResult;
import adrianromanski.restschool.model.ExamDTO;
import adrianromanski.restschool.model.ExamResultDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ExamResultMapperTest {


    public static final String MATH_FINAL = "Math Final";
    public static final long MAX_POINTS = 80L;
    public static final String NAME = "Adrian Romanski";
    public static final long SCORE = 60L;
    ExamResultMapper examResultMapper = ExamResultMapper.INSTANCE;

    @Test
    public void ExamResultToExamResultDTO() {
        Exam exam = new Exam();
        exam.setName(MATH_FINAL);
        exam.setMaxPoints(MAX_POINTS);
        ExamResult examResult = new ExamResult();
        examResult.setName(NAME);
        examResult.setScore(SCORE);
        examResult.setExam(exam);

        ExamResultDTO examResultDTO = examResultMapper.examResultToExamResultDTO(examResult);

        assertEquals(examResultDTO.getScore(), SCORE);
        assertEquals(examResultDTO.getName(), NAME);
        assertEquals(examResultDTO.getExamDTO().getMaxPoints(), MAX_POINTS);
        assertEquals(examResultDTO.getExamDTO().getName(), MATH_FINAL);

    }

    @Test
    public void ExamResultDTOToExamResult() {
        ExamDTO examDTO = new ExamDTO();
        examDTO.setName(MATH_FINAL);
        examDTO.setMaxPoints(MAX_POINTS);
        ExamResultDTO examResultDTO = new ExamResultDTO();
        examResultDTO.setName(NAME);
        examResultDTO.setScore(SCORE);
        examResultDTO.setExamDTO(examDTO);

        ExamResult examResult = examResultMapper.examResultDTOToExamResult(examResultDTO);

        assertEquals(examResult.getScore(), SCORE);
        assertEquals(examResult.getName(), NAME);
        assertEquals(examResult.getExam().getMaxPoints(), MAX_POINTS);
        assertEquals(examResult.getExam().getName(), MATH_FINAL);

    }
}
