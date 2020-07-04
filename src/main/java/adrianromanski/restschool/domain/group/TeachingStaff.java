package adrianromanski.restschool.domain.group;

import adrianromanski.restschool.domain.person.Teacher;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class TeachingStaff extends Group {

    @OneToMany(mappedBy = "teachingStaff", cascade = CascadeType.ALL)
    private List<Teacher> teachers = new ArrayList<>();

    @Builder
    public TeachingStaff(String name, String president) {
        super(name, president);
    }
}
