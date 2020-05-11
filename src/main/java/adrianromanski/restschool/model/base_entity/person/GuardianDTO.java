package adrianromanski.restschool.model.base_entity.person;

import adrianromanski.restschool.domain.base_entity.enums.Gender;
import adrianromanski.restschool.model.base_entity.ContactDTO;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
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
