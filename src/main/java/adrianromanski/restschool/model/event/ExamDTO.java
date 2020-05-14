package adrianromanski.restschool.model.event;

import adrianromanski.restschool.domain.event.Event;
import adrianromanski.restschool.model.person.StudentDTO;
import adrianromanski.restschool.model.base_entity.SubjectDTO;
import adrianromanski.restschool.model.person.TeacherDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ExamDTO extends Event {

    private Long maxPoints;

    @Builder
    public ExamDTO(String name, LocalDate date, Long maxPoints) {
        super(name, date);
        this.maxPoints = maxPoints;
    }

    @JsonIgnore
    private List<StudentDTO> studentsDTO = new ArrayList<>();

    private SubjectDTO subjectDTO;
    private List<ExamResultDTO> resultsDTO = new ArrayList<>();
    private TeacherDTO teacherDTO;
}
