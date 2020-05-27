package adrianromanski.restschool.model.base_entity.contact;

import adrianromanski.restschool.model.person.TeacherDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TeacherContactDTO extends ContactDTO {

    private TeacherDTO teacherDTO;

    @Builder
    public TeacherContactDTO(String telephoneNumber, String emergencyNumber, String email) {
        super(telephoneNumber, emergencyNumber, email);
    }
}
