package adrianromanski.restschool.model.base_entity.person;

import adrianromanski.restschool.model.base_entity.event.ExamDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherDTO extends PersonDTO {

    private List<ExamDTO> examsDTO = new ArrayList<>();

}
