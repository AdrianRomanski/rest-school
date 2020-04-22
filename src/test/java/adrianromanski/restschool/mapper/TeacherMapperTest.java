package adrianromanski.restschool.mapper;

import adrianromanski.restschool.domain.base_entity.event.Exam;
import adrianromanski.restschool.domain.base_entity.person.Teacher;
import adrianromanski.restschool.mapper.person.TeacherMapper;
import adrianromanski.restschool.model.base_entity.event.ExamDTO;
import adrianromanski.restschool.model.base_entity.person.TeacherDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TeacherMapperTest {

    public static final String WALTER = "Walter";
    public static final String WHITE = "White";
    public static final long MAX_POINTS = 100L;
    public static final String CHEMISTRY_ALKALOIDS = "Chemistry - alkaloids";
    TeacherMapper teacherMapper = TeacherMapper.INSTANCE;

    @Test
    public void teacherToTeacherDTO() {
        //given
        Teacher teacher = new Teacher();
        teacher.setFirstName(WALTER);
        teacher.setLastName(WHITE);
        Exam exam = new Exam();
        exam.setMaxPoints(MAX_POINTS);
        exam.setName(CHEMISTRY_ALKALOIDS);
        teacher.getExams().add(exam);

        //when
        TeacherDTO teacherDTO = teacherMapper.teacherToTeacherDTO(teacher);

        //then
        assertEquals(teacherDTO.getFirstName(), WALTER);
        assertEquals(teacherDTO.getLastName(), WHITE);
        assertEquals(teacherDTO.getExamsDTO().get(0).getMaxPoints(), MAX_POINTS);
        assertEquals(teacherDTO.getExamsDTO().get(0).getName(), CHEMISTRY_ALKALOIDS);
    }

    @Test
    public void teacherDTOToTeacher() {
        //given
        TeacherDTO teacherDTO = new TeacherDTO();
        teacherDTO.setFirstName(WALTER);
        teacherDTO.setLastName(WHITE);
        ExamDTO examDTO = new ExamDTO();
        examDTO.setMaxPoints(MAX_POINTS);
        examDTO.setName(CHEMISTRY_ALKALOIDS);
        teacherDTO.getExamsDTO().add(examDTO);

        //when
        Teacher teacher = teacherMapper.teacherDTOToTeacher(teacherDTO);

        //then
        assertEquals(teacher.getFirstName(), WALTER);
        assertEquals(teacher.getLastName(), WHITE);
        assertEquals(teacher.getExams().get(0).getMaxPoints(), MAX_POINTS);
        assertEquals(teacher.getExams().get(0).getName(), CHEMISTRY_ALKALOIDS);
    }
}

