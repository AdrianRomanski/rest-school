package adrianromanski.restschool.model.base_entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class SubjectListDTO {
    List<SubjectDTO> subjects;

}
