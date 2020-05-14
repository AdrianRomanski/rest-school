package adrianromanski.restschool.repositories.group;

import adrianromanski.restschool.domain.group.SportTeam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SportTeamRepository extends JpaRepository<SportTeam, Long> {

    Optional<SportTeam> getSportTeamByName(String name);
}
