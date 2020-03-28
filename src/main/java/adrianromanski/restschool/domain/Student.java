package adrianromanski.restschool.domain;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(exclude = {"subjects", "exams"})
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Student extends Person {

    @Builder
    public Student(Long id, String firstName, String lastName) {
        super(id, firstName, lastName);
    }

    @ToString.Exclude
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "student_subjects", joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id"))
    private Set<Subject> subjects = new HashSet<>();

    @ToString.Exclude
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "student_exams", joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "exam_id"))
    private Set<Exam> exams = new HashSet<>();

    public void addSubject(Subject subject) {
        this.subjects.add(subject);
    }

    public void addExam(Exam exam) { this.exams.add(exam); }

}
