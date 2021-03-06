package adrianromanski.restschool.domain.base_entity.contact;

import adrianromanski.restschool.domain.person.Student;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class StudentContact extends Contact{

    @Builder
    public StudentContact(String telephoneNumber, String emergencyNumber, String email) {
        super(telephoneNumber, emergencyNumber, email);
    }

    @ToString.Exclude
    @OneToOne
    private Student student;

}
