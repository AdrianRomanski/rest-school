package adrianromanski.restschool.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "subjects")
public class Subject extends BaseEntity{

    private String name;
    private Long value;

    @ToString.Exclude
    @ManyToMany(mappedBy = "subjects")
    private Set<Student> students = new HashSet<>();

    public void addStudent(Student student) {
        this.students.add(student);
    }
}
