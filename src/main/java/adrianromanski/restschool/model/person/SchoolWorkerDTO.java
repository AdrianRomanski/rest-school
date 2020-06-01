package adrianromanski.restschool.model.person;

import adrianromanski.restschool.domain.enums.Gender;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public class SchoolWorkerDTO extends PersonDTO {

    private LocalDate firstDay;
    private Long yearsOfExperience;

    public SchoolWorkerDTO(String firstName, String lastName, Gender gender, LocalDate dateOfBirth,
                           Long age, Long yearsOfExperience, LocalDate firstDay) {
        super(firstName, lastName, gender, dateOfBirth, age);
        this.yearsOfExperience = yearsOfExperience;
        this.firstDay = firstDay;
    }
}
