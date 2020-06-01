package adrianromanski.restschool.model.person;

import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
public class StudentListDTO {
    private final List<StudentDTO> students;
}
