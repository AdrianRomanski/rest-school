package adrianromanski.restschool.model.group;

import adrianromanski.restschool.domain.enums.Sport;
import adrianromanski.restschool.model.person.StudentDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class SportTeamDTO extends GroupDTO{

    private Sport sport;

    @Builder
    public SportTeamDTO(String name, String president, Sport sport) {
        super(name, president);
        this.sport = sport;
    }

    private List<StudentDTO> studentsDTO = new ArrayList<>();

    @JsonIgnore
    public int getStudentsSize() {
        return this.studentsDTO.size();
    }
}
