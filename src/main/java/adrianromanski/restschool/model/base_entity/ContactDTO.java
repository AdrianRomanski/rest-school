package adrianromanski.restschool.model.base_entity;

import adrianromanski.restschool.model.base_entity.person.GuardianDTO;
import adrianromanski.restschool.model.base_entity.person.StudentDTO;
import adrianromanski.restschool.model.base_entity.person.TeacherDTO;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
@Setter
public class ContactDTO extends BaseEntityDTO {

    @NotNull
    private String telephoneNumber;

    @NotNull
    @Email
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
