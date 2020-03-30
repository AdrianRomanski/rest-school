package adrianromanski.restschool.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@EqualsAndHashCode(exclude = {"exam"})
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamResult extends Event {

    @ManyToOne
    private Exam exam;

    private Long score;

}
