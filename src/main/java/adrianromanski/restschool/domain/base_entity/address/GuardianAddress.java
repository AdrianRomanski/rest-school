package adrianromanski.restschool.domain.base_entity.address;

import adrianromanski.restschool.domain.person.Guardian;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class GuardianAddress extends Address{

    @Builder
    public GuardianAddress(String country, String city, String streetName, String postalCode) {
        super(country, city, streetName, postalCode);
    }

    @OneToOne
    Guardian guardian;
}
