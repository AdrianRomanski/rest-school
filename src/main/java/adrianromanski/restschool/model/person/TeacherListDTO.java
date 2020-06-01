package adrianromanski.restschool.model.person;

import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
public class TeacherListDTO {
    private final List<TeacherDTO> teachers;
}
