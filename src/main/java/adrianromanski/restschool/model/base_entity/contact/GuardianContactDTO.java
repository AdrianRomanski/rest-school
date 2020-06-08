package adrianromanski.restschool.model.base_entity.contact;

import adrianromanski.restschool.model.person.GuardianDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GuardianContactDTO extends ContactDTO {

    @Builder
    public GuardianContactDTO(String telephoneNumber, String emergencyNumber, String email) {
        super(telephoneNumber, emergencyNumber, email);
    }

    private GuardianDTO guardianDTO;
}
