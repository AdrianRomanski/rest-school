package adrianromanski.restschool.model.base_entity.person;


import adrianromanski.restschool.model.base_entity.SubjectDTO;
import adrianromanski.restschool.model.base_entity.event.ExamDTO;
import adrianromanski.restschool.model.base_entity.group.StudentClassDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO extends PersonDTO {

    private List<SubjectDTO> subjectsDTO = new ArrayList<>();

    private List<ExamDTO> examsDTO = new ArrayList<>();

    private StudentClassDTO studentClassDTO;
}
