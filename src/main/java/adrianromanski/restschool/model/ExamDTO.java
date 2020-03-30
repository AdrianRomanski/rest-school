package adrianromanski.restschool.model;

import adrianromanski.restschool.domain.Event;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamDTO extends Event {

    private Long maxPoints;

    @JsonIgnore
    private List<StudentDTO> studentsDTO = new ArrayList<>();
    private SubjectDTO subjectDTO;
    private List<ExamResultDTO> resultsDTO = new ArrayList<>();
}
