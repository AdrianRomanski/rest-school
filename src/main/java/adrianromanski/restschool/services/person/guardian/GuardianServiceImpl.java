package adrianromanski.restschool.services.person.guardian;

import adrianromanski.restschool.domain.base_entity.person.Guardian;
import adrianromanski.restschool.exceptions.ResourceNotFoundException;
import adrianromanski.restschool.mapper.person.GuardianMapper;
import adrianromanski.restschool.model.base_entity.person.GuardianDTO;
import adrianromanski.restschool.repositories.person.GuardianRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class GuardianServiceImpl implements GuardianService {

    private final GuardianMapper guardianMapper;
    private final GuardianRepository guardianRepository;

    public GuardianServiceImpl(GuardianMapper legalGuardianMapper, GuardianRepository legalGuardianRepository) {
        this.guardianMapper = legalGuardianMapper;
        this.guardianRepository = legalGuardianRepository;
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