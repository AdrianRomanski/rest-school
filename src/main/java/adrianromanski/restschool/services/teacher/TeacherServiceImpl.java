package adrianromanski.restschool.services.teacher;

import adrianromanski.restschool.domain.base_entity.person.Teacher;
import adrianromanski.restschool.exceptions.ResourceNotFoundException;
import adrianromanski.restschool.mapper.TeacherMapper;
import adrianromanski.restschool.model.base_entity.person.TeacherDTO;
import adrianromanski.restschool.repositories.TeacherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeacherServiceImpl implements TeacherService {

    private  TeacherRepository teacherRepository;
    private  TeacherMapper teacherMapper;

    public TeacherServiceImpl(TeacherRepository teacherRepository, TeacherMapper teacherMapper) {
        this.teacherRepository = teacherRepository;
        this.teacherMapper = teacherMapper;
    }

    @Override
    public List<TeacherDTO> getAllTeachers() {
        return teacherRepository.findAll()
                .stream()
                .map(teacherMapper::teacherToTeacherDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TeacherDTO getTeacherByFirstNameAndLastName(String firstName, String lastName) {
        return teacherMapper.teacherToTeacherDTO(teacherRepository
                                                    .getTeacherByFirstNameAndLastName(firstName,lastName));
    }

    @Override
    public TeacherDTO getTeacherByID(Long id) {
        return teacherMapper.teacherToTeacherDTO(teacherRepository
                                                    .findById(id)
                                                    .orElseThrow(ResourceNotFoundException::new));
    }

    @Override
    public TeacherDTO createNewTeacher(TeacherDTO teacherDTO) {
        return savedAndReturnDTO(teacherMapper.teacherDTOToTeacher(teacherDTO));
    }

    @Override
    public TeacherDTO updateTeacher(Long id, TeacherDTO teacherDTO) {
        teacherDTO.setId(id);
        return savedAndReturnDTO(teacherMapper.teacherDTOToTeacher(teacherDTO));
    }

    @Override
    public void deleteTeacherById(Long id) {
        teacherRepository.deleteById(id);
    }

    TeacherDTO savedAndReturnDTO(Teacher teacher) {
        teacherRepository.save(teacher);
        return teacherMapper.teacherToTeacherDTO(teacher);
    }
}
