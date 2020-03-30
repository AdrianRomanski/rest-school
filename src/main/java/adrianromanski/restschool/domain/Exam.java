package adrianromanski.restschool.domain;

import lombok.*;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(exclude = {"students", "subject", "subject"})
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Exam extends Event {

    private Long maxPoints;

    @ToString.Exclude
    @ManyToMany(mappedBy = "exams")
    private List<Student> students = new ArrayList<>();

    @OneToMany(mappedBy = "exam", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private List<ExamResult> results = new ArrayList<>();

    @ManyToOne
    private Subject subject;

    public void addResult(ExamResult examResult) {
        this.results.add(examResult);
    }
}
