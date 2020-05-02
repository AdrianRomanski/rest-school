package adrianromanski.restschool.domain.base_entity.event;

import adrianromanski.restschool.domain.base_entity.person.Student;
import adrianromanski.restschool.domain.base_entity.Subject;
import adrianromanski.restschool.domain.base_entity.person.Teacher;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(exclude = {"students", "subject", "results", "teacher"})
@Data
@NoArgsConstructor
@Entity
public class Exam extends Event {

    private Long maxPoints;

    @Builder
    public Exam(String name, LocalDate date, Long maxPoints) {
        super(name, date);
        this.maxPoints = maxPoints;
    }

    @JsonBackReference
    @ToString.Exclude
    @ManyToMany(mappedBy = "exams")
    private List<Student> students = new ArrayList<>();

    @JsonBackReference
    @ToString.Exclude
    @OneToMany(mappedBy = "exam", cascade = CascadeType.PERSIST)
    private List<ExamResult> results = new ArrayList<>();

    @ManyToOne
    private Subject subject;

    @JsonBackReference
    @ManyToOne
    private Teacher teacher;

    public void addResult(ExamResult examResult) {
        this.results.add(examResult);
    }
}
