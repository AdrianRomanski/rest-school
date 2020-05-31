package adrianromanski.restschool.domain.base_entity;

import adrianromanski.restschool.domain.enums.Subjects;
import adrianromanski.restschool.domain.event.Exam;
import adrianromanski.restschool.domain.person.Student;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
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
    @ManyToMany(mappedBy = "subjects")
    private Set<Student> students = new HashSet<>();

    @JsonBackReference
    @OneToMany(mappedBy = "subject")
    private Set<Exam> exams = new HashSet<>();

}
