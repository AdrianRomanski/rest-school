package adrianromanski.restschool.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(exclude = {"subjects", "exams"})
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Student extends Person {


    @ToString.Exclude
    @ManyToMany
    @JoinTable(name = "student_subjects", joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id"))
    private List<Subject> subjects = new ArrayList<>();

    @ToString.Exclude
    @ManyToMany
    @JoinTable(name = "student_exams", joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "exam_id"))
    private List<Exam> exams = new ArrayList<>();


    public void addSubject(Subject subject) {
        this.subjects.add(subject);
    }

    public void addExam(Exam exam) { this.exams.add(exam); }


}
