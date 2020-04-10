package adrianromanski.restschool.domain.base_entity.person;

import adrianromanski.restschool.domain.base_entity.Subject;
import adrianromanski.restschool.domain.base_entity.event.Exam;
import adrianromanski.restschool.domain.base_entity.group.StudentClass;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(exclude = {"subjects", "exams", "studentClass"})
@Entity

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

    @ManyToOne
    private StudentClass studentClass;


    public void addSubject(Subject subject) {
        this.subjects.add(subject);
    }

    public void addExam(Exam exam) { this.exams.add(exam); }


}
