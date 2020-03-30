package adrianromanski.restschool.domain.base_entity.event;

import adrianromanski.restschool.domain.base_entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public class Event extends BaseEntity {

    private String name;
    private LocalDate date;
}
