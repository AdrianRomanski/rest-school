package adrianromanski.restschool.domain.base_entity.contact;

import adrianromanski.restschool.domain.base_entity.BaseEntity;
import lombok.*;
import javax.persistence.MappedSuperclass;

@Getter
@Setter
@MappedSuperclass
@AllArgsConstructor
public class Contact extends BaseEntity {

    private String telephoneNumber;
    private String emergencyNumber;
    private String email;

    public Contact() {
        this.telephoneNumber = "default";
        this.emergencyNumber = "default";
        this.email = "default@gmail.com";
    }
}
