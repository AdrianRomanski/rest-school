package adrianromanski.restschool.model.person;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class GuardianListDTO {
   private final List<GuardianDTO> guardianDTOList;
}
