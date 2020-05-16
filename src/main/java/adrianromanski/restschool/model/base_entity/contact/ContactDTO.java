package adrianromanski.restschool.model.base_entity.contact;

import adrianromanski.restschool.model.base_entity.BaseEntityDTO;
import lombok.*;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Getter
@Setter
@AllArgsConstructor
@MappedSuperclass
public class ContactDTO extends BaseEntityDTO {

    @NotNull
    @Size(min = 8, max = 14)
    private String telephoneNumber;

    @NotNull
    @Size(min = 8, max = 14)
    private String emergencyNumber;

    @NotNull
    @Email
    private String email;

    public ContactDTO() {
        this.telephoneNumber = "default";
        this.emergencyNumber = "default";
        this.email = "default@gmail.com";
    }
}
