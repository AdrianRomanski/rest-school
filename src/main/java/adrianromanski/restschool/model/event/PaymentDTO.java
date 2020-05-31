package adrianromanski.restschool.model.event;

import adrianromanski.restschool.model.person.TeacherDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class PaymentDTO extends EventDTO {

    private Double amount;
    private TeacherDTO teacherDTO;

    @Builder
    public PaymentDTO(String name, LocalDate date, Double amount) {
        super(name, date);
        this.amount = amount;
    }
}
