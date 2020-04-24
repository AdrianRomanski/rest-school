package adrianromanski.restschool.domain.base_entity.person;

import adrianromanski.restschool.domain.base_entity.BaseEntity;
import adrianromanski.restschool.domain.base_entity.person.enums.Gender;
import lombok.*;

import javax.persistence.MappedSuperclass;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
@AllArgsConstructor
public class Person extends BaseEntity {

    private String firstName;
    private String lastName;
    private Gender gender;

}
