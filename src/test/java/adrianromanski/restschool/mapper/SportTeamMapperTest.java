package adrianromanski.restschool.mapper;

import adrianromanski.restschool.domain.group.SportTeam;
import adrianromanski.restschool.domain.person.Student;
import adrianromanski.restschool.mapper.group.SportTeamMapper;
import adrianromanski.restschool.model.group.SportTeamDTO;
import adrianromanski.restschool.model.person.StudentDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SportTeamMapperTest {

    public static final String FIRST_NAME = "Jessie";
    public static final String LAST_NAME = "Pinkman";
    public static final String NAME = "Electric Hornets";
    public static final String PRESIDENT = "Philip";
    public static final long ID = 1L;
    SportTeamMapper sportTeamMapper = SportTeamMapper.INSTANCE;

    @Test
    void sportTeamToSportTeamDTO() {
        SportTeam sportTeam = new SportTeam();
        sportTeam.setId(ID);
        sportTeam.setName(NAME);
        sportTeam.setPresident(PRESIDENT);


        Student student = new Student();
        student.setFirstName(FIRST_NAME);
        student.setLastName(LAST_NAME);
        student.setId(ID);
        student.setSportTeam(sportTeam);

        sportTeam.getStudents().add(student);

        SportTeamDTO sportTeamDTO = sportTeamMapper.sportTeamToSportTeamDTO(sportTeam);

        // Testing sportTeam
        assertEquals(sportTeamDTO.getId(), ID);
        assertEquals(sportTeamDTO.getName(), NAME);
        assertEquals(sportTeamDTO.getPresident(), PRESIDENT);

        // Testing Student
        assertEquals(sportTeamDTO.getStudentsDTO().get(0).getFirstName(), FIRST_NAME);
        assertEquals(sportTeamDTO.getStudentsDTO().get(0).getLastName(), LAST_NAME);
        assertEquals(sportTeamDTO.getStudentsDTO().get(0).getId(), ID);


    }

    @Test
    void sportTeamDTOToSportTeam() {
        SportTeamDTO sportTeamDTO = new SportTeamDTO();
        sportTeamDTO.setId(ID);
        sportTeamDTO.setName(NAME);
        sportTeamDTO.setPresident(PRESIDENT);


        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setFirstName(FIRST_NAME);
        studentDTO.setLastName(LAST_NAME);
        studentDTO.setId(ID);
        studentDTO.setSportTeamDTO(sportTeamDTO);

        sportTeamDTO.getStudentsDTO().add(studentDTO);

        SportTeam sportTeam = sportTeamMapper.sportTeamDTOToSportTeam(sportTeamDTO);

        // Testing sportTeam
        assertEquals(sportTeam.getId(), ID);
        assertEquals(sportTeam.getName(), NAME);
        assertEquals(sportTeam.getPresident(), PRESIDENT);

        // Testing Student
        assertEquals(sportTeam.getStudents().get(0).getFirstName(), FIRST_NAME);
        assertEquals(sportTeam.getStudents().get(0).getLastName(), LAST_NAME);
        assertEquals(sportTeam.getStudents().get(0).getId(), ID);
    }
}