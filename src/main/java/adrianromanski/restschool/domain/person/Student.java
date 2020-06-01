package adrianromanski.restschool.domain.person;

import adrianromanski.restschool.domain.base_entity.Subject;
import adrianromanski.restschool.domain.base_entity.address.StudentAddress;
import adrianromanski.restschool.domain.base_entity.contact.StudentContact;
import adrianromanski.restschool.domain.enums.Gender;
import adrianromanski.restschool.domain.event.Exam;
import adrianromanski.restschool.domain.group.SportTeam;
import adrianromanski.restschool.domain.group.StudentClass;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Student extends Person {

    @Builder
    public Student(String firstName, String lastName, Gender gender, LocalDate dateOfBirth, Long age) {
        super(firstName, lastName, gender, dateOfBirth, age);
    }

    @OneToOne(cascade={CascadeType.ALL})
    private StudentAddress address;

    public Optional<StudentAddress> getAddressOptional() {
        return Optional.ofNullable(this.getAddress());
    }

    @OneToOne(cascade={CascadeType.ALL})
    private StudentContact contact;

    public Optional<StudentContact> getContactOptional() {
        return Optional.ofNullable(this.getContact());
    }

    @ManyToOne
    private Guardian guardian;

    @ManyToOne
    private SportTeam sportTeam;

    @ManyToOne
    private StudentClass studentClass;

    @ManyToMany
    @JoinTable(name = "student_subjects", joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id"))
    private List<Subject> subjects = new ArrayList<>();


    @ManyToMany
    @JoinTable(name = "student_exams", joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "exam_id"))
    private List<Exam> exams = new ArrayList<>();

}