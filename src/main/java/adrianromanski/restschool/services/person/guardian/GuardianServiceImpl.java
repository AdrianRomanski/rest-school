package adrianromanski.restschool.services.person.guardian;

import adrianromanski.restschool.domain.base_entity.address.Address;
import adrianromanski.restschool.domain.base_entity.address.GuardianAddress;
import adrianromanski.restschool.domain.person.Guardian;
import adrianromanski.restschool.exceptions.ResourceNotFoundException;
import adrianromanski.restschool.exceptions.UpdateBeforeInitializationException;
import adrianromanski.restschool.mapper.base_entity.GuardianAddressMapper;
import adrianromanski.restschool.mapper.person.GuardianMapper;
import adrianromanski.restschool.mapper.person.StudentMapper;
import adrianromanski.restschool.model.base_entity.address.GuardianAddressDTO;
import adrianromanski.restschool.model.person.GuardianDTO;
import adrianromanski.restschool.model.person.PersonDTO;
import adrianromanski.restschool.model.person.StudentDTO;
import adrianromanski.restschool.repositories.base_entity.AddressRepository;
import adrianromanski.restschool.repositories.person.GuardianRepository;
import adrianromanski.restschool.repositories.person.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class GuardianServiceImpl implements GuardianService {

    private final GuardianMapper guardianMapper;
    private final GuardianAddressMapper addressMapper;
    private final StudentMapper studentMapper;
    private final GuardianRepository guardianRepository;
    private final AddressRepository addressRepository;
    private final StudentRepository studentRepository;

    public GuardianServiceImpl(GuardianMapper guardianMapper, GuardianAddressMapper addressMapper, StudentMapper studentMapper,
                               GuardianRepository guardianRepository, AddressRepository addressRepository, StudentRepository studentRepository) {
        this.guardianMapper = guardianMapper;
        this.addressMapper = addressMapper;
        this.studentMapper = studentMapper;
        this.guardianRepository = guardianRepository;
        this.addressRepository = addressRepository;
        this.studentRepository = studentRepository;
    }

    /**
     * @param id of the Guardian we are looking for
     * @return Guardian with matching id
     * @throws ResourceNotFoundException if not found
     */
    @Override
    public GuardianDTO getGuardianByID(Long id) {
        return guardianMapper.guardianToGuardianDTO(guardianRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id, Guardian.class)));
    }


    /**
     * @param firstName of Guardian we are looking for
     * @param lastName of Guardian we are looking for
     * @return Guardian with matching firstName and lastName
     * @throws ResourceNotFoundException if not found
     */
    @Override
    public GuardianDTO getGuardianByFirstAndLastName(String firstName, String lastName) {
        return guardianMapper.guardianToGuardianDTO(guardianRepository
                .getGuardianByFirstNameAndLastName(firstName, lastName)
                .orElseThrow(() -> new ResourceNotFoundException(firstName, lastName, Guardian.class)));
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
     * @return Guardians grouped by Age
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
     * @param id of the Guardian we are looking for
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
     * @param guardianDTO and save it to Database
     * @return GuardianDTO object
     */
    @Override
    public GuardianDTO createNewGuardian(GuardianDTO guardianDTO) {
        guardianRepository.save(guardianMapper.guardianDTOToGuardian(guardianDTO));
        log.info("Guardian with id: " + guardianDTO.getId() + " successfully saved");
        return guardianDTO;
    }


    /**
     * @param id of the Guardian to add Address
     * @param addressDTO to be added
     * @return Address if successfully added to Guardian
     * @throws ResourceNotFoundException if not found
     */
    @Override
    public GuardianAddressDTO addAddress(Long id, GuardianAddressDTO addressDTO) {
        Guardian guardian = guardianRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id, Address.class));
        GuardianAddress address = addressMapper.addressDTOToAddress(addressDTO);
        guardian.setAddress(address);
        address.setGuardian(guardian);
        guardianRepository.save(guardian);
        addressRepository.save(address);
        log.info("Address successfully added to Guardian with id: " + id);
        return addressMapper.addressToAddressDTO(address);
    }


    /**
     * Update Guardian with Matching ID and save it to Database
     * @param id of the Guardian we are looking for
     * @param guardianDTO for update
     * @throws ResourceNotFoundException if not found
     */
    @Override
    public GuardianDTO updateGuardian(GuardianDTO guardianDTO, Long id) {
            guardianRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException(id, Guardian.class));
            Guardian updatedGuardian = guardianMapper.guardianDTOToGuardian(guardianDTO);
                updatedGuardian.setId(id);
            guardianRepository.save(updatedGuardian);
            log.info("Guardian with id: " + id + " successfully saved");
            return guardianMapper.guardianToGuardianDTO(updatedGuardian);
    }


    /**
     * @param id of the Guardian
     * @param addressDTO for update
     * @return Address if successfully updated
     * @throws UpdateBeforeInitializationException if Address wasn't initialized
     * @throws ResourceNotFoundException if Guardian not found
     */
    @Override
    public GuardianAddressDTO updateAddress(Long id, GuardianAddressDTO addressDTO) {
        Guardian guardian = guardianRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id, Guardian.class));
        GuardianAddress address = guardian.getAddressOptional()
                .orElseThrow(() -> new UpdateBeforeInitializationException(Address.class));
        GuardianAddress updatedAddress = addressMapper.addressDTOToAddress(addressDTO);
            updatedAddress.setId(address.getId());
            guardian.setAddress(updatedAddress);
            updatedAddress.setGuardian(guardian);
        guardianRepository.save(guardian);
        addressRepository.save(updatedAddress);
        log.info("Address with id: " + id + " successfully updated");
        return addressMapper.addressToAddressDTO(updatedAddress);
    }


    /**
     * @param id of the Guardian we want to delete
     * Delete Guardian with matching id
     * @throws ResourceNotFoundException if not found
     */
    @Override
    public void deleteGuardianByID(Long id) {
        Guardian guardian = guardianRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id, Guardian.class));
        guardianRepository.delete(guardian);
        log.info("Guardian with id: " + id + " successfully deleted");
    }
}