package adrianromanski.restschool.model;

import adrianromanski.restschool.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamDTO extends BaseEntity {

    private String name;
    private LocalDate date;
    private Long points;
    private Boolean passed;

    private Set<StudentDTO> studentsDTO = new HashSet<>();
    private SubjectDTO subjectDTO;
}
