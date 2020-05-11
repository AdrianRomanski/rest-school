package adrianromanski.restschool.domain.base_entity;

import adrianromanski.restschool.domain.base_entity.person.Student;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;

@EqualsAndHashCode(exclude = {"address", "student"})
@Entity
@NoArgsConstructor
@Data
public class Contact extends BaseEntity {

    private String telephoneNumber;

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
}
