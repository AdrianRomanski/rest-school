package adrianromanski.restschool.model.group;


import adrianromanski.restschool.model.base_entity.BaseEntityDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.MappedSuperclass;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
@AllArgsConstructor
public class GroupDTO extends BaseEntityDTO {

    private String name;
    private String president;
}
