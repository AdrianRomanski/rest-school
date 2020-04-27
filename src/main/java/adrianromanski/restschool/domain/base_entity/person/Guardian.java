package adrianromanski.restschool.domain.base_entity.person;

import adrianromanski.restschool.domain.base_entity.person.enums.Gender;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(exclude = {"students"})
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

    @JsonBackReference
    @ToString.Exclude
    @OneToMany(mappedBy = "guardian")
    private List<Student> students = new ArrayList<>();

}
