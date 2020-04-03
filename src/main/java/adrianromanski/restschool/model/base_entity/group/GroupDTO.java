package adrianromanski.restschool.model.base_entity.group;


import adrianromanski.restschool.model.base_entity.BaseEntityDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.MappedSuperclass;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public class GroupDTO extends BaseEntityDTO {

    private String name;
    private String president;
}
