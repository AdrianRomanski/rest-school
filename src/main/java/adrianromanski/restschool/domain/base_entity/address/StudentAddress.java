package adrianromanski.restschool.domain.base_entity.address;

import adrianromanski.restschool.domain.person.Student;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Data
@Entity
@NoArgsConstructor
public class StudentAddress extends Address {

    @OneToOne
    private Student student;

    @Builder
    public StudentAddress(String country, String city, String streetName, String postalCode) {
        super(country, city, streetName, postalCode);
    }
}
