package adrianromanski.restschool.domain.base_entity;

import adrianromanski.restschool.domain.base_entity.person.Student;
import lombok.Data;
import lombok.EqualsAndHashCode;


import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;

@EqualsAndHashCode(exclude = {"address", "student"})
@Entity
@Data
public class Contact extends BaseEntity {

    private String telephoneNumber;

    @Email
    private String email;

    @OneToOne
    private Address address;

    @OneToOne
    private Student student;

}
