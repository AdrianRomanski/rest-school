package adrianromanski.restschool.model.base_entity.group;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class StudentClassListDTO {

    private List<StudentClassDTO> studentClasses;

}
