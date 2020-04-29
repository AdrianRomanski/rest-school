package adrianromanski.restschool.services.group.sport_team;

import adrianromanski.restschool.domain.base_entity.group.SportTeam;
import adrianromanski.restschool.exceptions.ResourceNotFoundException;
import adrianromanski.restschool.mapper.group.SportTeamMapper;
import adrianromanski.restschool.model.base_entity.group.SportTeamDTO;
import adrianromanski.restschool.repositories.group.SportTeamRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SportTeamServiceImpl implements SportTeamService {

    private final SportTeamRepository sportTeamRepository;
    private final SportTeamMapper sportTeamMapper;

    public SportTeamServiceImpl(SportTeamRepository sportTeamRepository, SportTeamMapper sportTeamMapper) {
        this.sportTeamRepository = sportTeamRepository;
        this.sportTeamMapper = sportTeamMapper;
    }

    @Override
    public List<SportTeamDTO> getAllSportTeam() {
        return sportTeamRepository.findAll()
                            .stream()
                            .map(sportTeamMapper::sportTeamToSportTeamDTO)
                            .collect(Collectors.toList());
    }


    @Override
    public SportTeamDTO getSportTeamById(Long id) {
        return sportTeamMapper.sportTeamToSportTeamDTO(sportTeamRepository
                            .findById(id)
                            .orElseThrow(ResourceNotFoundException::new));
    }

    @Override
    public SportTeamDTO createNewSportTeam(SportTeamDTO sportTeamDTO) {
        sportTeamRepository.save(sportTeamMapper.sportTeamDTOToSportTeam(sportTeamDTO));
        log.info("Sport Team with id: " + sportTeamDTO.getId() + " successfully saved");
        return sportTeamDTO;
    }

    @Override
    public SportTeamDTO updateSportTeam(SportTeamDTO sportTeamDTO, Long id) {
        if(sportTeamRepository.findById(id).isPresent()) {
            SportTeam updated = sportTeamMapper.sportTeamDTOToSportTeam(sportTeamDTO);
            updated.setId(id);
            sportTeamRepository.save(updated);
            log.info("Sport Team with id: " + id + " successfully updated");
            return sportTeamMapper.sportTeamToSportTeamDTO(updated);
        } else {
            throw new ResourceNotFoundException("Sport Team with id: " + id + " not found");
        }
    }

    @Override
    public void deleteSportTeamById(Long id) {
        if(sportTeamRepository.findById(id).isPresent()) {
            sportTeamRepository.deleteById(id);
            log.info("Sport Team with id: " + id + " successfully deleted");
        } else {
            log.debug("Sport Team id: " + id + " not found");
            throw new ResourceNotFoundException("Sport Team with id: " + id + " not found");
        }
    }
}
