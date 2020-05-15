package adrianromanski.restschool.model.base_entity.address;

import adrianromanski.restschool.model.person.StudentDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StudentAddressDTO extends AddressDTO {

    private StudentDTO studentDTO;

    @Builder
    public StudentAddressDTO(String country, String city, String streetName, String postalCode) {
        super(country, city, streetName, postalCode);
    }
}
