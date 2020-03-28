package adrianromanski.restschool.domain;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(exclude = {"students", "exams"})
@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Subject extends BaseEntity{

    private String name;
    private Long value;

    @ToString.Exclude
    @ManyToMany(mappedBy = "subjects")
    private Set<Student> students = new HashSet<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "subject", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Set<Exam> exams = new HashSet<>();

    public void addStudent(Student student) {
        this.students.add(student);
    }

    public void addExam(Exam exam) { this.exams.add(exam); }
}
