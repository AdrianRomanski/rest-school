package adrianromanski.restschool.domain.base_entity.person;

import adrianromanski.restschool.domain.base_entity.person.enums.Gender;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
public class Guardian extends Person {

    @Builder
    public Guardian(String firstName, String lastName, Gender gender, LocalDate dateOfBirth,
                    Long age, String telephoneNumber, String email) {
        super(firstName, lastName, gender, dateOfBirth, age);
        this.telephoneNumber = telephoneNumber;
        this.email = email;
    }

    private String telephoneNumber;
    private String email;

}
