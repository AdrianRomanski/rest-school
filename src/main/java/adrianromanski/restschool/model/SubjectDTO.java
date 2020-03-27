package adrianromanski.restschool.model;

import adrianromanski.restschool.domain.BaseEntity;
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
public class SubjectDTO extends BaseEntity {

    private String name;
    private Long value;

    private Set<StudentDTO> studentsDTO = new HashSet<>();

}
