package adrianromanski.restschool.model.base_entity.event;

import adrianromanski.restschool.model.base_entity.BaseEntityDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class EventDTO extends BaseEntityDTO {

    private String name;
    private LocalDate date;
}
