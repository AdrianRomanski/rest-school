package adrianromanski.restschool.model;

import adrianromanski.restschool.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.MappedSuperclass;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class PersonDTO extends BaseEntity {

    private String firstName;
    private String lastName;
}
