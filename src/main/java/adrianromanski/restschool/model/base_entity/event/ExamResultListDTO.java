package adrianromanski.restschool.model.base_entity.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExamResultListDTO {
    List<ExamResultDTO> examResultDTOList;
}
