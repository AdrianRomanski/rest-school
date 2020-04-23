package adrianromanski.restschool.model.base_entity.group;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class SportTeamListDTO {

    private List<SportTeamDTO> sportTeamDTOList;
}
