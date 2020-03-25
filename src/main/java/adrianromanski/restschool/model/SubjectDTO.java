package adrianromanski.restschool.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubjectDTO {

    private Long id;
    private String name;
    private Long value;
}
