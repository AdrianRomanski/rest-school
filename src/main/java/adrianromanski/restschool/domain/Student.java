package adrianromanski.restschool.domain;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(exclude = "subjects")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "students")
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

    public void addSubject(Subject subject) {
        this.subjects.add(subject);
    }

}
