package adrianromanski.restschool.domain.base_entity.person;

import adrianromanski.restschool.domain.base_entity.event.Exam;
import adrianromanski.restschool.domain.base_entity.group.StudentClass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(exclude = {"exams", "studentClass"})
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Teacher extends Person {

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.PERSIST)
    private List<Exam> exams = new ArrayList<>();

    @OneToOne
    private StudentClass studentClass;

}
