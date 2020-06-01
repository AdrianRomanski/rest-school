package adrianromanski.restschool.domain.group;

import adrianromanski.restschool.domain.enums.Subjects;
import adrianromanski.restschool.domain.person.Student;
import adrianromanski.restschool.domain.person.Teacher;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class StudentClass extends Group {

    private Subjects subject;

    @Builder
    public StudentClass(String name, String president, Subjects subject) {
        super(name, president);
        this.subject = subject;
    }

    @OneToOne
    private Teacher teacher;

    @JsonBackReference
    @OneToMany(mappedBy = "studentClass")
    private List<Student> studentList = new ArrayList<>();


}
