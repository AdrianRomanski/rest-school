package adrianromanski.restschool.domain.base_entity.person;

import adrianromanski.restschool.domain.base_entity.event.Exam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(exclude = {"exams"})
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Teacher extends Person {

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.PERSIST)
    private List<Exam> exams = new ArrayList<>();


}
