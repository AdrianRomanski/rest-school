package adrianromanski.restschool.model.person;

import adrianromanski.restschool.domain.enums.Gender;
import adrianromanski.restschool.model.base_entity.BaseEntityDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
@AllArgsConstructor
public class PersonDTO extends BaseEntityDTO {

    private String firstName;
    private String lastName;
    private Gender gender;
    private LocalDate dateOfBirth;
    private Long age;

}
