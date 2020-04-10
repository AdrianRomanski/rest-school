package adrianromanski.restschool.model.base_entity.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ExamResultDTO extends EventDTO {

    private ExamDTO examDTO;
    private float score;
    private String grade;
}
