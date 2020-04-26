package adrianromanski.restschool.domain.base_entity.person;

import adrianromanski.restschool.domain.base_entity.event.Exam;
import adrianromanski.restschool.domain.base_entity.event.Payment;
import adrianromanski.restschool.domain.base_entity.group.StudentClass;
import adrianromanski.restschool.domain.base_entity.person.enums.Gender;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Teacher extends Person {


    private String specialization;

    private Long yearsOfExperience;

    private LocalDate firstDay;

    public Long getYearsOfExperience() {
        if (firstDay != null) {
            LocalDate now = LocalDate.now();
            Period period = Period.between(this.firstDay, now);
            this.yearsOfExperience = (long) period.getYears();
            return this.yearsOfExperience;
        } else {
            return 0L;
        }
    }

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
