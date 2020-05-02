package adrianromanski.restschool.services.group.sport_team;

import adrianromanski.restschool.domain.base_entity.enums.Sport;
import adrianromanski.restschool.domain.base_entity.group.SportTeam;
import adrianromanski.restschool.exceptions.ResourceNotFoundException;
import adrianromanski.restschool.mapper.group.SportTeamMapper;
import adrianromanski.restschool.model.base_entity.group.SportTeamDTO;
import adrianromanski.restschool.repositories.group.SportTeamRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@Slf4j
@Service
public class SportTeamServiceImpl implements SportTeamService {

    private final SportTeamRepository sportTeamRepository;
    private final SportTeamMapper sportTeamMapper;

    public SportTeamServiceImpl(SportTeamRepository sportTeamRepository, SportTeamMapper sportTeamMapper) {
        this.sportTeamRepository = sportTeamRepository;
        this.sportTeamMapper = sportTeamMapper;
    }


    /**
     * @return all Sport Teams
     */
    @Override
    public List<SportTeamDTO> getAllSportTeam() {
        return sportTeamRepository.findAll()
                            .stream()
                            .map(sportTeamMapper::sportTeamToSportTeamDTO)
                            .collect(toList());
    }


    /**
     * @return Sport Team with matching id
     * @throws ResourceNotFoundException if not found
     */
    @Override
    public SportTeamDTO getSportTeamById(Long id) {
        return sportTeamMapper.sportTeamToSportTeamDTO(sportTeamRepository
                            .findById(id)
                            .orElseThrow(ResourceNotFoundException::new));
    }


    /**
     * @return Sport Team with matching name
     * @throws ResourceNotFoundException if not found
     */
    @Override
    public SportTeamDTO getSportTeamByName(String name) {
        return sportTeamRepository.getSportTeamByName(name)
                            .map(sportTeamMapper::sportTeamToSportTeamDTO)
                            .orElseThrow(ResourceNotFoundException::new);
    }


    /**
     * @return  List of Sport Teams with matching president
     */
    @Override
    public List<SportTeamDTO> getSportTeamByPresident(String president) {
        return sportTeamRepository.findAll()
                .stream()
                .map(sportTeamMapper::sportTeamToSportTeamDTO)
                .filter(s -> s.getPresident().equals(president))
                .collect(Collectors.toList());
    }


    /**
     * @return Map where they key is Matching Sport and values Lists of Sport Teams grouped by President
     */
    @Override
    public Map<Sport, Map<String, List<SportTeamDTO>>> getTeamsForSport(Sport sport) {
        return  sportTeamRepository.findAll()
                .stream()
                .filter(s -> s.getSport().equals(sport))
                .map(sportTeamMapper::sportTeamToSportTeamDTO)
                .collect(groupingBy(
                        SportTeamDTO::getSport,
                        groupingBy(
                                SportTeamDTO::getPresident
                        )
                ));
    }


    /**
     * @return Map where the keys are Sports and values Lists of Sport Teams grouped by President
     */
    @Override
    public Map<Sport, Map<String, List<SportTeamDTO>>> getTeamsGroupedBySport() {
        return sportTeamRepository.findAll()
                .stream()
                .map(sportTeamMapper::sportTeamToSportTeamDTO)
                .collect(groupingBy(
                        SportTeamDTO::getSport,
                        groupingBy(
                                SportTeamDTO::getPresident
                        )
                    )
                );
    }


    /**
     * @return Map where the keys are numbers of Students and values Lists of Sport Teams
     */
    @Override
    public Map<Integer, List<SportTeamDTO>> getSportTeamsByStudentsSize() {
        return sportTeamRepository.findAll()
                .stream()
                .map(sportTeamMapper::sportTeamToSportTeamDTO)
                .collect(groupingBy(
                        SportTeamDTO::getStudentsSize
                ));
    }


    /**
     * Converts DTO Object and Save it to Database
     * @return SportTeamDTO object
     */
    @Override
    public SportTeamDTO createNewSportTeam(SportTeamDTO sportTeamDTO) {
        sportTeamRepository.save(sportTeamMapper.sportTeamDTOToSportTeam(sportTeamDTO));
        log.info("Sport Team with id: " + sportTeamDTO.getId() + " successfully saved");
        return sportTeamDTO;
    }


    /**
     * Converts DTO Object, Update Sport Team with Matching ID and save it to Database
     * @return SportTeamDTO object if the Sport Team  was successfully saved
     * @throws ResourceNotFoundException if not found
     */
    @Override
    public SportTeamDTO updateSportTeam(SportTeamDTO sportTeamDTO, Long id) {
        if(sportTeamRepository.findById(id).isPresent()) {
            SportTeam updated = sportTeamMapper.sportTeamDTOToSportTeam(sportTeamDTO);
            updated.setId(id);
            sportTeamRepository.save(updated);
            log.info("Sport Team with id: " + id + " successfully updated");
            return sportTeamMapper.sportTeamToSportTeamDTO(updated);
        } else {
            log.debug("Sport Team id: " + id + " not found");
            throw new ResourceNotFoundException("Sport Team with id: " + id + " not found");
        }
    }


    /**
     * Delete Sport Team with matching id
     * @throws ResourceNotFoundException if not found
     */
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