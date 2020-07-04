package adrianromanski.restschool.model.group;

import adrianromanski.restschool.model.person.TeacherDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TeachingStaffDTO extends GroupDTO{

    private List<TeacherDTO> teachersDTO = new ArrayList<>();

    @Builder
    public TeachingStaffDTO(String name, String president, List<TeacherDTO> teachersDTO) {
        super(name, president);
        this.teachersDTO = teachersDTO;
    }
}
