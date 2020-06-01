package adrianromanski.restschool.domain.person;

import adrianromanski.restschool.domain.base_entity.address.TeacherAddress;
import adrianromanski.restschool.domain.base_entity.contact.TeacherContact;
import adrianromanski.restschool.domain.enums.Gender;
import adrianromanski.restschool.domain.enums.Subjects;
import adrianromanski.restschool.domain.event.Exam;
import adrianromanski.restschool.domain.event.Payment;
import adrianromanski.restschool.domain.group.StudentClass;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Teacher extends SchoolWorker {

    private Subjects subject;

    @Builder
    public Teacher(String firstName, String lastName, Gender gender, LocalDate dateOfBirth,
                   Long age, Long yearsOfExperience, LocalDate firstDay, Subjects subject) {
        super(firstName, lastName, gender, dateOfBirth, age, yearsOfExperience, firstDay);
        this.subject = subject;
    }

    @OneToOne
    private StudentClass studentClass;

    @OneToOne(cascade={CascadeType.ALL})
    private TeacherAddress address;

    public Optional<TeacherAddress> getAddressOptional() {
        return Optional.ofNullable(this.address);
    }

    @OneToOne(cascade = {CascadeType.ALL})
    private TeacherContact contact;

    public Optional<TeacherContact> getContactOptional() { return Optional.ofNullable(this.contact); }

    @OneToMany(mappedBy = "teacher")
    private List<Exam> exams = new ArrayList<>();

    @OneToMany(mappedBy = "teacher")
    private List<Payment> payments = new ArrayList<>();


}
