package adrianromanski.restschool.model.person;

import adrianromanski.restschool.domain.enums.Gender;
import adrianromanski.restschool.domain.enums.Subjects;
import adrianromanski.restschool.model.base_entity.address.TeacherAddressDTO;
import adrianromanski.restschool.model.base_entity.contact.ContactDTO;
import adrianromanski.restschool.model.base_entity.contact.TeacherContactDTO;
import adrianromanski.restschool.model.event.ExamDTO;
import adrianromanski.restschool.model.event.PaymentDTO;
import adrianromanski.restschool.model.group.StudentClassDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TeacherDTO extends PersonDTO {

    private Subjects subject;
    private LocalDate firstDay;
    private Long yearsOfExperience;

    @Builder
    public TeacherDTO(String firstName, String lastName, Gender gender,
                      LocalDate dateOfBirth, Long age, LocalDate firstDay, Subjects subject) {
        super(firstName, lastName, gender, dateOfBirth, age);
        this.firstDay = firstDay;
        this.subject = subject;
    }

    private TeacherAddressDTO addressDTO;
    private TeacherContactDTO contactDTO;

    @JsonIgnore
    private List<ExamDTO> examsDTO = new ArrayList<>();

    @JsonIgnore
    private List<PaymentDTO> paymentsDTO = new ArrayList<>();

    @JsonIgnore
    private StudentClassDTO studentClassDTO;

}
