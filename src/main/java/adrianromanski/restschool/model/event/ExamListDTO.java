package adrianromanski.restschool.model.event;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@Getter
public class ExamListDTO {
    List<ExamDTO> examDTOList;
}
