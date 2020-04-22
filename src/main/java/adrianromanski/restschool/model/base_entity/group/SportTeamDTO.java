package adrianromanski.restschool.model.base_entity.group;

import adrianromanski.restschool.model.base_entity.person.StudentDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class SportTeamDTO extends GroupDTO{

    private List<StudentDTO> studentsDTO = new ArrayList<>();
}
