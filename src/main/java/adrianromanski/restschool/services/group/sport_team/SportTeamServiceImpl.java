package adrianromanski.restschool.services.group.sport_team;

import adrianromanski.restschool.domain.base_entity.group.SportTeam;
import adrianromanski.restschool.exceptions.ResourceNotFoundException;
import adrianromanski.restschool.mapper.group.SportTeamMapper;
import adrianromanski.restschool.model.base_entity.group.SportTeamDTO;
import adrianromanski.restschool.repositories.group.SportTeamRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
        return sportTeamMapper.sportTeamToSportTeamDTO(sportTeamRepository.findById(id)
                                                                    .orElseThrow(ResourceNotFoundException::new));
    }

    @Override
    public SportTeamDTO createNewSportTeam(SportTeamDTO sportTeamDTO) {
        return saveAndReturnDTO(sportTeamMapper.sportTeamDTOToSportTeam(sportTeamDTO));
    }

    @Override
    public SportTeamDTO updateSportTeam(SportTeamDTO sportTeamDTO, Long id) {
        sportTeamDTO.setId(id);
        return saveAndReturnDTO((sportTeamMapper.sportTeamDTOToSportTeam(sportTeamDTO)));
    }

    @Override
    public void deleteSportTeamById(Long id) {
        sportTeamRepository.deleteById(id);
    }

    private SportTeamDTO saveAndReturnDTO(SportTeam team) {
        sportTeamRepository.save(team);
        return sportTeamMapper.sportTeamToSportTeamDTO(team);
    }
}
