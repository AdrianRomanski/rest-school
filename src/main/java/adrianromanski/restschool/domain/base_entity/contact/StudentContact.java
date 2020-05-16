package adrianromanski.restschool.domain.base_entity.contact;

import adrianromanski.restschool.domain.person.Student;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Data
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
