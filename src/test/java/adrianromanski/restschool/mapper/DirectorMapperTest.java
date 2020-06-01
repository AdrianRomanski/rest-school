package adrianromanski.restschool.mapper;

import adrianromanski.restschool.domain.person.Director;
import adrianromanski.restschool.mapper.person.DirectorMapper;
import adrianromanski.restschool.model.person.DirectorDTO;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static adrianromanski.restschool.domain.enums.Gender.MALE;
import static adrianromanski.restschool.domain.enums.LastName.COOPER;
import static adrianromanski.restschool.domain.enums.MaleName.ETHAN;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DirectorMapperTest {

    public static final Double BUDGET = 24021411.021;
    public static final LocalDate FIRST_DAY = LocalDate.of(2006, 10, 2);
    public static final LocalDate DATE_OF_BIRTH = LocalDate.of(1992, 11, 3);
    DirectorMapper mapper = DirectorMapper.INSTANCE;

    @Test
    void directorToDirectorDTO() {
        Director director = Director.builder().firstName(ETHAN.get()).lastName(COOPER.get()).dateOfBirth(DATE_OF_BIRTH).gender(MALE)
                .firstDay(FIRST_DAY).budget(BUDGET).build();

        DirectorDTO directorDTO = mapper.directorToDirectorDTO(director);

        assertEquals(directorDTO.getFirstName(), ETHAN.get());
        assertEquals(directorDTO.getLastName(), COOPER.get());
        assertEquals(directorDTO.getDateOfBirth(), DATE_OF_BIRTH);
        assertEquals(directorDTO.getGender(), MALE);
        assertEquals(directorDTO.getFirstDay(), FIRST_DAY);
        assertEquals(directorDTO.getBudget(), BUDGET);
    }

    @Test
    void directorDTOToDirector() {
        DirectorDTO directorDTO = DirectorDTO.builder().firstName(ETHAN.get()).lastName(COOPER.get()).dateOfBirth(DATE_OF_BIRTH).gender(MALE)
                .firstDay(FIRST_DAY).budget(BUDGET).build();

        Director director = mapper.directorDTOToDirector(directorDTO);

        assertEquals(director.getFirstName(), ETHAN.get());
        assertEquals(director.getLastName(), COOPER.get());
        assertEquals(director.getDateOfBirth(), DATE_OF_BIRTH);
        assertEquals(director.getGender(), MALE);
        assertEquals(director.getFirstDay(), FIRST_DAY);
        assertEquals(director.getBudget(), BUDGET);
    }
}