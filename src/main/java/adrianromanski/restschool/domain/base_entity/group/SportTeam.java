package adrianromanski.restschool.domain.base_entity.group;

import adrianromanski.restschool.domain.base_entity.enums.Sport;
import adrianromanski.restschool.domain.base_entity.person.Student;
import adrianromanski.restschool.domain.base_entity.person.Teacher;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(exclude = {"students"})
@Data
@Entity
@NoArgsConstructor
public class SportTeam extends Group {

    Sport sport;

    @Builder
    public SportTeam(String name, String president, Sport sport) {
        super(name, president);
        this.sport = sport;
    }

    @ToString.Exclude
    @OneToMany(mappedBy = "sportTeam")
    private List<Student> students = new ArrayList<>();

}
