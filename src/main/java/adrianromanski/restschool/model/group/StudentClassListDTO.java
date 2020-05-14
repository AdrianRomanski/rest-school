package adrianromanski.restschool.model.group;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class StudentClassListDTO {

    private List<StudentClassDTO> studentClasses;

}
