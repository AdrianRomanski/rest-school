package adrianromanski.restschool.domain.base_entity.event;

import adrianromanski.restschool.domain.base_entity.person.Student;
import adrianromanski.restschool.domain.base_entity.Subject;
import adrianromanski.restschool.domain.base_entity.person.Teacher;
import lombok.*;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(exclude = {"students", "subject", "results", "teacher"})
@Data
@NoArgsConstructor
@Entity
public class Exam extends Event {

    private Long maxPoints;

    @ToString.Exclude
    @ManyToMany(mappedBy = "exams")
    private List<Student> students = new ArrayList<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "exam", cascade = CascadeType.PERSIST)
    private List<ExamResult> results = new ArrayList<>();

    @ManyToOne
    private Subject subject;

    @ManyToOne
    private Teacher teacher;

    public void addResult(ExamResult examResult) {
        this.results.add(examResult);
    }
}
