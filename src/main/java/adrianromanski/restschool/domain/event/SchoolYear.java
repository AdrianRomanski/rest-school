package adrianromanski.restschool.domain.event;

import adrianromanski.restschool.domain.group.SportTeam;
import adrianromanski.restschool.domain.group.StudentClass;
import adrianromanski.restschool.domain.group.TeachingStaff;
import adrianromanski.restschool.domain.person.Director;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class SchoolYear extends Event{

    @Builder
    public SchoolYear(String name, LocalDate date) {
        super(name, date);
    }

    @OneToOne
    private TeachingStaff teachingStaff;

    @OneToMany
    private List<StudentClass> studentClasses = new ArrayList<>();

    @OneToMany
    private List<SportTeam> sportTeams = new ArrayList<>();

    @ManyToOne
    private Director director;
}
