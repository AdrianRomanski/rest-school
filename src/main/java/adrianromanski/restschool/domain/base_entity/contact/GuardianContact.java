package adrianromanski.restschool.domain.base_entity.contact;

import adrianromanski.restschool.domain.person.Guardian;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class GuardianContact extends Contact{

    @Builder
    public GuardianContact(String telephoneNumber, String emergencyNumber, String email) {
        super(telephoneNumber, emergencyNumber, email);
    }

    @OneToOne
    private Guardian guardian;
}
