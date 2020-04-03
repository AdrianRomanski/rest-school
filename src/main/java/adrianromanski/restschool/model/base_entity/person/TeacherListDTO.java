package adrianromanski.restschool.model.base_entity.person;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherListDTO {
    List<TeacherDTO> teachers;
}
