package adrianromanski.restschool.services.legal_guardian;

import adrianromanski.restschool.domain.base_entity.person.LegalGuardian;
import adrianromanski.restschool.exceptions.ResourceNotFoundException;
import adrianromanski.restschool.mapper.LegalGuardianMapper;
import adrianromanski.restschool.model.base_entity.person.LegalGuardianDTO;
import adrianromanski.restschool.repositories.LegalGuardianRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LegalGuardianServiceImpl implements LegalGuardianService {

    private final LegalGuardianMapper legalGuardianMapper;
    private final LegalGuardianRepository legalGuardianRepository;

    public LegalGuardianServiceImpl(LegalGuardianMapper legalGuardianMapper, LegalGuardianRepository legalGuardianRepository) {
        this.legalGuardianMapper = legalGuardianMapper;
        this.legalGuardianRepository = legalGuardianRepository;
    }

    @Override
    public List<LegalGuardianDTO> getAllLegalGuardians() {
        return legalGuardianRepository.findAll()
                                        .stream()
                                        .map(legalGuardianMapper::legalGuardianToLegalGuardianDTO)
                                        .collect(Collectors.toList());
    }

    @Override
    public LegalGuardianDTO getGuardianByID(Long id) {
        return legalGuardianMapper.legalGuardianToLegalGuardianDTO
                                        (legalGuardianRepository.findById(id)
                                        .orElseThrow(ResourceNotFoundException::new));
    }

    @Override
    public LegalGuardianDTO getLegalGuardianByFirstAndLastName(String firstName, String lastName) {
        return legalGuardianMapper.legalGuardianToLegalGuardianDTO
                                        (legalGuardianRepository.getLegalGuardianByFirstNameAndLastName(firstName, lastName)
                                        .orElseThrow(ResourceNotFoundException::new));
    }

    @Override
    public LegalGuardianDTO createNewLegalGuardian(LegalGuardianDTO legalGuardianDTO) {
        return saveAndReturnDTO(legalGuardianMapper.legalGuardianDTOToLegalGuardian(legalGuardianDTO));
    }

    @Override
    public LegalGuardianDTO updateLegalGuardian(LegalGuardianDTO legalGuardianDTO, Long id) {
        legalGuardianDTO.setId(id);
        return saveAndReturnDTO(legalGuardianMapper.legalGuardianDTOToLegalGuardian(legalGuardianDTO));
    }

    @Override
    public void deleteLegalGuardianByID(Long id) {
        if(legalGuardianRepository.findById(id).isPresent()) {
            legalGuardianRepository.deleteById(id);
        } else {
            throw new RuntimeException("Legal guardian with id: " + id + " not found");
        }
    }


    private LegalGuardianDTO saveAndReturnDTO(LegalGuardian legalGuardian) {
        legalGuardianRepository.save(legalGuardian);
        return legalGuardianMapper.legalGuardianToLegalGuardianDTO(legalGuardian);
    }
}
