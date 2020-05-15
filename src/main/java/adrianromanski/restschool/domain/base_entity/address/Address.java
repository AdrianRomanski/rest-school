package adrianromanski.restschool.domain.base_entity.address;

import adrianromanski.restschool.domain.base_entity.BaseEntity;
import lombok.*;

import javax.persistence.MappedSuperclass;

@Getter
@Setter
@MappedSuperclass
@AllArgsConstructor
public class Address extends BaseEntity {

    private String country;
    private String city;
    private String streetName;
    private String postalCode;

    public Address() {
        this.country = "default";
        this.city = "default";
        this.streetName = "default";
        this.postalCode = "default";
    }
}
