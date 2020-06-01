package adrianromanski.restschool.services.group.sport_team;

import adrianromanski.restschool.domain.enums.Sport;
import adrianromanski.restschool.domain.group.SportTeam;
import adrianromanski.restschool.exceptions.ResourceNotFoundException;
import adrianromanski.restschool.mapper.group.SportTeamMapper;
import adrianromanski.restschool.model.group.SportTeamDTO;
import adrianromanski.restschool.repositories.group.SportTeamRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

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
     * @param id of the SportTeam to be found
     * @return SportTeam with matching id
     * @throws ResourceNotFoundException if not found
     */
    @Override
    public SportTeamDTO getSportTeamById(Long id) {
        return sportTeamRepository
                .findById(id)
                .map(sportTeamMapper::sportTeamToSportTeamDTO)
                .orElseThrow(() -> new ResourceNotFoundException(id, SportTeam.class));
    }


    /**
     * @param name of the SportTeam to be found
     * @return Sport Team with matching name
     * @throws ResourceNotFoundException if not found
     */
    @Override
    public SportTeamDTO getSportTeamByName(String name) {
        return sportTeamRepository
                .getSportTeamByName(name)
                .map(sportTeamMapper::sportTeamToSportTeamDTO)
                .orElseThrow(() -> new ResourceNotFoundException(name, SportTeam.class));
    }


    /**
     * @param president name
     * @return List of Sport Teams with matching president
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
     * @return Sport teams grouped by total of Students
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
     * @param sport name
     * @return Sport Teams with matching sport grouped president
     */
    @Override
    public Map<String, List<SportTeamDTO>> getTeamsForSport(Sport sport) {
        return  sportTeamRepository.findAll()
                .stream()
                .filter(s -> s.getSport().equals(sport))
                .map(sportTeamMapper::sportTeamToSportTeamDTO)
                .collect(groupingBy(
                                SportTeamDTO::getPresident
                        )
                );
    }


    /**
     * @return Sport Teams grouped by Sport
     * @see Sport
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
     * @param sportTeamDTO
     * Save Sport Team to Database
     * @return Sport Team after saving it to database
     */
    @Override
    public SportTeamDTO createNewSportTeam(SportTeamDTO sportTeamDTO) {
        sportTeamRepository.save(sportTeamMapper.sportTeamDTOToSportTeam(sportTeamDTO));
        log.info("Sport Team with id: " + sportTeamDTO.getId() + " successfully saved");
        return sportTeamDTO;
    }


    /**
     * @param id of Sport Team  to be updated
     * @param sportTeamDTO Object to save
     * @return Updated Sport Team if successfully saved
     * @throws ResourceNotFoundException if not found
     */
    @Override
    public SportTeamDTO updateSportTeam(SportTeamDTO sportTeamDTO, Long id) {
            sportTeamRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException(id, SportTeam.class));
            SportTeam updated = sportTeamMapper.sportTeamDTOToSportTeam(sportTeamDTO);
            updated.setId(id);
            sportTeamRepository.save(updated);
            log.info("Sport Team with id: " + id + " successfully updated");
            return sportTeamMapper.sportTeamToSportTeamDTO(updated);
    }


    /**
     * @param id of Sport Team to be deleted
     * @throws ResourceNotFoundException if not found
     */
    @Override
    public void deleteSportTeamById(Long id) {
        sportTeamRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id, SportTeam.class));
        sportTeamRepository.deleteById(id);
        log.info("Sport Team with id: " + id + " successfully deleted");
    }
}