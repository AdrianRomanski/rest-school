package adrianromanski.restschool.domain.base_entity.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@EqualsAndHashCode(exclude = {"exam"})
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamResult extends Event {

    @ManyToOne
    private Exam exam;

    private float score;

    private String grade;

    public void setScore(float score) {
        this.score = score;
        setGrade(score);
    }

    private void setGrade(float score) {
        if(this.exam != null && score >= 0 && score <= this.exam.getMaxPoints()) {
            Long maxPoints = this.exam.getMaxPoints();
            float result = (score / maxPoints) * 100;
            if(result >= 85) {
                this.grade = "A";
            } else if(result >= 70 && result < 85) {
                this.grade = "B";
            } else if(result >= 55 && result < 70) {
                this.grade = "C";
            } else if(result >= 45 && result < 55) {
                this.grade = "D";
            } else if(result >= 35 && result < 45)  {
                this.grade = "E";
            } else if(result > 35) {
                this.grade = "F";
            }
        }
    }
}
