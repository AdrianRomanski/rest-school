package adrianromanski.restschool.domain.base_entity.group;

import adrianromanski.restschool.domain.base_entity.person.Student;
import adrianromanski.restschool.domain.base_entity.person.Teacher;
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

    @Builder
    public StudentClass(String name, String president) {
        super(name, president);
    }

    @OneToOne
    private Teacher teacher;

    @OneToMany(mappedBy = "studentClass")
    private List<Student> studentList = new ArrayList<>();


}
