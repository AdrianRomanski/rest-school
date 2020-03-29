package adrianromanski.restschool.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExamListDTO {
    List<ExamDTO> examDTOList;
}
