package adrianromanski.restschool.model.person;


import adrianromanski.restschool.domain.enums.Gender;
import adrianromanski.restschool.model.base_entity.SubjectDTO;
import adrianromanski.restschool.model.base_entity.address.StudentAddressDTO;
import adrianromanski.restschool.model.base_entity.contact.StudentContactDTO;
import adrianromanski.restschool.model.event.ExamDTO;
import adrianromanski.restschool.model.group.SportTeamDTO;
import adrianromanski.restschool.model.group.StudentClassDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class StudentDTO extends PersonDTO {

    @Builder
    public StudentDTO(String firstName, String lastName, Gender gender, LocalDate dateOfBirth, Long age) {
        super(firstName, lastName, gender, dateOfBirth, age);
    }

    private StudentContactDTO contactDTO;
    private StudentAddressDTO addressDTO;

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
