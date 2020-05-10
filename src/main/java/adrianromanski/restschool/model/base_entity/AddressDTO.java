package adrianromanski.restschool.model.base_entity;

import lombok.*;

@Getter
@Setter
public class AddressDTO extends BaseEntityDTO {

    private String country;
    private String city;
    private String streetName;
    private String postalCode;

    @Builder
    public AddressDTO(String country, String city, String streetName, String postalCode) {
        this.country = country;
        this.city = city;
        this.streetName = streetName;
        this.postalCode = postalCode;
    }
}
