package adrianromanski.restschool.domain.base_entity.group;

import adrianromanski.restschool.domain.base_entity.person.Student;
import adrianromanski.restschool.domain.base_entity.person.Teacher;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(exclude = {"teacher", "studentList"})
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class StudentClass extends Group {

    @OneToOne
    private Teacher teacher;

    @OneToMany(mappedBy = "studentClass")
    private List<Student> studentList = new ArrayList<>();


}
