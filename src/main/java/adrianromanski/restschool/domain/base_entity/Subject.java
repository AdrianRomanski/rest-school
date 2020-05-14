package adrianromanski.restschool.domain.base_entity;

import adrianromanski.restschool.domain.enums.Subjects;
import adrianromanski.restschool.domain.event.Exam;
import adrianromanski.restschool.domain.person.Student;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(exclude = {"students", "exams"})
@Data
@Entity
@NoArgsConstructor
public class Subject extends BaseEntity {

    private Subjects name;
    private Long value;

    @Builder
    public Subject(Subjects name, Long value) {
        this.name = name;
        this.value = value;
    }

    @JsonBackReference
    @ToString.Exclude
    @ManyToMany(mappedBy = "subjects")
    private Set<Student> students = new HashSet<>();

    @JsonBackReference
    @ToString.Exclude
    @OneToMany(mappedBy = "subject")
    private Set<Exam> exams = new HashSet<>();

    public void addStudent(Student student) {
        this.students.add(student);
    }

    public void addExam(Exam exam) { this.exams.add(exam); }
}
