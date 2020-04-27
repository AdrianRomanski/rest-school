package adrianromanski.restschool.domain.base_entity.person;

import adrianromanski.restschool.domain.base_entity.Subject;
import adrianromanski.restschool.domain.base_entity.event.Exam;
import adrianromanski.restschool.domain.base_entity.group.SportTeam;
import adrianromanski.restschool.domain.base_entity.group.StudentClass;
import adrianromanski.restschool.domain.base_entity.person.enums.Gender;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(exclude = {"subjects", "exams", "studentClass", "guardians"})
@Entity
@NoArgsConstructor
@Data
public class Student extends Person {

    @Builder
    public Student(String firstName, String lastName, Gender gender, LocalDate dateOfBirth, Long age) {
        super(firstName, lastName, gender, dateOfBirth, age);
    }

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
    private Guardian guardian;

    @ManyToOne
    private SportTeam sportTeam;

    @ToString.Exclude
    @ManyToOne
    private StudentClass studentClass;


    public void addSubject(Subject subject) {
        this.subjects.add(subject);
    }

    public void addExam(Exam exam) { this.exams.add(exam); }


}
