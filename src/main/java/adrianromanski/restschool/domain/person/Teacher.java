package adrianromanski.restschool.domain.person;

import adrianromanski.restschool.domain.base_entity.Contact;
import adrianromanski.restschool.domain.enums.Subjects;
import adrianromanski.restschool.domain.event.Exam;
import adrianromanski.restschool.domain.event.Payment;
import adrianromanski.restschool.domain.group.StudentClass;
import adrianromanski.restschool.domain.enums.Gender;
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

    private Subjects subject;
    private Long yearsOfExperience;
    private LocalDate firstDay;

    @Builder
    public Teacher(String firstName, String lastName, Gender gender, LocalDate dateOfBirth, Long age, Subjects subject, LocalDate firstDay) {
        super(firstName, lastName, gender, dateOfBirth, age);
        this.firstDay = firstDay;
        this.subject = subject;
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

    @OneToOne
    private Contact contact;

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
}
