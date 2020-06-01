package adrianromanski.restschool.model.event;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
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
