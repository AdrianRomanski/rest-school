package adrianromanski.restschool.model.base_entity.contact;

import adrianromanski.restschool.model.person.StudentDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class StudentContactDTO extends ContactDTO{

    private StudentDTO studentDTO;

    @Builder
    public StudentContactDTO(String telephoneNumber, String emergencyNumber, String email) {
        super(telephoneNumber, emergencyNumber, email);
    }
}
