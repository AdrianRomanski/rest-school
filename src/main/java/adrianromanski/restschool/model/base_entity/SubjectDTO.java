package adrianromanski.restschool.model.base_entity;

import adrianromanski.restschool.domain.base_entity.BaseEntity;
import adrianromanski.restschool.domain.base_entity.enums.Subjects;
import adrianromanski.restschool.model.base_entity.event.ExamDTO;
import adrianromanski.restschool.model.base_entity.person.StudentDTO;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SubjectDTO extends BaseEntity {

    private Subjects name;
    private Long value;

    @Builder
    public SubjectDTO(Subjects name, Long value) {
        this.name = name;
        this.value = value;
    }

    private List<StudentDTO> studentsDTO = new ArrayList<>();
    private List<ExamDTO> examsDTO = new ArrayList<>();

}
