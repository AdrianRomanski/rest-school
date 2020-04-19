package adrianromanski.restschool.services.guardian;

import adrianromanski.restschool.domain.base_entity.person.Guardian;
import adrianromanski.restschool.exceptions.ResourceNotFoundException;
import adrianromanski.restschool.mapper.GuardianMapper;
import adrianromanski.restschool.model.base_entity.person.GuardianDTO;
import adrianromanski.restschool.repositories.GuardianRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GuardianServiceImpl implements GuardianService {

    private final GuardianMapper guardianMapper;
    private final GuardianRepository guardianRepository;

    public GuardianServiceImpl(GuardianMapper legalGuardianMapper, GuardianRepository legalGuardianRepository) {
        this.guardianMapper = legalGuardianMapper;
        this.guardianRepository = legalGuardianRepository;
    }

    @Override
    public List<GuardianDTO> getAllGuardians() {
        return guardianRepository.findAll()
                                        .stream()
                                        .map(guardianMapper::guardianToGuardianDTO)
                                        .collect(Collectors.toList());
    }

    @Override
    public GuardianDTO getGuardianByID(Long id) {
        return guardianMapper.guardianToGuardianDTO
                                        (guardianRepository.findById(id)
                                        .orElseThrow(ResourceNotFoundException::new));
    }

    @Override
    public GuardianDTO getGuardianByFirstAndLastName(String firstName, String lastName) {
        return guardianMapper.guardianToGuardianDTO
                                        (guardianRepository.getLegalGuardianByFirstNameAndLastName(firstName, lastName)
                                        .orElseThrow(ResourceNotFoundException::new));
    }

    @Override
    public GuardianDTO createNewGuardian(GuardianDTO legalGuardianDTO) {
        return saveAndReturnDTO(guardianMapper.guardianDTOToGuardian(legalGuardianDTO));
    }

    @Override
    public GuardianDTO updateGuardian(GuardianDTO legalGuardianDTO, Long id) {
        legalGuardianDTO.setId(id);
        return saveAndReturnDTO(guardianMapper.guardianDTOToGuardian(legalGuardianDTO));
    }

    @Override
    public void deleteGuardianByID(Long id) {
            guardianRepository.deleteById(id);
    }


    private GuardianDTO saveAndReturnDTO(Guardian legalGuardian) {
        guardianRepository.save(legalGuardian);
        return guardianMapper.guardianToGuardianDTO(legalGuardian);
    }
}
