package adrianromanski.restschool.bootstrap;

import adrianromanski.restschool.domain.base_entity.event.Exam;
import adrianromanski.restschool.domain.base_entity.event.ExamResult;
import adrianromanski.restschool.domain.base_entity.group.StudentClass;
import adrianromanski.restschool.domain.base_entity.person.Student;
import adrianromanski.restschool.domain.base_entity.Subject;
import adrianromanski.restschool.domain.base_entity.person.Teacher;
import adrianromanski.restschool.repositories.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class SchoolBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;
    private final ExamRepository examRepository;
    private final ExamResultRepository examResultRepository;
    private final TeacherRepository teacherRepository;
    private final StudentClassRepository studentClassRepository;

    public SchoolBootstrap(StudentRepository studentRepository, SubjectRepository subjectRepository, ExamRepository examRepository, ExamResultRepository examResultRepository,
                           TeacherRepository teacherRepository, StudentClassRepository studentClassRepository) {
        this.studentRepository = studentRepository;
        this.subjectRepository = subjectRepository;
        this.examRepository = examRepository;
        this.examResultRepository = examResultRepository;
        this.teacherRepository = teacherRepository;
        this.studentClassRepository = studentClassRepository;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        // Init Class
        StudentClass studentClass = new StudentClass();
        studentClass.setName("Rookies");

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
        students.add(adrian);
        students.add(piotrek);
        students.add(filip);

        //Init Teacher
        Teacher mathTeacher = new Teacher();
        mathTeacher.setFirstName("Walter");
        mathTeacher.setLastName("White");


        // Init Exams //
        // Math Exam
        Exam mathExam = new Exam();
        mathExam.setName("First math exam");
        mathExam.setMaxPoints(80L);
        mathExam.setDate(LocalDate.now());
        // Assign students to math Exam
        mathExam.setStudents(students);
        // Assign Exam to Teacher
        mathTeacher.getExams().add(mathExam);
        // Assign teacher to exam
        mathExam.setTeacher(mathTeacher);

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

        // Assign Student and Teacher to Class
        studentClass.setTeacher(mathTeacher);
        studentClass.setStudentList(students);
        adrian.setStudentClass(studentClass);
        filip.setStudentClass(studentClass);
        piotrek.setStudentClass(studentClass);

        System.out.println(studentClass.getTeacher());


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
        //Teachers
        teacherRepository.save(mathTeacher);
        // StudentClasses
        studentClassRepository.save(studentClass);



        // Logging to console
        log.info("Saved: " + studentRepository.count() + " Students");
        log.info("Saved: " + subjectRepository.count() + " Subjects");
        log.info("Saved: " + examRepository.count() + " Exams");
        log.info("Saved: " + examResultRepository.count() + " Exam Results");
        log.info("Saved: " + teacherRepository.count() + " Teachers");
        log.info("Saved: " + studentClassRepository.count() + " Student Classes");
    }
}
