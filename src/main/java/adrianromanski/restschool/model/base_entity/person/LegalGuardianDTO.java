package adrianromanski.restschool.model.base_entity.person;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LegalGuardianDTO extends PersonDTO {

    private String telephoneNumber;
    private String email;
}
