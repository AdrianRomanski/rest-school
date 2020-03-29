package adrianromanski.restschool.bootstrap;

import adrianromanski.restschool.domain.Exam;
import adrianromanski.restschool.domain.Student;
import adrianromanski.restschool.domain.Subject;
import adrianromanski.restschool.model.SubjectDTO;
import adrianromanski.restschool.repositories.ExamRepository;
import adrianromanski.restschool.repositories.StudentRepository;
import adrianromanski.restschool.repositories.SubjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Component
@Slf4j
public class SchoolBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;
    private final ExamRepository examRepository;

    public SchoolBootstrap(StudentRepository studentRepository, SubjectRepository subjectRepository, ExamRepository examRepository) {
        this.studentRepository = studentRepository;
        this.subjectRepository = subjectRepository;
        this.examRepository = examRepository;
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

        // Init set of Students
        Set<Student> students = new HashSet<>();
        students.add(filip);
        students.add(adrian);
        students.add(piotrek);

        // Init Exams
        Exam mathExam = new Exam();
        mathExam.setName("First math exam");
        mathExam.setMaxPoints(80L);
        mathExam.setDate(LocalDate.now());
        // Assign students to Exam
        mathExam.setStudents(students);

        Exam biologyExam = new Exam();
        biologyExam.setName("Second biology exam");
        biologyExam.setMaxPoints(60L);
        biologyExam.setDate(LocalDate.now());
        // Assign students to Exam
        biologyExam.setStudents(students);


        // Assign exam to Students
        adrian.addExam(mathExam);
        adrian.addExam(biologyExam);
        filip.addExam(mathExam);
        piotrek.addExam(mathExam);


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


        System.out.println(mathExam.getSubject());


        // Saving to repositories
        studentRepository.save(adrian);
        studentRepository.save(filip);
        studentRepository.save(piotrek);
        subjectRepository.save(biology);
        subjectRepository.save(math);
        examRepository.save(mathExam);
        examRepository.save(biologyExam);

        // Logging to console
        log.info("Saved: " + studentRepository.count() + " Students");
        log.info("Saved: " + subjectRepository.count() + " Subjects");
        log.info("Saved: " + examRepository.count() + " Exams");
    }
}
