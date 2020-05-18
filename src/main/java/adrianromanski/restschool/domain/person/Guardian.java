package adrianromanski.restschool.domain.person;

import adrianromanski.restschool.domain.base_entity.contact.Contact;
import adrianromanski.restschool.domain.enums.Gender;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
                    Long age) {
        super(firstName, lastName, gender, dateOfBirth, age);
    }


    @JsonBackReference
    @ToString.Exclude
    @OneToMany(mappedBy = "guardian")
    private List<Student> students = new ArrayList<>();

}