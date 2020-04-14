package adrianromanski.restschool.model.base_entity.person;

import adrianromanski.restschool.model.base_entity.event.ExamDTO;
import adrianromanski.restschool.model.base_entity.event.PaymentDTO;
import adrianromanski.restschool.model.base_entity.group.StudentClassDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class TeacherDTO extends PersonDTO {

    private List<ExamDTO> examsDTO = new ArrayList<>();

    private List<PaymentDTO> paymentsDTO = new ArrayList<>();

    private StudentClassDTO studentClassDTO;

}
