package adrianromanski.restschool.services.student_class;

import adrianromanski.restschool.domain.base_entity.group.StudentClass;
import adrianromanski.restschool.exceptions.ResourceNotFoundException;
import adrianromanski.restschool.mapper.StudentClassMapper;
import adrianromanski.restschool.mapper.TeacherMapper;
import adrianromanski.restschool.model.base_entity.group.StudentClassDTO;
import adrianromanski.restschool.model.base_entity.person.TeacherDTO;
import adrianromanski.restschool.repositories.StudentClassRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentClassServiceImpl implements StudentClassService {

    private final StudentClassRepository studentClassRepository;
    private final StudentClassMapper studentClassMapper;
    private final TeacherMapper teacherMapper;

    public StudentClassServiceImpl(StudentClassRepository studentClassRepository, StudentClassMapper studentClassMapper, TeacherMapper teacherMapper) {
        this.studentClassRepository = studentClassRepository;
        this.studentClassMapper = studentClassMapper;
        this.teacherMapper = teacherMapper;
    }

    @Override
    public List<StudentClassDTO> getAllStudentClasses() {
        return studentClassRepository.findAll()
                        .stream()
                        .map(studentClassMapper::StudentClassToStudentClassDTO)
                        .collect(Collectors.toList());
    }

    @Override
    public StudentClassDTO getStudentClassByID(Long id) {
        return studentClassMapper.StudentClassToStudentClassDTO(studentClassRepository.findById(id)
                                                    .orElseThrow(ResourceNotFoundException::new));
    }

    @Override
    public StudentClassDTO createNewStudentClass(StudentClassDTO studentClassDTO) {
        return saveAndReturnDTO(studentClassMapper.StudentClassDTOToStudentClass(studentClassDTO));
    }

    @Override
    public StudentClassDTO updateStudentClass(Long id, StudentClassDTO studentClassDTO) {
        studentClassDTO.setId(id);
        return saveAndReturnDTO(studentClassMapper.StudentClassDTOToStudentClass(studentClassDTO));
    }

    @Override
    public TeacherDTO getStudentClassTeacher(StudentClassDTO studentClassDTO) {
        return teacherMapper.teacherToTeacherDTO(studentClassRepository.getTeacher()
                                            .orElseThrow(ResourceNotFoundException::new));
    }

    @Override
    public void deleteStudentClassById(Long id) {
        studentClassRepository.deleteById(id);
    }

    StudentClassDTO saveAndReturnDTO(StudentClass studentClass) {
        studentClassRepository.save(studentClass);
        return studentClassMapper.StudentClassToStudentClassDTO(studentClass);
    }
}
