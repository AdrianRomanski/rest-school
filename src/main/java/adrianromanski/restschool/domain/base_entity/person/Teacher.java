package adrianromanski.restschool.domain.base_entity.person;

import adrianromanski.restschool.domain.base_entity.event.Exam;
import adrianromanski.restschool.domain.base_entity.event.Payment;
import adrianromanski.restschool.domain.base_entity.group.StudentClass;
import adrianromanski.restschool.domain.base_entity.person.enums.Gender;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(exclude = {"exams", "studentClass", "payments"})
@Data
@NoArgsConstructor
@Entity
public class Teacher extends Person {

    @Builder
    public Teacher(String firstName, String lastName, Gender gender, LocalDate dateOfBirth, Long age) {
        super(firstName, lastName, gender, dateOfBirth, age);
    }

    @ToString.Exclude
    @OneToMany(mappedBy = "teacher")
    private List<Exam> exams = new ArrayList<>();

    @ToString.Exclude
    @OneToOne
    private StudentClass studentClass;

    @ToString.Exclude
    @OneToMany(mappedBy = "teacher")
    private List<Payment> payments = new ArrayList<>();
}
