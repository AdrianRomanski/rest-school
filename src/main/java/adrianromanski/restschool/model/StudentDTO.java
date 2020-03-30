package adrianromanski.restschool.model;


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
}
