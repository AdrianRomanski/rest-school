package adrianromanski.restschool.domain.event;

import adrianromanski.restschool.domain.person.Teacher;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Data
@Entity
@NoArgsConstructor
public class Payment extends Event {

    private Double amount;

    @ManyToOne
    private Teacher teacher;

}
