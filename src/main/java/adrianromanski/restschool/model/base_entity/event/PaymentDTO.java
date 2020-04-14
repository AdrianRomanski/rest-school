package adrianromanski.restschool.model.base_entity.event;

import adrianromanski.restschool.model.base_entity.person.TeacherDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PaymentDTO extends EventDTO {

    private Double amount;
    private TeacherDTO teacherDTO;
}
