package adrianromanski.restschool.model.event;

import adrianromanski.restschool.model.person.TeacherDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PaymentDTO extends EventDTO {

    private Double amount;
    private TeacherDTO teacherDTO;
}
