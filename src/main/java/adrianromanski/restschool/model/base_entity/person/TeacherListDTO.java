package adrianromanski.restschool.model.base_entity.person;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class TeacherListDTO {
    List<TeacherDTO> teachers;
}
