package adrianromanski.restschool.domain;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"students"})

public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private Long value;

    @ToString.Exclude
    @ManyToMany
    private Set<Student> students = new HashSet<>();

    public void addStudent(Student student) {
        this.students.add(student);
    }
}
