package adrianromanski.restschool.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubjectDTO {

    private Long id;
    private String name;
    private Long value;

    private Set<StudentDTO> students = new HashSet<>();
}
