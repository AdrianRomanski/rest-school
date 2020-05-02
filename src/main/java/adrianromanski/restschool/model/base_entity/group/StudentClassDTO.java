package adrianromanski.restschool.model.base_entity.group;

import adrianromanski.restschool.domain.base_entity.enums.Subjects;
import adrianromanski.restschool.model.base_entity.person.StudentDTO;
import adrianromanski.restschool.model.base_entity.person.TeacherDTO;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class StudentClassDTO extends GroupDTO {

    private Subjects subject;

    @Builder
    public StudentClassDTO(String name, String president, Subjects subject) {
        super(name, president);
        this.subject = subject;
    }

    private TeacherDTO teacherDTO;

    private List<StudentDTO> studentDTOList = new ArrayList<>();
}
