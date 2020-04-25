package adrianromanski.restschool.mapper;

import adrianromanski.restschool.domain.base_entity.event.Exam;
import adrianromanski.restschool.domain.base_entity.event.ExamResult;
import adrianromanski.restschool.mapper.event.ExamResultMapper;
import adrianromanski.restschool.model.base_entity.event.ExamDTO;
import adrianromanski.restschool.model.base_entity.event.ExamResultDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ExamResultMapperTest {


    public static final String MATH_FINAL = "Math Final";
    public static final long MAX_POINTS = 100L;
    public static final String NAME = "Adrian Romanski";
    public static final float SCORE = 95L;
    ExamResultMapper examResultMapper = ExamResultMapper.INSTANCE;

    @Test
    public void ExamResultToExamResultDTO() {
        Exam exam = new Exam();
        exam.setName(MATH_FINAL);
        exam.setMaxPoints(MAX_POINTS);

        ExamResult examResult1 = new ExamResult();
        examResult1.setExam(exam);
        examResult1.setName(NAME);
        examResult1.setScore(SCORE);   // A

        ExamResult examResult2 = new ExamResult();
        examResult2.setExam(exam);
        examResult2.setScore(80);     // B

        ExamResult examResult3 = new ExamResult();
        examResult3.setExam(exam);
        examResult3.setScore(65);    // C

        ExamResult examResult4 = new ExamResult();
        examResult4.setExam(exam);
        examResult4.setScore(50);   // D

        ExamResult examResult5 = new ExamResult();
        examResult5.setExam(exam);
        examResult5.setScore(40);   // E

        ExamResult examResult6 = new ExamResult();
        examResult6.setExam(exam);
        examResult6.setScore(0);   // F

        ExamResultDTO examResultDTO1 = examResultMapper.examResultToExamResultDTO(examResult1);
        ExamResultDTO examResultDTO2 = examResultMapper.examResultToExamResultDTO(examResult2);
        ExamResultDTO examResultDTO3 = examResultMapper.examResultToExamResultDTO(examResult3);
        ExamResultDTO examResultDTO4 = examResultMapper.examResultToExamResultDTO(examResult4);
        ExamResultDTO examResultDTO5 = examResultMapper.examResultToExamResultDTO(examResult5);
        ExamResultDTO examResultDTO6 = examResultMapper.examResultToExamResultDTO(examResult6);

        assertEquals(examResultDTO1.getScore(), SCORE);
        assertEquals(examResultDTO1.getName(), NAME);
        assertEquals(examResultDTO1.getExamDTO().getMaxPoints(), MAX_POINTS);
        assertEquals(examResultDTO1.getExamDTO().getName(), MATH_FINAL);

        // Checking set.grade method
        assertEquals(examResultDTO1.getGrade(), "A");
        assertEquals(examResultDTO2.getGrade(), "B");
        assertEquals(examResultDTO3.getGrade(), "C");
        assertEquals(examResultDTO4.getGrade(), "D");
        assertEquals(examResultDTO5.getGrade(), "E");
        assertEquals(examResultDTO6.getGrade(), "F");

    }

    @Test
    public void ExamResultDTOToExamResult() {
        ExamDTO examDTO = new ExamDTO();
        examDTO.setName(MATH_FINAL);
        examDTO.setMaxPoints(MAX_POINTS);
        ExamResultDTO examResultDTO = new ExamResultDTO();
        examResultDTO.setExamDTO(examDTO);
        examResultDTO.setName(NAME);
        examResultDTO.setScore(SCORE);


        ExamResult examResult = examResultMapper.examResultDTOToExamResult(examResultDTO);

        assertEquals(examResult.getScore(), SCORE);
        assertEquals(examResult.getName(), NAME);
        assertEquals(examResult.getExam().getMaxPoints(), MAX_POINTS);
        assertEquals(examResult.getExam().getName(), MATH_FINAL);

    }
}
