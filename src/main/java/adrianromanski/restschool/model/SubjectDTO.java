package adrianromanski.restschool.model;

import adrianromanski.restschool.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubjectDTO extends BaseEntity {

    private String name;
    private Long value;

    private Set<StudentDTO> studentsDTO = new HashSet<>();
    private Set<ExamDTO> examsDTO = new HashSet<>();

}
