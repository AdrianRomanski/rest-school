package adrianromanski.restschool.model.group;

import adrianromanski.restschool.domain.enums.Subjects;
import adrianromanski.restschool.model.event.SchoolYearDTO;
import adrianromanski.restschool.model.person.StudentDTO;
import adrianromanski.restschool.model.person.TeacherDTO;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class StudentClassDTO extends GroupDTO {

    private Subjects subject;

    @Builder
    public StudentClassDTO(String name, String president, Subjects subject) {
        super(name, president);
        this.subject = subject;
    }

    private TeacherDTO teacherDTO;
    private SchoolYearDTO schoolYearDTO;
    private List<StudentDTO> studentDTOList = new ArrayList<>();
}
