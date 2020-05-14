package adrianromanski.restschool.services.person.guardian;

import adrianromanski.restschool.domain.person.Guardian;
import adrianromanski.restschool.exceptions.ResourceNotFoundException;
import adrianromanski.restschool.mapper.person.GuardianMapper;
import adrianromanski.restschool.mapper.person.StudentMapper;
import adrianromanski.restschool.model.person.GuardianDTO;
import adrianromanski.restschool.model.person.PersonDTO;
import adrianromanski.restschool.model.person.StudentDTO;
import adrianromanski.restschool.repositories.person.GuardianRepository;
import adrianromanski.restschool.repositories.person.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Slf4j
@Service
public class GuardianServiceImpl implements GuardianService {

    private final GuardianMapper guardianMapper;
    private final StudentMapper studentMapper;
    private final GuardianRepository guardianRepository;
    private final StudentRepository studentRepository;

    public GuardianServiceImpl(GuardianMapper guardianMapper, StudentMapper studentMapper,
                               GuardianRepository guardianRepository, StudentRepository studentRepository) {
        this.guardianMapper = guardianMapper;
        this.studentMapper = studentMapper;
        this.guardianRepository = guardianRepository;
        this.studentRepository = studentRepository;
    }

    /**
     * @return all Guardians
     */
    @Override
    public List<GuardianDTO> getAllGuardians() {
        return guardianRepository.findAll()
                                        .stream()
                                        .map(guardianMapper::guardianToGuardianDTO)
                                        .collect(Collectors.toList());
    }


    /**
     * @return GuardianDTO with matching id
     * @throws ResourceNotFoundException if not found
     */
    @Override
    public GuardianDTO getGuardianByID(Long id) {
        return guardianMapper.guardianToGuardianDTO(guardianRepository
                                        .findById(id)
                                        .orElseThrow(ResourceNotFoundException::new));
    }


    /**
     * @return GuardianDTO with matching firstName and lastName
     * @throws ResourceNotFoundException if not found
     */
    @Override
    public GuardianDTO getGuardianByFirstAndLastName(String firstName, String lastName) {
        return guardianMapper.guardianToGuardianDTO(guardianRepository
                                        .getGuardianByFirstNameAndLastName(firstName, lastName)
                                        .orElseThrow(ResourceNotFoundException::new));
    }


    /**
     * @return Map where the keys are ages of Guardians and values List of Guardians
     */
    @Override
    public Map<Long, List<GuardianDTO>> getGuardiansByAge() {
        return guardianRepository.findAll()
                .stream()
                .map(guardianMapper::guardianToGuardianDTO)
                .collect(
                        Collectors.groupingBy(
                                PersonDTO::getAge
                        )
                );
    }

    /**
     * @return List of Students for Guardian with matching id
     */
    @Override
    public List<StudentDTO> getAllStudentsForGuardian(Long id) {
        return studentRepository.findAll()
                .stream()
                .filter(student -> student.getGuardian().getId().equals(id))
                .map(studentMapper::studentToStudentDTO)
                .collect(Collectors.toList());
    }


    /**
     * Converts DTO Object and Save it to Database
     * @return GuardianDTO object
     */
    @Override
    public GuardianDTO createNewGuardian(GuardianDTO guardianDTO) {
        guardianRepository.save(guardianMapper.guardianDTOToGuardian(guardianDTO));
        log.info("Guardian with id: " + guardianDTO.getId() + " successfully saved");
        return guardianDTO;
    }


    /**
     * Converts DTO Object, Update Guardian with Matching ID and save it to Database
     * @return GuardianDTO object if the guardian was successfully saved
     * @throws ResourceNotFoundException if not found
     */
    @Override
    public GuardianDTO updateGuardian(GuardianDTO guardianDTO, Long id) {
        if(guardianRepository.findById(id).isPresent()) {
            Guardian updatedGuardian = guardianMapper.guardianDTOToGuardian(guardianDTO);
            updatedGuardian.setId(id);
            guardianRepository.save(updatedGuardian);
            log.info("Guardian with id: " + id + " successfully saved");
            return guardianMapper.guardianToGuardianDTO(updatedGuardian);
        } else {
            throw new ResourceNotFoundException("Guardian with id: " + id + " not found");
        }
    }


    /**
     * Delete Guardian with matching id
     * @throws ResourceNotFoundException if not found
     */
    @Override
    public void deleteGuardianByID(Long id) {
        if(guardianRepository.findById(id).isPresent()) {
            guardianRepository.deleteById(id);
            log.info("Guardian with id: " + id + " successfully deleted");
        } else {
            throw new ResourceNotFoundException("Guardian with id: " + id + " not found");
        }
    }
}