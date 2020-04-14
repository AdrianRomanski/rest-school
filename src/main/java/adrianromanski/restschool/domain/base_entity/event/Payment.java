package adrianromanski.restschool.domain.base_entity.event;

import adrianromanski.restschool.domain.base_entity.person.Teacher;
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
