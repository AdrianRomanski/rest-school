package adrianromanski.restschool.model.base_entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
public class SubjectListDTO {
    List<SubjectDTO> subjects;

}
