package adrianromanski.restschool.services.event.school_year;

import adrianromanski.restschool.domain.event.SchoolYear;
import adrianromanski.restschool.exceptions.ResourceNotFoundException;
import adrianromanski.restschool.mapper.event.SchoolYearMapper;
import adrianromanski.restschool.model.event.SchoolYearDTO;
import adrianromanski.restschool.repositories.event.SchoolYearRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SchoolYearServiceImpl implements SchoolYearService {

    private final SchoolYearRepository schoolYearRepository;
    private final SchoolYearMapper schoolYearMapper;


    public SchoolYearServiceImpl(SchoolYearRepository schoolYearRepository, SchoolYearMapper schoolYearMapper) {
        this.schoolYearRepository = schoolYearRepository;
        this.schoolYearMapper = schoolYearMapper;
    }


    /**
     * @param id of the SchoolYear we are looking for
     * @return SchoolYearDTO
     * @throws ResourceNotFoundException if not found
     */
    @Override
    public SchoolYearDTO getSchoolYearByID(Long id) {
        return schoolYearRepository.findById(id)
                .map(schoolYearMapper::schoolYearToSchoolYearDTO)
                .orElseThrow(() -> new ResourceNotFoundException(id, SchoolYear.class));
    }


    /**
     * @param schoolYearDTO body
     * @return SchoolYearDTO if successfully saved to database
     */
    @Override
    public SchoolYearDTO createSchoolYear(SchoolYearDTO schoolYearDTO) {
        SchoolYear schoolYear = schoolYearMapper.schoolYearDTOToSchoolYear(schoolYearDTO);
        schoolYearRepository.save(schoolYear);
        log.info("School Year : " + schoolYearDTO.getDate() + " successfully saved to database");
        return schoolYearMapper.schoolYearToSchoolYearDTO(schoolYear);
    }


    /**
     * @param id of the SchoolYear we want update
     * @param schoolYearDTO body
     * @return SchoolYearDTO if successfully updated
     * @throws ResourceNotFoundException if not found
     */
    @Override
    public SchoolYearDTO updateSchoolYear(Long id, SchoolYearDTO schoolYearDTO) {
        schoolYearRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id, SchoolYear.class));
        SchoolYear updated = schoolYearMapper.schoolYearDTOToSchoolYear(schoolYearDTO);
        updated.setId(id);
        schoolYearRepository.save(updated);
        log.info("School Year : " + schoolYearDTO.getDate() + " successfully updated and saved to database");
        return schoolYearMapper.schoolYearToSchoolYearDTO(updated);
    }


    /**
     * @param id of the SchoolYear we want to delete
     * @throws ResourceNotFoundException if not found
     */
    @Override
    public void deleteSchoolYear(Long id) {
        SchoolYear schoolYear = schoolYearRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id, SchoolYear.class));
        schoolYearRepository.deleteById(id);
        log.info("School Year : " + schoolYear.getDate() + " successfully deleted");
    }
}
