package adrianromanski.restschool.model.base_entity;


import adrianromanski.restschool.domain.base_entity.person.Guardian;
import adrianromanski.restschool.model.base_entity.person.GuardianDTO;
import adrianromanski.restschool.model.base_entity.person.StudentDTO;
import adrianromanski.restschool.model.base_entity.person.TeacherDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class ContactDTO extends BaseEntityDTO {

    private String telephoneNumber;
    private String email;
    private AddressDTO addressDTO;
    private StudentDTO studentDTO;
    private TeacherDTO teacherDTO;
    private GuardianDTO guardianDTO;

    @Builder
    public ContactDTO(String telephoneNumber, String email) {
        this.telephoneNumber = telephoneNumber;
        this.email = email;
    }
}
