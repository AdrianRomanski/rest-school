package adrianromanski.restschool.model.base_entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class SubjectListDTO {
    private final List<SubjectDTO> subjects;
}
