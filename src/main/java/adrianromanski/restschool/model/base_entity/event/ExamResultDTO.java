package adrianromanski.restschool.model.base_entity.event;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Getter
public class ExamResultDTO extends EventDTO {

    private ExamDTO examDTO;
    private float score;
    private String grade;
}
