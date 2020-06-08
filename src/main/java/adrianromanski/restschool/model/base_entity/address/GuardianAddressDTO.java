package adrianromanski.restschool.model.base_entity.address;

import adrianromanski.restschool.model.person.GuardianDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GuardianAddressDTO extends AddressDTO{

    @Builder
    public GuardianAddressDTO(String country, String city, String streetName, String postalCode) {
        super(country, city, streetName, postalCode);
    }

    private GuardianDTO guardianDTO;
}
