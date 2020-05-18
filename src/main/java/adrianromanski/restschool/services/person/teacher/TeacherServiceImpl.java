package adrianromanski.restschool.services.person.teacher;

import adrianromanski.restschool.domain.base_entity.address.Address;
import adrianromanski.restschool.domain.base_entity.address.TeacherAddress;
import adrianromanski.restschool.domain.enums.Subjects;
import adrianromanski.restschool.domain.event.Exam;
import adrianromanski.restschool.domain.person.Student;
import adrianromanski.restschool.domain.person.Teacher;
import adrianromanski.restschool.exceptions.ResourceNotFoundException;
import adrianromanski.restschool.mapper.base_entity.TeacherAddressMapper;
import adrianromanski.restschool.mapper.event.ExamMapper;
import adrianromanski.restschool.mapper.person.StudentMapper;
import adrianromanski.restschool.mapper.person.TeacherMapper;
import adrianromanski.restschool.model.base_entity.address.TeacherAddressDTO;
import adrianromanski.restschool.model.event.ExamDTO;
import adrianromanski.restschool.model.person.StudentDTO;
import adrianromanski.restschool.model.person.TeacherDTO;
import adrianromanski.restschool.repositories.base_entity.AddressRepository;
import adrianromanski.restschool.repositories.event.ExamRepository;
import adrianromanski.restschool.repositories.person.StudentRepository;
import adrianromanski.restschool.repositories.person.TeacherRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Slf4j
@Service
public class TeacherServiceImpl implements TeacherService {


    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final ExamRepository examRepository;
    private final AddressRepository addressRepository;
    private final TeacherMapper teacherMapper;
    private final TeacherAddressMapper addressMapper;
    private final ExamMapper examMapper;
    private final StudentMapper studentMapper;

    public static final Comparator<TeacherDTO> COMPARING_SPEC_EXPERIENCE = Comparator
            .comparing(TeacherDTO::getSubject)
            .thenComparing(TeacherDTO::getYearsOfExperience);

    public TeacherServiceImpl(TeacherRepository teacherRepository, StudentRepository studentRepository, ExamRepository examRepository,
                              AddressRepository addressRepository, TeacherMapper teacherMapper, TeacherAddressMapper addressMapper, ExamMapper examMapper, StudentMapper studentMapper) {
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
        this.examRepository = examRepository;
        this.addressRepository = addressRepository;
        this.teacherMapper = teacherMapper;
        this.addressMapper = addressMapper;
        this.examMapper = examMapper;
        this.studentMapper = studentMapper;
    }

    /**
     * @return Teachers sorted by Specialization -> yearsOfExperience
     */
    @Override
    public List<TeacherDTO> getAllTeachers() {
        return teacherRepository.findAll()
                .stream()
                .map(teacherMapper::teacherToTeacherDTO)
                .sorted(COMPARING_SPEC_EXPERIENCE)
                .collect(toList());
    }

    /**
     * @return Teacher with matching firstName and lastName
     * @throws ResourceNotFoundException if not found
     */
    @Override
    public TeacherDTO getTeacherByFirstNameAndLastName(String firstName, String lastName) {
        return teacherMapper.teacherToTeacherDTO(teacherRepository
                .getTeacherByFirstNameAndLastName(firstName, lastName)
                .orElseThrow(() -> new ResourceNotFoundException(firstName, lastName, Teacher.class)));
    }

    /**
     * @return Teacher with matching id
     * @throws ResourceNotFoundException if not found
     */
    @Override
    public TeacherDTO getTeacherByID(Long id) {
        return teacherMapper.teacherToTeacherDTO(teacherRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id, Teacher.class)));
    }

    /**
     * @return Teachers grouped by Specialization
     */
    @Override
    public Map<Subjects, List<TeacherDTO>> getTeachersBySpecialization() {
        return teacherRepository.findAll()
                .stream()
                .map(teacherMapper::teacherToTeacherDTO)
                .collect(
                        Collectors.groupingBy(
                                TeacherDTO::getSubject
                        )
                );
    }

    /**
     * @return Teachers grouped by yearsOfExperience
     */
    @Override
    public Map<Long, List<TeacherDTO>> getTeachersByYearsOfExperience() {
        return teacherRepository.findAll()
                .stream()
                .map(teacherMapper::teacherToTeacherDTO)
                .collect(
                        Collectors.groupingBy(
                                TeacherDTO::getYearsOfExperience
                        )
                );
    }

    /**
     * Adding Exam to every Student in the Class
     * @throws ResourceNotFoundException if not found
     */
    @Override
    public ExamDTO addExamForClass(Long teacherID, ExamDTO examDTO) {
        Teacher teacher = teacherRepository
                .findById(teacherID)
                .orElseThrow(() -> new ResourceNotFoundException(teacherID, Teacher.class));
        Exam exam = examMapper.examDTOToExam(examDTO);
            teacher.getExams().add(exam);
            teacher.getStudentClass().getStudentList().forEach(s -> s.getExams().add(exam)); // Adding Exams to Students
            exam.setStudents(teacher.getStudentClass().getStudentList()); // Adding Students to Exam
            exam.setTeacher(teacher);
        teacherRepository.save(teacher);
        examRepository.save(exam);
        log.info("Exam successfully added to Class: " + teacher.getStudentClass().getName());
        return examMapper.examToExamDTO(exam);
    }


    /**
     * Adding Correction Exam to Student with matching id
     * @throws ResourceNotFoundException if not found
     */
    @Override
    public ExamDTO addCorrectionExamToStudent(Long teacherID, Long studentID, ExamDTO examDTO) {
            Teacher teacher = teacherRepository
                    .findById(teacherID)
                    .orElseThrow(() -> new ResourceNotFoundException(teacherID, Teacher.class));
            Student student = studentRepository
                    .findById(studentID)
                    .orElseThrow(() -> new ResourceNotFoundException(studentID, Student.class));
            Exam exam = examMapper.examDTOToExam(examDTO);
                teacher.getExams().add(exam);
                student.getExams().add(exam);
                exam.getStudents().add(student);
                exam.setTeacher(teacher);
            studentRepository.save(student);
            teacherRepository.save(teacher);
            examRepository.save(exam);
            log.info("Exam with id: " + exam.getId() + " successfully added to Student with id: " + studentID);
            return examMapper.examToExamDTO(exam);
    }

    /**
     * Adding new Student to the Class
     * @throws ResourceNotFoundException if not found
     */
    @Override
    public StudentDTO addNewStudentToClass(Long teacherID, StudentDTO studentDTO) {
        Teacher teacher = teacherRepository
                .findById(teacherID)
                .orElseThrow(() -> new ResourceNotFoundException(teacherID, Teacher.class));
        Student student = studentMapper.studentDTOToStudent(studentDTO);
            student.setStudentClass(teacher.getStudentClass()); // Adding StudentClass to Student
            teacher.getStudentClass().getStudentList().add(student); // Adding Student to StudentClass
        studentRepository.save(student);
        teacherRepository.save(teacher);
        log.info("Student " + student.getFirstName() + " " + student.getLastName() + " added to Class");
        return studentMapper.studentToStudentDTO(student);
    }


    /**
     *  Save Teacher to Database
     */
    @Override
    public TeacherDTO createNewTeacher(TeacherDTO teacherDTO) {
        teacherRepository.save(teacherMapper.teacherDTOToTeacher(teacherDTO));
        log.info("Teacher with id: " + teacherDTO.getId() + " saved to repository");
        return teacherDTO;
    }


    /**
     * Add Address to Teacher and save to database
     */
    @Override
    public TeacherAddressDTO addAddressToTeacher(Long teacherID, TeacherAddressDTO teacherAddressDTO) {
        Teacher teacher = teacherRepository
                .findById(teacherID)
                .orElseThrow(() -> new ResourceNotFoundException(teacherID, Teacher.class));
        TeacherAddress address = addressMapper.teacherAddressDTOToTeacherAddress(teacherAddressDTO);
            teacher.setAddress(address);
            address.setTeacher(teacher);
        teacherRepository.save(teacher);
        addressRepository.save(address);
        log.info("Address successfully added to Teacher with id: " + teacherID);
        return addressMapper.teacherAddressToTeacherAddressDTO(address);
    }


    /**
     * Moving exam from one day to another
     * @throws ResourceNotFoundException if not found
     */
    @Override
    public ExamDTO moveExamToAnotherDay(Long teacherID, Integer examID, LocalDate localDate) {
        Teacher teacher = teacherRepository
                .findById(teacherID)
                .orElseThrow(() -> new ResourceNotFoundException(teacherID, Teacher.class));
        teacher.getExams().get(examID).setDate(localDate); //  Changing date
        teacherRepository.save(teacher);
        log.info("Exam with id: " + examID + " moved to " + localDate.toString());
        return examMapper.examToExamDTO(teacher.getExams().get(examID));
    }

    /**
     * Changing class president
     * @throws ResourceNotFoundException if teacher or student not found
     *
     */
    @Override
    public TeacherDTO changeClassPresident(Long teacherID, Long studentID) {
            Teacher teacher = teacherRepository
                    .findById(teacherID)
                    .orElseThrow(() -> new ResourceNotFoundException(teacherID, Teacher.class));
            Student student = studentRepository
                    .findById(studentID)
                    .orElseThrow(() -> new ResourceNotFoundException(studentID, Student.class));
            teacher.getStudentClass().setPresident(student.getFirstName() + " " + student.getLastName());
            log.info("New president is Student with id: " + studentID);
            teacherRepository.save(teacher);
            return teacherMapper.teacherToTeacherDTO(teacher);
    }

    /**
     * Update Teacher with Matching ID, and save it to Database
     * @throws ResourceNotFoundException if not found
     */
    @Override
    public TeacherDTO updateTeacher(Long id, TeacherDTO teacherDTO) {
            teacherRepository
                    .findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException(id, Teacher.class));
            Teacher updatedTeacher = teacherMapper.teacherDTOToTeacher(teacherDTO);
            updatedTeacher.setId(id);
            teacherRepository.save(updatedTeacher);
            log.info("Teacher with id:" + id + " successfully updated");
            return teacherMapper.teacherToTeacherDTO(updatedTeacher);
    }


    /**
     * Update Teacher Address with Matching ID, and save it to Database
     *  @throws ResourceNotFoundException if not found
     */
    @Override
    public TeacherAddressDTO updateAddress(Long teacherID, TeacherAddressDTO addressDTO) {
        Teacher teacher = teacherRepository
                .findById(teacherID)
                .orElseThrow(() -> new ResourceNotFoundException(teacherID, Teacher.class));
        Address address = teacher.getAddressOptional()
                .orElseThrow(() -> new ResourceNotFoundException("Address can't be updated"));
        TeacherAddress updatedAddress = addressMapper.teacherAddressDTOToTeacherAddress(addressDTO);
            updatedAddress.setId(address.getId());
            teacher.setAddress(updatedAddress);
        teacherRepository.save(teacher);
        addressRepository.save(updatedAddress);
        log.info("Address of Teacher with id: " + teacherID + " successfully updated");
        return addressMapper.teacherAddressToTeacherAddressDTO(updatedAddress);
    }

    /**
     * Delete Teacher with matching id
     * @throws ResourceNotFoundException if not found
     */
    @Override
    public void deleteTeacherById(Long id) {
        Teacher teacher = teacherRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id, Teacher.class));
        teacherRepository.delete(teacher);
    }


    /**
     * Removes a Student from the Class - it does not delete the Student from database
     * I will transform it later to some kind of punishment for bad behaviour
     */
    @Override
    public void removeStudentFromClass(Long teacherID, Long studentID) {
            Teacher teacher = teacherRepository
                    .findById(teacherID)
                    .orElseThrow(() -> new ResourceNotFoundException(teacherID, Teacher.class));
            Student student = studentRepository
                    .findById(studentID)
                    .orElseThrow(() -> new ResourceNotFoundException(studentID, Student.class));
            teacher.getStudentClass().getStudentList().remove(student);
            log.info("Student with id: " + student + " successfully removed");
    }
}

