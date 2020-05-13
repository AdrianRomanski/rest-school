package adrianromanski.restschool.model.base_entity;

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
    @NotNull
    @Size(min = 3, max = 20)
    private String city;
    @NotNull
    @Size(min = 3, max = 20)
    private String streetName;
    @NotNull
    @Size(min = 5, max = 10)
    private String postalCode;

    @Builder
    public AddressDTO(String country, String city, String streetName, String postalCode) {
        this.country = country;
        this.city = city;
        this.streetName = streetName;
        this.postalCode = postalCode;
    }
}
