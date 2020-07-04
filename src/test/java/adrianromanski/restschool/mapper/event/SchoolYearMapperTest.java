package adrianromanski.restschool.mapper.event;

import adrianromanski.restschool.domain.enums.Sport;
import adrianromanski.restschool.domain.event.SchoolYear;
import adrianromanski.restschool.domain.group.SportTeam;
import adrianromanski.restschool.domain.group.TeachingStaff;
import adrianromanski.restschool.domain.person.Director;
import adrianromanski.restschool.domain.person.Teacher;
import adrianromanski.restschool.model.event.SchoolYearDTO;
import adrianromanski.restschool.model.group.SportTeamDTO;
import adrianromanski.restschool.model.group.TeachingStaffDTO;
import adrianromanski.restschool.model.person.DirectorDTO;
import adrianromanski.restschool.model.person.TeacherDTO;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;

import static adrianromanski.restschool.domain.enums.Sport.FOOTBALL;
import static org.junit.jupiter.api.Assertions.*;

class SchoolYearMapperTest {

    public static final String NAME = "Flowers";
    public static final LocalDate LOCAL_DATE = LocalDate.of(2020, 10, 10);
    public static final String SULTANS = "Sultans";
    public static final String MAHARAJAH = "Maharajah";
    public static final String FIRST_NAME = "Walter";
    public static final String LAST_NAME = "White";
    SchoolYearMapper mapper = SchoolYearMapper.INSTANCE;

    @Test
    void schoolYearToSchoolYearDTO() {
        SchoolYear schoolYear = SchoolYear.builder().name(NAME).date(LOCAL_DATE).build();

        SportTeam sportTeam = SportTeam.builder().sport(FOOTBALL).name(SULTANS).president(MAHARAJAH).build();
        Director director = Director.builder().firstName(FIRST_NAME).lastName(LAST_NAME).build();
        TeachingStaff teachingStaff = new TeachingStaff();
        teachingStaff.setTeachers(Arrays.asList(new Teacher(), new Teacher()));
        schoolYear.setDirector(director);
        schoolYear.setSportTeams(Collections.singletonList(sportTeam));
        schoolYear.setTeachingStaff(teachingStaff);

        SchoolYearDTO schoolYearDTO = mapper.schoolYearToSchoolYearDTO(schoolYear);

        assertEquals(schoolYearDTO.getName(), NAME);
        assertEquals(schoolYearDTO.getDate(), LOCAL_DATE);

        assertEquals(schoolYearDTO.getDirectorDTO().getFirstName(), FIRST_NAME);
        assertEquals(schoolYearDTO.getDirectorDTO().getLastName(), LAST_NAME);

        assertEquals(schoolYearDTO.getSportTeamsDTO().get(0).getSport(), FOOTBALL);
        assertEquals(schoolYearDTO.getSportTeamsDTO().get(0).getName(), SULTANS);
        assertEquals(schoolYearDTO.getSportTeamsDTO().get(0).getPresident(), MAHARAJAH);

    }

    @Test
    void schoolYearDTOToSchoolYear() {
        SchoolYearDTO schoolYearDTO = SchoolYearDTO.builder().name(NAME).date(LOCAL_DATE).build();

        SportTeamDTO sportTeamDTO = SportTeamDTO.builder().sport(FOOTBALL).name(SULTANS).president(MAHARAJAH).build();
        DirectorDTO directorDTO = DirectorDTO.builder().firstName(FIRST_NAME).lastName(LAST_NAME).build();
        TeachingStaffDTO teachingStaffDTO = new TeachingStaffDTO();
        teachingStaffDTO.setTeachersDTO(Arrays.asList(new TeacherDTO(), new TeacherDTO()));
        schoolYearDTO.setDirectorDTO(directorDTO);
        schoolYearDTO.setSportTeamsDTO(Collections.singletonList(sportTeamDTO));
        schoolYearDTO.setTeachingStaffDTO(teachingStaffDTO);

        SchoolYear schoolYear = mapper.schoolYearDTOToSchoolYear(schoolYearDTO);

        assertEquals(schoolYear.getName(), NAME);
        assertEquals(schoolYear.getDate(), LOCAL_DATE);

        assertEquals(schoolYear.getDirector().getFirstName(), FIRST_NAME);
        assertEquals(schoolYear.getDirector().getLastName(), LAST_NAME);

        assertEquals(schoolYear.getSportTeams().get(0).getSport(), FOOTBALL);
        assertEquals(schoolYear.getSportTeams().get(0).getName(), SULTANS);
        assertEquals(schoolYear.getSportTeams().get(0).getPresident(), MAHARAJAH);
    }
}