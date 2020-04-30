package adrianromanski.restschool.domain.base_entity.group;

import adrianromanski.restschool.domain.base_entity.enums.Specialization;
import adrianromanski.restschool.domain.base_entity.person.Student;
import adrianromanski.restschool.domain.base_entity.person.Teacher;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class StudentClass extends Group {

    private Specialization specialization;

    @Builder
    public StudentClass(String name, String president, Specialization specialization) {
        super(name, president);
        this.specialization = specialization;
    }

    @OneToOne
    private Teacher teacher;

    @JsonBackReference
    @OneToMany(mappedBy = "studentClass")
    private List<Student> studentList = new ArrayList<>();


}
