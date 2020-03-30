package adrianromanski.restschool.model;

import adrianromanski.restschool.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubjectDTO extends BaseEntity {

    private String name;
    private Long value;

    private List<StudentDTO> studentsDTO = new ArrayList<>();
    private List<ExamDTO> examsDTO = new ArrayList<>();

}
