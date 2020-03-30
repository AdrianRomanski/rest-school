package adrianromanski.restschool.domain.base_entity;

import adrianromanski.restschool.domain.base_entity.BaseEntity;
import adrianromanski.restschool.domain.base_entity.event.Exam;
import adrianromanski.restschool.domain.base_entity.person.Student;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(exclude = {"students", "exams"})
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Subject extends BaseEntity {

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