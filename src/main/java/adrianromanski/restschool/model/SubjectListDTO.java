package adrianromanski.restschool.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectListDTO {
    List<SubjectDTO> subjects;

    public void addSubject(SubjectDTO subjectDTO) {
        this.subjects.add(subjectDTO);
    }
}
