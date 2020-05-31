package adrianromanski.restschool.domain.event;

import adrianromanski.restschool.domain.person.Teacher;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Payment extends Event {

    private Double amount;

    @ManyToOne
    private Teacher teacher;

    @Builder
    public Payment(String name, LocalDate date, Double amount) {
        super(name, date);
        this.amount = amount;
    }
}
