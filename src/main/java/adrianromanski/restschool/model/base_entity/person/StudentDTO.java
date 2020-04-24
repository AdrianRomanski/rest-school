package adrianromanski.restschool.model.base_entity.person;


import adrianromanski.restschool.domain.base_entity.person.enums.Gender;
import adrianromanski.restschool.model.base_entity.SubjectDTO;
import adrianromanski.restschool.model.base_entity.event.ExamDTO;
import adrianromanski.restschool.model.base_entity.group.SportTeamDTO;
import adrianromanski.restschool.model.base_entity.group.StudentClassDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO extends PersonDTO {

    @Builder
    public StudentDTO(String firstName, String lastName, Gender gender) {
        super(firstName, lastName, gender);
    }

    @JsonIgnore
    private List<SubjectDTO> subjectsDTO = new ArrayList<>();

    @JsonIgnore
    private List<ExamDTO> examsDTO = new ArrayList<>();

    private StudentClassDTO studentClassDTO;

    private SportTeamDTO sportTeamDTO;
}
