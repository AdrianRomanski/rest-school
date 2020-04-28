package adrianromanski.restschool.domain.base_entity.person;

import adrianromanski.restschool.domain.base_entity.BaseEntity;
import adrianromanski.restschool.domain.base_entity.enums.Gender;
import lombok.*;

import javax.persistence.MappedSuperclass;
import java.time.LocalDate;
import java.time.Period;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
@AllArgsConstructor
public class Person extends BaseEntity {

    private String firstName;
    private String lastName;
    private Gender gender;
    private LocalDate dateOfBirth;
    private Long age;

    public Long getAge() {
        if (dateOfBirth != null) {
            LocalDate now = LocalDate.now();
            Period period = Period.between(this.getDateOfBirth(), now);
            this.age = (long) period.getYears();
            return this.age;
        } else {
            return 0L;
        }
    }
}
