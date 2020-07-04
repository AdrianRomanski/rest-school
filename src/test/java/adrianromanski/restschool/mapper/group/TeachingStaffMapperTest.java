package adrianromanski.restschool.mapper.group;

import adrianromanski.restschool.domain.group.TeachingStaff;
import adrianromanski.restschool.domain.person.Teacher;
import adrianromanski.restschool.model.group.TeachingStaffDTO;
import adrianromanski.restschool.model.person.TeacherDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class TeachingStaffMapperTest {

    public static final String NAME = "2020 Teaching Staff New Oklahoma";
    public static final String PRESIDENT = "Walter White";
    public static final String FIRST_NAME = "Walter";
    public static final String WHITE = "White";
    TeachingStaffMapper mapper = TeachingStaffMapper.INSTANCE;

    @Test
    void teachingStaffToTeachingStaffDTO() {
        Teacher teacher = Teacher.builder().firstName(FIRST_NAME).lastName(WHITE).build();

        TeachingStaff teachingStaff = TeachingStaff.builder().name(NAME).president(PRESIDENT).build();
        teachingStaff.setTeachers(Collections.singletonList(teacher));

        TeachingStaffDTO teachingStaffDTO = mapper.teachingStaffToTeachingStaffDTO(teachingStaff);

        assertEquals(teachingStaffDTO.getName(), NAME);
        assertEquals(teachingStaffDTO.getPresident(), PRESIDENT);

        assertEquals(teachingStaffDTO.getTeachersDTO().get(0).getFirstName(), FIRST_NAME);
        assertEquals(teachingStaffDTO.getTeachersDTO().get(0).getLastName(), WHITE);


    }

    @Test
    void testTeachingStaffToTeachingStaffDTO() {
        TeacherDTO teacherDTO = TeacherDTO.builder().firstName(FIRST_NAME).lastName(WHITE).build();

        TeachingStaffDTO teachingStaffDTO = TeachingStaffDTO.builder().name(NAME).president(PRESIDENT).build();
        teachingStaffDTO.setTeachersDTO(Collections.singletonList(teacherDTO));

        TeachingStaff teachingStaff = mapper.teachingStaffToTeachingStaffDTO(teachingStaffDTO);

        assertEquals(teachingStaff.getName(), NAME);
        assertEquals(teachingStaff.getPresident(), PRESIDENT);

        assertEquals(teachingStaff.getTeachers().get(0).getFirstName(), FIRST_NAME);
        assertEquals(teachingStaff.getTeachers().get(0).getLastName(), WHITE);
    }
}