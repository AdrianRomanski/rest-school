package adrianromanski.restschool.mapper;

import adrianromanski.restschool.domain.base_entity.event.Exam;
import adrianromanski.restschool.domain.base_entity.Subject;
import adrianromanski.restschool.mapper.event.ExamMapper;
import adrianromanski.restschool.model.base_entity.event.ExamDTO;
import adrianromanski.restschool.model.base_entity.SubjectDTO;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExamMapperTest {

    private final String NAME = "Final Math Exam";
    private final String MATH = "Math";
    private final LocalDate DATE = LocalDate.now();
    private final Long POINTS = 100L;

    ExamMapper examMapper = ExamMapper.INSTANCE;

    @Test
    public void ExamToExamDTO() {
        Exam exam = new Exam();
        exam.setName(NAME);
        exam.setDate(DATE);
        exam.setMaxPoints(POINTS);
        Subject math = new Subject();
        math.setName(MATH);
        exam.setSubject(math);

        ExamDTO examDTO = examMapper.examToExamDTO(exam);

        assertEquals(NAME, examDTO.getName());
        assertEquals(DATE, examDTO.getDate());
        assertEquals(POINTS, examDTO.getMaxPoints());
        assertEquals(MATH, examDTO.getSubjectDTO().getName());
    }

    @Test
    public void ExamDTOToExam() {
        ExamDTO examDTO = new ExamDTO();
        examDTO.setName(NAME);
        examDTO.setDate(DATE);
        examDTO.setMaxPoints(POINTS);
        SubjectDTO math = new SubjectDTO();
        math.setName(MATH);
        examDTO.setSubjectDTO(math);

        Exam exam = examMapper.examDTOToExam(examDTO);

        assertEquals(NAME, exam.getName());
        assertEquals(DATE, exam.getDate());
        assertEquals(POINTS, exam.getMaxPoints());
        assertEquals(MATH, exam.getSubject().getName());
    }



}
