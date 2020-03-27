package adrianromanski.restschool.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO extends PersonDTO {

    private Set<SubjectDTO> subjectsDTO = new HashSet<>();
}
