package adrianromanski.restschool.domain.base_entity;

import adrianromanski.restschool.domain.base_entity.person.Guardian;
import adrianromanski.restschool.domain.base_entity.person.Student;
import adrianromanski.restschool.domain.base_entity.person.Teacher;
import lombok.*;


import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;


@EqualsAndHashCode(exclude = {"address", "student"})
@Entity
@NoArgsConstructor
@Data
public class Contact extends BaseEntity {

    @NotNull
    private String telephoneNumber;

    @NotNull
    @Email
    private String email;

    @Builder
    public Contact(String telephoneNumber,String email) {
        this.telephoneNumber = telephoneNumber;
        this.email = email;
    }

    @OneToOne
    private Address address;

    @OneToOne
    private Student student;

    @OneToOne
    private Teacher teacher;

    @OneToOne
    private Guardian guardian;
}
