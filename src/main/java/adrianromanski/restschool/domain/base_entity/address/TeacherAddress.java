package adrianromanski.restschool.domain.base_entity.address;


import adrianromanski.restschool.domain.person.Teacher;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class TeacherAddress extends Address {

    @OneToOne
    private Teacher teacher;

    @Builder
    public TeacherAddress(String country, String city, String streetName, String postalCode) {
        super(country, city, streetName, postalCode);
    }
}
