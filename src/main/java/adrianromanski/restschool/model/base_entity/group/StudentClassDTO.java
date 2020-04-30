package adrianromanski.restschool.model.base_entity.group;

import adrianromanski.restschool.domain.base_entity.enums.Specialization;
import adrianromanski.restschool.model.base_entity.person.StudentDTO;
import adrianromanski.restschool.model.base_entity.person.TeacherDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class StudentClassDTO extends GroupDTO {

    private Specialization specialization;

    @Builder
    public StudentClassDTO(String name, String president, Specialization specialization) {
        super(name, president);
        this.specialization = specialization;
    }

    private TeacherDTO teacherDTO;

    private List<StudentDTO> studentDTOList = new ArrayList<>();
}
