package adrianromanski.restschool.model.person;

import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
public class TeacherListDTO {
    List<TeacherDTO> teachers;
}
