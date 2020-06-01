package adrianromanski.restschool.model.event;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@Getter
public class ExamResultListDTO {
    private final List<ExamResultDTO> examResultDTOList;
}
