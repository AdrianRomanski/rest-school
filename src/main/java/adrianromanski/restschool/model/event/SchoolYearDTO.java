package adrianromanski.restschool.model.event;

import adrianromanski.restschool.domain.group.StudentClass;
import adrianromanski.restschool.model.group.SportTeamDTO;
import adrianromanski.restschool.model.group.StudentClassDTO;
import adrianromanski.restschool.model.group.TeachingStaffDTO;
import adrianromanski.restschool.model.person.DirectorDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SchoolYearDTO extends EventDTO {

    @Builder
    public SchoolYearDTO(String name, LocalDate date) {
        super(name, date);
    }

    private TeachingStaffDTO teachingStaffDTO;
    private List<StudentClassDTO> studentClassesDTO = new ArrayList<>();
    private List<SportTeamDTO> sportTeamsDTO = new ArrayList<>();
    private DirectorDTO directorDTO;
}
