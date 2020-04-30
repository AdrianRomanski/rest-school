package adrianromanski.restschool.bootstrap;

import adrianromanski.restschool.domain.base_entity.enums.MaleName;
import adrianromanski.restschool.domain.base_entity.enums.Specialization;
import adrianromanski.restschool.domain.base_entity.event.Exam;
import adrianromanski.restschool.domain.base_entity.event.ExamResult;
import adrianromanski.restschool.domain.base_entity.group.StudentClass;
import adrianromanski.restschool.domain.base_entity.person.Guardian;
import adrianromanski.restschool.domain.base_entity.person.Student;
import adrianromanski.restschool.domain.base_entity.Subject;
import adrianromanski.restschool.domain.base_entity.person.Teacher;
import adrianromanski.restschool.repositories.base_entity.SubjectRepository;
import adrianromanski.restschool.repositories.event.ExamRepository;
import adrianromanski.restschool.repositories.event.ExamResultRepository;
import adrianromanski.restschool.repositories.group.StudentClassRepository;
import adrianromanski.restschool.repositories.person.GuardianRepository;
import adrianromanski.restschool.repositories.person.StudentRepository;
import adrianromanski.restschool.repositories.person.TeacherRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static adrianromanski.restschool.domain.base_entity.enums.FemaleName.CHARLOTTE;
import static adrianromanski.restschool.domain.base_entity.enums.Gender.FEMALE;
import static adrianromanski.restschool.domain.base_entity.enums.Gender.MALE;
import static adrianromanski.restschool.domain.base_entity.enums.LastName.*;
import static adrianromanski.restschool.domain.base_entity.enums.MaleName.*;
import static adrianromanski.restschool.domain.base_entity.enums.Specialization.*;

@Component
@Slf4j
public class SchoolBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;
    private final ExamRepository examRepository;
    private final ExamResultRepository examResultRepository;
    private final TeacherRepository teacherRepository;
    private final StudentClassRepository studentClassRepository;
    private final GuardianRepository guardianRepository;

    public SchoolBootstrap(StudentRepository studentRepository, SubjectRepository subjectRepository, ExamRepository examRepository,
                                                           ExamResultRepository examResultRepository, TeacherRepository teacherRepository,
                                                           StudentClassRepository studentClassRepository, GuardianRepository guardianRepository) {
        this.studentRepository = studentRepository;
        this.subjectRepository = subjectRepository;
        this.examRepository = examRepository;
        this.examResultRepository = examResultRepository;
        this.teacherRepository = teacherRepository;
        this.studentClassRepository = studentClassRepository;
        this.guardianRepository = guardianRepository;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        // Init Class
        StudentClass biologyClass = StudentClass.builder().name("First year Biology")
                                                .president(GABRIEL.get()).specialization(BIOLOGY).build();

        StudentClass mathClass =  StudentClass.builder().name("Second year Mathematics")
                                                .president(OLIVER.get()).specialization(MATHEMATICS).build();

        StudentClass physicsClass = StudentClass.builder().name("Last year Physics")
                                                .president(MASON.get()).specialization(PHYSICS).build();

        // Init Subjects
        Subject math = Subject.builder().name(MATHEMATICS).value(10L).build();
        Subject biology = Subject.builder().name(BIOLOGY).value(8L).build();
        Subject physics = Subject.builder().name(PHYSICS).value(10L).build();


        // Init Students
        Student jacob = Student.builder().firstName(JACOB.get()).lastName(COOPER.get()).
                                    gender(MALE).dateOfBirth(LocalDate.of(1992, 11, 3)).build();

        Student charlotte = Student.builder().firstName(CHARLOTTE.get()).lastName(HENDERSON.get()).
                                    gender(FEMALE).dateOfBirth(LocalDate.of(1994, 10, 4)).build();

        Student ethan = Student.builder().firstName(ETHAN.get()).lastName(PARKER.get()).
                                    gender(MALE).dateOfBirth(LocalDate.of(1991, 12, 5)).build();

        // Init List of Students
        List<Student> students = Arrays.asList(jacob, charlotte, ethan);

        // Init List of Subjects
        List<Subject> subjects = Arrays.asList(math, biology, physics);

        // Assign Subjects to Students
        jacob.getSubjects().addAll(subjects);
        ethan.getSubjects().addAll(subjects);
        charlotte.getSubjects().addAll(subjects);


        // Assign Students to Subjects
        biology.getStudents().addAll(students);
        math.getStudents().addAll(students);


        // Init Guardians
        Guardian william = Guardian.builder().firstName(WILLIAM.get()).lastName(GONZALES.get())
                                    .gender(MALE).dateOfBirth(LocalDate.of(1991, 12, 5)).build();

        Guardian henry = Guardian.builder().firstName(HENRY.get()).lastName(HENDERSON.get())
                                    .gender(MALE).dateOfBirth(LocalDate.of(1991, 12, 5)).build();

        //Add Students to Guardians
        william.getStudents().addAll(Arrays.asList(jacob, ethan));
        henry.getStudents().add(charlotte);

        // Add Guardian To Student
        jacob.setGuardian(william);
        ethan.setGuardian(william);
        charlotte.setGuardian(henry);


        //Init Teacher
        Teacher isaac_biology = Teacher.builder().firstName(ISAAC.get()).lastName(JOHNSON.get())
                                        .gender(MALE).specialization(BIOLOGY)
                                        .dateOfBirth(LocalDate.of(1980, 10, 3)).firstDay(LocalDate.of(2018, 10, 3)).build();

        Teacher benjamin_math = Teacher.builder().firstName(ISAAC.get()).lastName(PEREZ.get())
                                        .gender(MALE).specialization(MATHEMATICS)
                                        .dateOfBirth(LocalDate.of(1970, 10, 3)).firstDay(LocalDate.of(2012, 10, 3)).build();

        Teacher logan_physics = Teacher.builder().firstName(LOGAN.get()).lastName(RODRIGUEZ.get())
                                        .gender(MALE).specialization(PHYSICS)
                                        .dateOfBirth(LocalDate.of(1960, 10, 3)).firstDay(LocalDate.of(2013, 5, 3)).build();

        // Assign Teacher to Classes
        physicsClass.setTeacher(logan_physics);
        mathClass.setTeacher(benjamin_math);
        biologyClass.setTeacher(isaac_biology);

        // Init Exam
        Exam mathExam = Exam.builder().name(MATHEMATICS.toString()).maxPoints(100L).date(LocalDate.now()).build();
        Exam biologyExam = Exam.builder().name(BIOLOGY.toString()).maxPoints(100L).date(LocalDate.now()).build();

        // Assign students to exams
        biologyExam.setStudents(students);
        mathExam.setStudents(students);

        // Assign Exam to Teacher
        isaac_biology.getExams().add(biologyExam);
        benjamin_math.getExams().add(mathExam);

        // Assign teacher to exam
        biologyExam.setTeacher(isaac_biology);

        mathExam.setTeacher(benjamin_math);

        // Init ExamResults
        ExamResult ethanResultMath = ExamResult.builder().name(ethan.getFirstName() + " " + ethan.getLastName())
                                                    .score(60f).exam(mathExam).date(LocalDate.now()).build();

        ExamResult ethanResultBiology = ExamResult.builder().name(ethan.getFirstName() + " " + ethan.getLastName())
                                                    .score(30f).exam(biologyExam).date(LocalDate.now()).build();

        ExamResult jacobResultMath = ExamResult.builder().name(jacob.getFirstName() + " " + jacob.getLastName())
                                                    .score(45f).exam(mathExam).date(LocalDate.now()).build();

        ExamResult jacobResultBiology = ExamResult.builder().name(jacob.getFirstName() + " " + jacob.getLastName())
                                                    .score(90f).exam(biologyExam).date(LocalDate.now()).build();

        ExamResult charlotteResultMath = ExamResult.builder().name(charlotte.getFirstName() + " " + charlotte.getLastName())
                                                    .score(55f).exam(mathExam).date(LocalDate.now()).build();

        ExamResult charlotteResultBiology = ExamResult.builder().name(charlotte.getFirstName() + " " + charlotte.getLastName())
                                                    .score(85f).exam(biologyExam).date(LocalDate.now()).build();

        // Assign Exams to Subjects
        math.addExam(mathExam);
        biology.addExam(biologyExam);

        // Assign subject to Exam
        mathExam.setSubject(math);
        biologyExam.setSubject(biology);

        // Assign exam to Students
        jacob.getExams().addAll(Arrays.asList(mathExam, biologyExam));
        ethan.getExams().addAll(Arrays.asList(mathExam, biologyExam));
        charlotte.getExams().addAll(Arrays.asList(mathExam, biologyExam));


        // Assign result
        jacob.getExams().get(0).addResult(jacobResultMath);
        jacob.getExams().get(1).addResult(jacobResultBiology);

        ethan.getExams().get(0).addResult(ethanResultMath);
        ethan.getExams().get(1).addResult(ethanResultBiology);

        charlotte.getExams().get(0).addResult(charlotteResultMath);
        charlotte.getExams().get(1).addResult(charlotteResultBiology);

        // Assign Student and Teacher to Class
        physicsClass.setTeacher(isaac_biology);
        physicsClass.setStudentList(students);

        // Assign Class to Student
        jacob.setStudentClass(physicsClass);
        ethan.setStudentClass(physicsClass);
        charlotte.setStudentClass(physicsClass);


        // Saving to repositories

        //Students
        studentRepository.save(jacob);
        studentRepository.save(ethan);
        studentRepository.save(charlotte);

        // Guardians
        guardianRepository.save(henry);
        guardianRepository.save(william);

        //Teachers
        teacherRepository.save(isaac_biology);
        teacherRepository.save(benjamin_math);
        teacherRepository.save(logan_physics);

        //Subjects
        subjectRepository.save(biology);
        subjectRepository.save(math);
        subjectRepository.save(physics);

        //Exams
        examRepository.save(mathExam);
        examRepository.save(biologyExam);

        //Exam Results
        examResultRepository.save(jacobResultMath);
        examResultRepository.save(charlotteResultMath);
        examResultRepository.save(ethanResultMath);

        // StudentClasses
        studentClassRepository.save(physicsClass);
        studentClassRepository.save(mathClass);
        studentClassRepository.save(biologyClass);

        // Logging to console
        log.info("Saved: " + studentRepository.count() + " Students");
        log.info("Saved: " + guardianRepository.count() + " Guardians");
        log.info("Saved: " + teacherRepository.count() + " Teachers");
        log.info("Saved: " + subjectRepository.count() + " Subjects");
        log.info("Saved: " + examRepository.count() + " Exams");
        log.info("Saved: " + examResultRepository.count() + " Exam Results");
        log.info("Saved: " + studentClassRepository.count() + " Student Classes");
    }
}
