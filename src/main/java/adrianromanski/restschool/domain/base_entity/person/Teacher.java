package adrianromanski.restschool.domain.base_entity.person;

import adrianromanski.restschool.domain.base_entity.event.Exam;
import adrianromanski.restschool.domain.base_entity.group.StudentClass;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(exclude = {"exams", "studentClass"})
@Data
@NoArgsConstructor
@Entity
public class Teacher extends Person {

    @ToString.Exclude
    @OneToMany(mappedBy = "teacher")
    private List<Exam> exams = new ArrayList<>();

    @ToString.Exclude
    @OneToOne
    private StudentClass studentClass;
}
