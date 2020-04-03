package adrianromanski.restschool.domain.base_entity.group;

import adrianromanski.restschool.domain.base_entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.MappedSuperclass;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public class Group extends BaseEntity {

    private String name;
    private String president;

}
