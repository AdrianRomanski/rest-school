package adrianromanski.restschool.model.person;

import adrianromanski.restschool.domain.enums.Gender;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class DirectorDTO extends SchoolWorkerDTO {

    private Double budget;

    @Builder
    public DirectorDTO(String firstName, String lastName, Gender gender, LocalDate dateOfBirth,
                       Long age, Long yearsOfExperience, LocalDate firstDay, Double budget) {
        super(firstName, lastName, gender, dateOfBirth, age, yearsOfExperience, firstDay);
        this.budget = budget;
    }
}
