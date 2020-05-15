package adrianromanski.restschool.model.base_entity.address;

import adrianromanski.restschool.model.base_entity.BaseEntityDTO;
import lombok.*;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@MappedSuperclass
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
    @Size(min = 3, max = 10)
    private String postalCode;

    public AddressDTO() {
        this.country = "default";
        this.city = "default";
        this.streetName = "default";
        this.postalCode = "default";
    }
}
