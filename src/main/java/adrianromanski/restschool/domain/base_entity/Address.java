package adrianromanski.restschool.domain.base_entity;

import adrianromanski.restschool.domain.person.Student;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Entity
public class Address extends BaseEntity {

    @NotNull
    @Size(min = 3, max = 20)
    private String country;

    @Size(min = 3, max = 20)
    @NotNull
    private String city;

    @Size(min = 3, max = 20)
    @NotNull
    private String streetName;

    @Size(min = 3, max = 10)
    @NotNull
    private String postalCode;

    @Builder
    public Address(String country, String city, String streetName, String postalCode) {
        this.country = country;
        this.city = city;
        this.streetName = streetName;
        this.postalCode = postalCode;
    }

    public Address() {
        this.country = "default";
        this.city = "default";
        this.streetName = "default";
        this.postalCode = "default";
    }

    @OneToOne
    private Student student;
}
