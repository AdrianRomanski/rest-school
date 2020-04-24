package adrianromanski.restschool.model.base_entity.person;


import adrianromanski.restschool.domain.base_entity.person.enums.Gender;
import adrianromanski.restschool.model.base_entity.BaseEntityDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.MappedSuperclass;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
@AllArgsConstructor
public class PersonDTO extends BaseEntityDTO {

    private String firstName;
    private String lastName;
    private Gender gender;

}
