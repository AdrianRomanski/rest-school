package adrianromanski.restschool.model;

import adrianromanski.restschool.domain.Person;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO extends Person {

    private Set<SubjectDTO> subjects = new HashSet<>();
}
