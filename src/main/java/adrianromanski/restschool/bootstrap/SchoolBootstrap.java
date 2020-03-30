package adrianromanski.restschool.bootstrap;

import adrianromanski.restschool.domain.base_entity.event.Exam;
import adrianromanski.restschool.domain.base_entity.event.ExamResult;
import adrianromanski.restschool.domain.base_entity.person.Student;
import adrianromanski.restschool.domain.base_entity.Subject;
import adrianromanski.restschool.repositories.ExamRepository;
import adrianromanski.restschool.repositories.ExamResultRepository;
import adrianromanski.restschool.repositories.StudentRepository;
import adrianromanski.restschool.repositories.SubjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class SchoolBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;
    private final ExamRepository examRepository;
    private final ExamResultRepository examResultRepository;

    public SchoolBootstrap(StudentRepository studentRepository, SubjectRepository subjectRepository,
                           ExamRepository examRepository, ExamResultRepository examResultRepository) {
        this.studentRepository = studentRepository;
        this.subjectRepository = subjectRepository;
        this.examRepository = examRepository;
        this.examResultRepository = examResultRepository;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        // Init Subjects
        Subject math = new Subject();
        math.setName("Mathematics");
        math.setValue(10L);

        Subject biology = new Subject();
        biology.setName("Biology");
        biology.setValue(8L);

        // Init Students
        Student adrian = new Student();
        adrian.setFirstName("Adrian");
        adrian.setLastName("Romanski");

        Student piotrek = new Student();
        piotrek.setFirstName("Piotrek");
        piotrek.setLastName("Cieszynski");

        Student filip = new Student();
        filip.setFirstName("Filip");
        filip.setLastName("Konieczny");

        // Init List of Students
        List<Student> students = new ArrayList<>();
        students.add(filip);
        students.add(adrian);
        students.add(piotrek);

        // Init Exams //
        // Math Exam
        Exam mathExam = new Exam();
        mathExam.setName("First math exam");
        mathExam.setMaxPoints(80L);
        mathExam.setDate(LocalDate.now());
        // Assign students to math Exam
        mathExam.setStudents(students);

        // Biology Exam
        Exam biologyExam = new Exam();
        biologyExam.setName("Second biology exam");
        biologyExam.setMaxPoints(60L);
        biologyExam.setDate(LocalDate.now());
        // Assign students to biology Exam
        biologyExam.setStudents(students);

        // Init ExamResults
        ExamResult filipMathResult = new ExamResult();
        filipMathResult.setName(filip.getFirstName() + " " + filip.getLastName());
        filipMathResult.setExam(mathExam);
        filipMathResult.setScore(60f);
        filipMathResult.setDate(LocalDate.now());
        ExamResult adrianMathResult = new ExamResult();
        adrianMathResult.setName(adrian.getFirstName() + " " + adrian.getLastName());
        adrianMathResult.setExam(mathExam);
        adrianMathResult.setScore(45f);
        adrianMathResult.setDate(LocalDate.now());
        ExamResult piotrekMathResult = new ExamResult();
        piotrekMathResult.setName(piotrek.getFirstName() + " " + piotrek.getLastName());
        piotrekMathResult.setExam(mathExam);
        piotrekMathResult.setScore(55f);
        piotrekMathResult.setDate(LocalDate.now());


        //


        // Assign Subjects to Students
        adrian.addSubject(math);
        adrian.addSubject(biology);
        filip.addSubject(math);
        piotrek.addSubject(math);

        // Assign Students to Subjects
        biology.addStudent(adrian);
        math.addStudent(adrian);
        math.addStudent(filip);
        math.addStudent(piotrek);

        // Assign Exams to Subjects
        math.addExam(mathExam);
        biology.addExam(biologyExam);

        // Assign subject to Exam
        mathExam.setSubject(math);
        biologyExam.setSubject(biology);

        // Assign exam to Students
        adrian.addExam(mathExam);
        adrian.addExam(biologyExam);
        filip.addExam(mathExam);
        piotrek.addExam(mathExam);

        // Assign result
        adrian.getExams().get(0).addResult(adrianMathResult);
        filip.getExams().get(0).addResult(filipMathResult);
        piotrek.getExams().get(0).addResult(piotrekMathResult);



        // Saving to repositories
        //Students
        studentRepository.save(adrian);
        studentRepository.save(filip);
        studentRepository.save(piotrek);
        //Subjects
        subjectRepository.save(biology);
        subjectRepository.save(math);
        //Exams
        examRepository.save(mathExam);
        examRepository.save(biologyExam);
        //Exam Results
        examResultRepository.save(adrianMathResult);
        examResultRepository.save(piotrekMathResult);
        examResultRepository.save(filipMathResult);


        // Logging to console
        log.info("Saved: " + studentRepository.count() + " Students");
        log.info("Saved: " + subjectRepository.count() + " Subjects");
        log.info("Saved: " + examRepository.count() + " Exams");
        log.info("Saved: " + examResultRepository.count() + " Exam Results");
    }
}