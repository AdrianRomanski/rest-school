package adrianromanski.restschool.model.base_entity;

import adrianromanski.restschool.model.base_entity.SubjectDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectListDTO {
    List<SubjectDTO> subjects;

}
