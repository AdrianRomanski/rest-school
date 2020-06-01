package adrianromanski.restschool.domain.person;

import adrianromanski.restschool.domain.enums.Gender;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import java.time.LocalDate;
import java.time.Period;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public class SchoolWorker extends Person{

    private Long yearsOfExperience;
    private LocalDate firstDay;;

    public SchoolWorker(String firstName, String lastName, Gender gender, LocalDate dateOfBirth,
                        Long age,Long yearsOfExperience, LocalDate firstDay) {
        super(firstName, lastName, gender, dateOfBirth, age);
        this.yearsOfExperience = yearsOfExperience;
        this.firstDay = firstDay;
    }

    public Long getYearsOfExperience() {
        if (firstDay != null) {
            LocalDate now = LocalDate.now();
            Period period = Period.between(this.firstDay, now);
            this.yearsOfExperience = (long) period.getYears();
            return this.yearsOfExperience;
        } else {
            return 0L;
        }
    }
}
