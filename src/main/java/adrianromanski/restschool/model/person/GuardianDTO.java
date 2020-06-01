package adrianromanski.restschool.model.person;

import adrianromanski.restschool.domain.enums.Gender;
import adrianromanski.restschool.model.base_entity.contact.ContactDTO;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class GuardianDTO extends PersonDTO {

    private ContactDTO contactDTO;
    private List<StudentDTO> studentsDTO = new ArrayList<>();

    @Builder
    public GuardianDTO(String firstName, String lastName, Gender gender, LocalDate dateOfBirth,
                    Long age) {
        super(firstName, lastName, gender, dateOfBirth, age);
    }

}
