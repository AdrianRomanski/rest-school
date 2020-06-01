package adrianromanski.restschool.domain.base_entity.address;

import adrianromanski.restschool.domain.person.Student;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToOne;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class StudentAddress extends Address {

    @OneToOne
    private Student student;

    @Builder
    public StudentAddress(String country, String city, String streetName, String postalCode) {
        super(country, city, streetName, postalCode);
    }
}
