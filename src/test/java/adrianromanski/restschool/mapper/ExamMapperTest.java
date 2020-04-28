package adrianromanski.restschool.mapper;

import adrianromanski.restschool.domain.base_entity.enums.LastName;
import adrianromanski.restschool.domain.base_entity.event.Exam;
import adrianromanski.restschool.domain.base_entity.Subject;
import adrianromanski.restschool.domain.base_entity.person.Teacher;
import adrianromanski.restschool.mapper.event.ExamMapper;
import adrianromanski.restschool.model.base_entity.event.ExamDTO;
import adrianromanski.restschool.model.base_entity.SubjectDTO;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static adrianromanski.restschool.domain.base_entity.enums.LastName.*;
import static adrianromanski.restschool.domain.base_entity.enums.MaleName.*;
import static adrianromanski.restschool.domain.base_entity.enums.Specialization.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExamMapperTest {

    private final String NAME = "Final Math Exam";
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
        math.setName(MATHEMATICS);
        exam.setSubject(math);

        Teacher teacher = Teacher.builder().firstName(ETHAN.get()).lastName(COOPER.get()).build();
        exam.setTeacher(teacher);
        teacher.getExams().add(exam);

        ExamDTO examDTO = examMapper.examToExamDTO(exam);

        assertEquals(NAME, examDTO.getName());
        assertEquals(DATE, examDTO.getDate());
        assertEquals(POINTS, examDTO.getMaxPoints());
        assertEquals(MATHEMATICS, examDTO.getSubjectDTO().getName());

        assertEquals(ETHAN.get(), examDTO.getTeacherDTO().getFirstName());
        assertEquals(COOPER.get(), examDTO.getTeacherDTO().getLastName());
    }

    @Test
    public void ExamDTOToExam() {
        ExamDTO examDTO = new ExamDTO();
        examDTO.setName(NAME);
        examDTO.setDate(DATE);
        examDTO.setMaxPoints(POINTS);
        SubjectDTO math = new SubjectDTO();
        math.setName(MATHEMATICS);
        examDTO.setSubjectDTO(math);

        Exam exam = examMapper.examDTOToExam(examDTO);

        assertEquals(NAME, exam.getName());
        assertEquals(DATE, exam.getDate());
        assertEquals(POINTS, exam.getMaxPoints());
        assertEquals(MATHEMATICS, exam.getSubject().getName());
    }



}
