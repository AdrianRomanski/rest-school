package adrianromanski.restschool.domain.person;

import adrianromanski.restschool.domain.enums.Gender;
import adrianromanski.restschool.domain.event.SchoolYear;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Director extends SchoolWorker {

    private Double budget;
    // I can add something like list of tasks to do
    // list of successes?
    // meetings or conferences
    // List of Teachers? I'm not sure maybe inside School Entity its better
    // Or either create object that contains all teachers and use OneToOne Relation

    @Builder
    public Director(String firstName, String lastName, Gender gender, LocalDate dateOfBirth,
                    Long age, Long yearsOfExperience, LocalDate firstDay, Double budget) {
        super(firstName, lastName, gender, dateOfBirth, age, yearsOfExperience, firstDay);
        this.budget = budget;
    }

    @OneToMany(mappedBy = "director")
    private List<SchoolYear> schoolYears = new ArrayList<>();
}
