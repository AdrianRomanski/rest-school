package adrianromanski.restschool.model.base_entity.person;

import adrianromanski.restschool.domain.base_entity.person.enums.Gender;
import adrianromanski.restschool.model.base_entity.event.ExamDTO;
import adrianromanski.restschool.model.base_entity.event.PaymentDTO;
import adrianromanski.restschool.model.base_entity.group.StudentClassDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class TeacherDTO extends PersonDTO {

    private String specialization;
    private LocalDate firstDay;
    private Long yearsOfExperience;

    @Builder
    public TeacherDTO(String firstName, String lastName, Gender gender, LocalDate dateOfBirth, Long age) {
        super(firstName, lastName, gender, dateOfBirth, age);
    }

    @JsonIgnore
    private List<ExamDTO> examsDTO = new ArrayList<>();

    @JsonIgnore
    private List<PaymentDTO> paymentsDTO = new ArrayList<>();

    @JsonIgnore
    private StudentClassDTO studentClassDTO;

}
