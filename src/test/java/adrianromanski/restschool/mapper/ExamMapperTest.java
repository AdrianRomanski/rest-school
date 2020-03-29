package adrianromanski.restschool.mapper;

import adrianromanski.restschool.domain.Exam;
import adrianromanski.restschool.domain.Subject;
import adrianromanski.restschool.model.ExamDTO;
import adrianromanski.restschool.model.SubjectDTO;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExamMapperTest {

    private final String NAME = "Final Math Exam";
    private final String MATH = "Math";
    private final LocalDate DATE = LocalDate.now();
    private final Long POINTS = 100L;
    private final Boolean PASSED = true;


    ExamMapper examMapper = ExamMapper.INSTANCE;

    @Test
    public void ExamToExamDTO() {
        Exam exam = new Exam();
        exam.setName(NAME);
        exam.setDate(DATE);
        exam.setPoints(POINTS);
        exam.setPassed(PASSED);
        Subject math = new Subject();
        math.setName(MATH);
        exam.setSubject(math);

        ExamDTO examDTO = examMapper.examToExamDTO(exam);

        assertEquals(NAME, examDTO.getName());
        assertEquals(DATE, examDTO.getDate());
        assertEquals(POINTS, examDTO.getPoints());
        assertEquals(PASSED, examDTO.getPassed());
        assertEquals(MATH, examDTO.getSubjectDTO().getName());
    }

    @Test
    public void ExamDTOToExam() {
        ExamDTO examDTO = new ExamDTO();
        examDTO.setName(NAME);
        examDTO.setDate(DATE);
        examDTO.setPoints(POINTS);
        examDTO.setPassed(PASSED);
        SubjectDTO math = new SubjectDTO();
        math.setName(MATH);
        examDTO.setSubjectDTO(math);

        Exam exam = examMapper.examDTOToExam(examDTO);

        assertEquals(NAME, exam.getName());
        assertEquals(DATE, exam.getDate());
        assertEquals(POINTS, exam.getPoints());
        assertEquals(PASSED, exam.getPassed());
        assertEquals(MATH, exam.getSubject().getName());
    }



}
