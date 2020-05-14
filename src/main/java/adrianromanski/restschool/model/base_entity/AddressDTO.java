package adrianromanski.restschool.model.base_entity;

import adrianromanski.restschool.model.person.StudentDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Getter
@Setter
@NoArgsConstructor
public class AddressDTO extends BaseEntityDTO {

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
    public AddressDTO(String country, String city, String streetName, String postalCode) {
        this.country = country;
        this.city = city;
        this.streetName = streetName;
        this.postalCode = postalCode;
    }

    @JsonIgnore
    private StudentDTO studentDTO;

}
