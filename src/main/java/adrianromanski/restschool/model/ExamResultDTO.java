package adrianromanski.restschool.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamResultDTO extends EventDTO {

    private ExamDTO examDTO;
    private Long score;
}
