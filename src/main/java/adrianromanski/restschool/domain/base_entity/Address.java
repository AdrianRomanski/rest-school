package adrianromanski.restschool.domain.base_entity;

import lombok.*;

import javax.persistence.Entity;


@Data
@NoArgsConstructor
@Entity
public class Address extends BaseEntity {

    private String country;
    private String city;
    private String streetName;
    private String postalCode;

    @Builder
    public Address(String country, String city, String streetName, String postalCode) {
        this.country = country;
        this.city = city;
        this.streetName = streetName;
        this.postalCode = postalCode;
    }
}
