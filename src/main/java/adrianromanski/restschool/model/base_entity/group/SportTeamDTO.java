package adrianromanski.restschool.model.base_entity.group;

import adrianromanski.restschool.model.base_entity.person.StudentDTO;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class SportTeamDTO extends GroupDTO{

    @Builder
    public SportTeamDTO(String name, String president) {
        super(name, president);
    }

    private List<StudentDTO> studentsDTO = new ArrayList<>();
}
