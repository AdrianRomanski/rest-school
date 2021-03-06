package adrianromanski.restschool.domain.group;

import adrianromanski.restschool.domain.enums.Sport;
import adrianromanski.restschool.domain.event.SchoolYear;
import adrianromanski.restschool.domain.person.Student;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class SportTeam extends Group {

    Sport sport;

    @Builder
    public SportTeam(String name, String president, Sport sport) {
        super(name, president);
        this.sport = sport;
    }

    @OneToOne
    private SchoolYear schoolYear;

    @OneToMany(mappedBy = "sportTeam")
    private List<Student> students = new ArrayList<>();


}
