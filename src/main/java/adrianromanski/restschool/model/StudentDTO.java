package adrianromanski.restschool.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO {
    private Long id;
    private String firstName;
    private String lastName;

    private Set<SubjectDTO> subjects = new HashSet<>();
}
