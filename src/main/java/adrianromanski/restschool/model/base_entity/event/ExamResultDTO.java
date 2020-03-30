package adrianromanski.restschool.model.base_entity.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamResultDTO extends EventDTO {

    @JsonIgnore
    private ExamDTO examDTO;
    private float score;
    private String grade;
}
