package adrianromanski.restschool.model.base_entity.address;

import adrianromanski.restschool.model.person.TeacherDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class TeacherAddressDTO extends AddressDTO {

    private TeacherDTO teacherDTO;

    @Builder
    public TeacherAddressDTO(String country, String city, String streetName, String postalCode) {
        super(country, city, streetName, postalCode);
    }
}
