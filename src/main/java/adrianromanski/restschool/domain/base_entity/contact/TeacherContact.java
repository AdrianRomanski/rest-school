package adrianromanski.restschool.domain.base_entity.contact;

import adrianromanski.restschool.domain.person.Teacher;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class TeacherContact extends Contact{

    @Builder
    public TeacherContact(String telephoneNumber, String emergencyNumber, String email) {
        super(telephoneNumber, emergencyNumber, email);
    }

    @OneToOne
    private Teacher teacher;

}
