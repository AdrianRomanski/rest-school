package adrianromanski.restschool.bootstrap;

import adrianromanski.restschool.domain.base_entity.address.StudentAddress;
import adrianromanski.restschool.domain.base_entity.contact.StudentContact;
import adrianromanski.restschool.domain.event.Exam;
import adrianromanski.restschool.domain.event.ExamResult;
import adrianromanski.restschool.domain.group.SportTeam;
import adrianromanski.restschool.domain.group.StudentClass;
import adrianromanski.restschool.domain.person.Guardian;
import adrianromanski.restschool.domain.person.Student;
import adrianromanski.restschool.domain.base_entity.Subject;
import adrianromanski.restschool.domain.person.Teacher;
import adrianromanski.restschool.repositories.base_entity.AddressRepository;
import adrianromanski.restschool.repositories.base_entity.StudentContactRepository;
import adrianromanski.restschool.repositories.base_entity.SubjectRepository;
import adrianromanski.restschool.repositories.event.ExamRepository;
import adrianromanski.restschool.repositories.event.ExamResultRepository;
import adrianromanski.restschool.repositories.group.SportTeamRepository;
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

import static adrianromanski.restschool.domain.enums.FemaleName.CHARLOTTE;
import static adrianromanski.restschool.domain.enums.Gender.FEMALE;
import static adrianromanski.restschool.domain.enums.Gender.MALE;
import static adrianromanski.restschool.domain.enums.LastName.*;
import static adrianromanski.restschool.domain.enums.MaleName.*;
import static adrianromanski.restschool.domain.enums.Subjects.*;
import static adrianromanski.restschool.domain.enums.Sport.FOOTBALL;

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
    private final SportTeamRepository sportTeamRepository;
    private final StudentContactRepository studentContactRepository;
    private final AddressRepository addressRepository;

    public SchoolBootstrap(StudentRepository studentRepository, SubjectRepository subjectRepository, ExamRepository examRepository, ExamResultRepository examResultRepository,
                           TeacherRepository teacherRepository, StudentClassRepository studentClassRepository, GuardianRepository guardianRepository,
                           SportTeamRepository sportTeamRepository, StudentContactRepository studentContactRepository, AddressRepository addressRepository) {
        this.studentRepository = studentRepository;
        this.subjectRepository = subjectRepository;
        this.examRepository = examRepository;
        this.examResultRepository = examResultRepository;
        this.teacherRepository = teacherRepository;
        this.studentClassRepository = studentClassRepository;
        this.guardianRepository = guardianRepository;
        this.sportTeamRepository = sportTeamRepository;
        this.studentContactRepository = studentContactRepository;
        this.addressRepository = addressRepository;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        // Init Class
        StudentClass biologyClass = StudentClass.builder().name("First year Biology")
                                                .president(GABRIEL.get()).subject(BIOLOGY).build();

        StudentClass mathClass =  StudentClass.builder().name("Second year Mathematics")
                                                .president(OLIVER.get()).subject(MATHEMATICS).build();

        StudentClass physicsClass = StudentClass.builder().name("Last year Physics")
                                                .president(MASON.get()).subject(PHYSICS).build();


        // Init Sport Team
        SportTeam footballTeam = SportTeam.builder().name("Shiny Asteroids").president(LIAM.get()).sport(FOOTBALL).build();
        SportTeam footballTeam2 = SportTeam.builder().name("Dark Asteroids").president(LIAM.get()).sport(FOOTBALL).build();


        // Init Subjects
        Subject math = Subject.builder().name(MATHEMATICS).value(10L).build();
        Subject biology = Subject.builder().name(BIOLOGY).value(8L).build();
        Subject physics = Subject.builder().name(PHYSICS).value(10L).build();


        // Init Students
        Student jacob = Student.builder().firstName(JACOB.get()).lastName(COOPER.get()).
                                    gender(MALE).dateOfBirth(LocalDate.of(1992, 11, 3)).build();

        // Contact Jacob
        StudentAddress address = StudentAddress.builder().country("Poland").city("Warsaw").postalCode("22-421").streetName("District 9").build();
        StudentContact contact = StudentContact.builder().email("JacobStar@gmail.com").telephoneNumber("22-4421-22").emergencyNumber("22-4112-22").build();
        contact.setStudent(jacob);
        address.setStudent(jacob);
        jacob.setContact(contact);
        jacob.setAddress(address);

        Student charlotte = Student.builder().firstName(CHARLOTTE.get()).lastName(HENDERSON.get()).
                                    gender(FEMALE).dateOfBirth(LocalDate.of(1994, 10, 4)).build();



        Student ethan = Student.builder().firstName(ETHAN.get()).lastName(PARKER.get()).
                                    gender(MALE).dateOfBirth(LocalDate.of(1991, 12, 5)).build();


        // Init List of Students
        List<Student> students = Arrays.asList(jacob, charlotte, ethan);

        // Init List of Subjects
        List<Subject> subjects = Arrays.asList(math, biology, physics);

        // Assign Students to Sport Teams
        footballTeam.getStudents().addAll(students);
//        footballTeam2.getStudents().add(jacob);

        // Assign Sport Team to Students
        students.forEach(student -> student.setSportTeam(footballTeam));

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
                                        .gender(MALE).subject(BIOLOGY)
                                        .dateOfBirth(LocalDate.of(1980, 10, 3)).firstDay(LocalDate.of(2018, 10, 3)).build();

        Teacher benjamin_math = Teacher.builder().firstName(ISAAC.get()).lastName(PEREZ.get())
                                        .gender(MALE).subject(MATHEMATICS)
                                        .dateOfBirth(LocalDate.of(1970, 10, 3)).firstDay(LocalDate.of(2012, 10, 3)).build();

        Teacher logan_physics = Teacher.builder().firstName(LOGAN.get()).lastName(RODRIGUEZ.get())
                                        .gender(MALE).subject(PHYSICS)
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

        // Contact
        studentContactRepository.save(contact);

        // Address
        addressRepository.save(address);

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

        // SportTeams
        sportTeamRepository.save(footballTeam);
        sportTeamRepository.save(footballTeam2);



        // Logging to console

//        log.info("Saved: " + guardianRepository.count() + " Guardians");
//        log.info("Saved: " + teacherRepository.count() + " Teachers");
//        log.info("Saved: " + subjectRepository.count() + " Subjects");
//        log.info("Saved: " + examRepository.count() + " Exams");
//        log.info("Saved: " + examResultRepository.count() + " Exam Results");
//        log.info("Saved: " + studentClassRepository.count() + " Student Classes");
//        log.info("Saved: " + sportTeamRepository.count() + " Sport Teams");
//        log.info("Saved: " + contactRepository.count() + " Contacts");
//        log.info("Saved: " + addressRepository.count() + " Addresses");
//        log.info("Saved: " + studentRepository.count() + " Students");
    }
}
