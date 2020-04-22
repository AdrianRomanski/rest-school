package adrianromanski.restschool.repositories.group;

import adrianromanski.restschool.domain.base_entity.group.SportTeam;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SportTeamRepository extends JpaRepository<SportTeam, Long> {
}
