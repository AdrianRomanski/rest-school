package adrianromanski.restschool.bootstrap;

import adrianromanski.restschool.domain.Student;
import adrianromanski.restschool.domain.Subject;
import adrianromanski.restschool.repositories.StudentRepository;
import adrianromanski.restschool.repositories.SubjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
public class SchoolBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;

    public SchoolBootstrap(StudentRepository studentRepository, SubjectRepository subjectRepository) {
        this.studentRepository = studentRepository;
        this.subjectRepository = subjectRepository;
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

        // Assign Subjects to Students
        adrian.addSubject(math);
        adrian.addSubject(biology);

        // Assign Students to Subjects
        biology.addStudent(adrian);
        math.addStudent(adrian);

        // Saving to repositories
        studentRepository.save(adrian);
        subjectRepository.save(biology);
        subjectRepository.save(math);

        // Logging to console
        log.info("Saved: " + studentRepository.count() + " Students");
        log.info("Saved: " + subjectRepository.count() + " Subjects");
    }
}
