package adrianromanski.restschool.model.base_entity;

import adrianromanski.restschool.model.person.GuardianDTO;
import adrianromanski.restschool.model.person.StudentDTO;
import adrianromanski.restschool.model.person.TeacherDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@NoArgsConstructor
@Getter
@Setter
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

    @Builder
    public ContactDTO(String telephoneNumber, String emergencyNumber, String email) {
        this.telephoneNumber = telephoneNumber;
        this.emergencyNumber = emergencyNumber;
        this.email = email;
    }

    @JsonIgnore
    private StudentDTO studentDTO;

    @JsonIgnore
    private TeacherDTO teacherDTO;

    @JsonIgnore
    private GuardianDTO guardianDTO;
}
