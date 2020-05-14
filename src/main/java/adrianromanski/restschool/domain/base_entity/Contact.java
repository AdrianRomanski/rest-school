package adrianromanski.restschool.domain.base_entity;

import adrianromanski.restschool.domain.person.Guardian;
import adrianromanski.restschool.domain.person.Student;
import adrianromanski.restschool.domain.person.Teacher;
import lombok.*;


import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@EqualsAndHashCode(exclude = {"address", "student"})
@Entity
@Data
public class Contact extends BaseEntity {

    @NotNull
    @Size(min = 7, max = 14)
    private String telephoneNumber;

    @NotNull
    @Size(min = 7, max = 14)
    private String emergencyNumber;

    @NotNull
    @Email
    private String email;


    @Builder
    public Contact(String telephoneNumber, String emergencyNumber, String email) {
        this.telephoneNumber = telephoneNumber;
        this.emergencyNumber = emergencyNumber;
        this.email = email;
    }

    public Contact() {
        this.telephoneNumber = "default";
        this.emergencyNumber = "default";
        this.email = "default@gmail.com";
    }

    @ToString.Exclude
    @OneToOne
    private Student student;

    @ToString.Exclude
    @OneToOne
    private Teacher teacher;

    @ToString.Exclude
    @OneToOne
    private Guardian guardian;
}
