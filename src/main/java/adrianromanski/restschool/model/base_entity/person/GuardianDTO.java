package adrianromanski.restschool.model.base_entity.person;

import adrianromanski.restschool.domain.base_entity.enums.Gender;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class GuardianDTO extends PersonDTO {

    @Builder
    public GuardianDTO(String firstName, String lastName, Gender gender, LocalDate dateOfBirth,
                    Long age, String telephoneNumber, String email) {
        super(firstName, lastName, gender, dateOfBirth, age);
        this.telephoneNumber = telephoneNumber;
        this.email = email;
    }

    private String telephoneNumber;
    private String email;

    private List<StudentDTO> studentsDTO = new ArrayList<>();
}
