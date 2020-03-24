package adrianromanski.restschool.services;

import adrianromanski.restschool.domain.Student;
import adrianromanski.restschool.exceptions.ResourceNotFoundException;
import adrianromanski.restschool.mapper.StudentMapper;
import adrianromanski.restschool.model.StudentDTO;
import adrianromanski.restschool.repositories.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    private StudentMapper studentMapper;
    private StudentRepository studentRepository;

    public StudentServiceImpl(StudentMapper studentMapper, StudentRepository studentRepository) {
        this.studentMapper = studentMapper;
        this.studentRepository = studentRepository;
    }

    @Override
    public List<StudentDTO> getAllStudents() {
        return studentRepository.findAll()
                .stream()
                .map(studentMapper::studentToStudentDTO)
                .collect(Collectors.toList());
    }

    @Override
    public StudentDTO getStudentByFirstAndLastName(String firstName, String lastName) {
        return studentMapper.studentToStudentDTO(studentRepository.findByFirstNameAndLastName(firstName, lastName));
    }


    @Override
    public StudentDTO createNewStudent(StudentDTO studentDTO) {
        return saveAndReturnDTO(studentMapper.studentDTOToStudent(studentDTO));
    }

    @Override
    public void deleteStudentByID(Long id) {
         studentRepository.deleteById(id);
    }

    @Override
    public StudentDTO saveStudentByDTO(Long id, StudentDTO studentDTO) {
        Student student = studentMapper.studentDTOToStudent(studentDTO);
        student.setId(id);

        return saveAndReturnDTO(student);
    }

    @Override
    public StudentDTO getStudentByID(Long id) {
        return studentMapper.studentToStudentDTO(studentRepository.findById(id)
                                                    .orElseThrow(ResourceNotFoundException::new));

}

    public StudentDTO saveAndReturnDTO(Student student) {
        Student savedStudent = studentRepository.save(student);
        return studentMapper.studentToStudentDTO(student);
    }
}

