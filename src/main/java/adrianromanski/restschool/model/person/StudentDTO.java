package adrianromanski.restschool.model.person;


import adrianromanski.restschool.domain.enums.Gender;
import adrianromanski.restschool.model.base_entity.address.AddressDTO;
import adrianromanski.restschool.model.base_entity.ContactDTO;
import adrianromanski.restschool.model.base_entity.SubjectDTO;
import adrianromanski.restschool.model.event.ExamDTO;
import adrianromanski.restschool.model.group.SportTeamDTO;
import adrianromanski.restschool.model.group.StudentClassDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
public class StudentDTO extends PersonDTO {

    @Builder
    public StudentDTO(String firstName, String lastName, Gender gender, LocalDate dateOfBirth, Long age) {
        super(firstName, lastName, gender, dateOfBirth, age);
    }

    private ContactDTO contactDTO;

    private AddressDTO addressDTO;

    @JsonIgnore
    private List<SubjectDTO> subjectsDTO = new ArrayList<>();

    @JsonIgnore
    private List<ExamDTO> examsDTO = new ArrayList<>();

    @JsonIgnore
    private StudentClassDTO studentClassDTO;

    @JsonIgnore
    private GuardianDTO guardianDTO;

    @JsonIgnore
    private SportTeamDTO sportTeamDTO;
}
