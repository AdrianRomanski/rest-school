package adrianromanski.restschool.model.base_entity.event;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@Getter
public class ExamListDTO {
    List<ExamDTO> examDTOList;
}
