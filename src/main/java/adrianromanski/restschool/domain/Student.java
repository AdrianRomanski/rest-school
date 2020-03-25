package adrianromanski.restschool.domain;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(exclude = {"subjects"})
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;

    @ToString.Exclude
    @ManyToMany
    private Set<Subject> subjects = new HashSet<>();

    public void addSubject(Subject subject) {
        this.subjects.add(subject);
    }

}
