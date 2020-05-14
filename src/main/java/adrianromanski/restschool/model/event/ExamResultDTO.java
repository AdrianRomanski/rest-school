package adrianromanski.restschool.model.event;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Getter
public class ExamResultDTO extends EventDTO {

    private ExamDTO examDTO;
    private float score;
    private String grade;

    @Builder
    public ExamResultDTO(String name, LocalDate date, float score) {
        super(name, date);
        this.score = score;
    }
}
