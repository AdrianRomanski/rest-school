package adrianromanski.restschool.model.group;

import adrianromanski.restschool.model.event.SchoolYearDTO;
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

    @Builder
    public TeachingStaffDTO(String name, String president,
                            SchoolYearDTO schoolYearDTO, List<TeacherDTO> teachersDTO) {
        super(name, president);
        this.schoolYearDTO = schoolYearDTO;
        this.teachersDTO = teachersDTO;
    }

    private SchoolYearDTO schoolYearDTO;
    private List<TeacherDTO> teachersDTO = new ArrayList<>();

}
