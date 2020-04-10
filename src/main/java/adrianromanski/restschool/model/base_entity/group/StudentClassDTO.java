package adrianromanski.restschool.model.base_entity.group;

import adrianromanski.restschool.model.base_entity.person.StudentDTO;
import adrianromanski.restschool.model.base_entity.person.TeacherDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class StudentClassDTO extends GroupDTO {

    private TeacherDTO teacherDTO;

    private List<StudentDTO> studentDTOList = new ArrayList<>();
}
