package adrianromanski.restschool.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.LocalDate;

import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(exclude = {"students", "subject"})
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Exam extends BaseEntity {

    private String name;
    private LocalDate date;

    @ToString.Exclude
    @ManyToMany(mappedBy = "exams")
    private Set<Student> students = new HashSet<>();

    @ToString.Exclude
    @ManyToOne
    private Subject subject;
}
