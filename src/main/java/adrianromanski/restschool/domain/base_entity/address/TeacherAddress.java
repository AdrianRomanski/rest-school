package adrianromanski.restschool.domain.base_entity.address;


import adrianromanski.restschool.domain.person.Teacher;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Data
@Entity
@NoArgsConstructor
public class TeacherAddress extends Address {

    @OneToOne
    private Teacher teacher;

    @Builder
    public TeacherAddress(String country, String city, String streetName, String postalCode) {
        super(country, city, streetName, postalCode);
    }
}
