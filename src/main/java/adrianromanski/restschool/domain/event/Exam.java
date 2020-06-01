package adrianromanski.restschool.domain.event;

import adrianromanski.restschool.domain.person.Student;
import adrianromanski.restschool.domain.base_entity.Subject;
import adrianromanski.restschool.domain.person.Teacher;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
@Setter
@Getter
@NoArgsConstructor
public class Exam extends Event {

    private Long maxPoints;

    @Builder
    public Exam(String name, LocalDate date, Long maxPoints) {
        super(name, date);
        this.maxPoints = maxPoints;
    }

    @JsonBackReference
    @ManyToMany(mappedBy = "exams")
    private List<Student> students = new ArrayList<>();

    @JsonBackReference
    @OneToMany(mappedBy = "exam", cascade = CascadeType.PERSIST)
    private List<ExamResult> results = new ArrayList<>();

    @ManyToOne
    private Subject subject;

    @JsonBackReference
    @ManyToOne
    private Teacher teacher;

}
