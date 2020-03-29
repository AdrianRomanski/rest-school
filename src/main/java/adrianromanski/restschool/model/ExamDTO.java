package adrianromanski.restschool.model;

import adrianromanski.restschool.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private Long maxPoints;

    @JsonIgnore
    private Set<StudentDTO> studentsDTO = new HashSet<>();
    private SubjectDTO subjectDTO;
}
