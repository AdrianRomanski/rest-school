package adrianromanski.restschool.services.person.guardian;

import adrianromanski.restschool.domain.base_entity.person.Guardian;
import adrianromanski.restschool.domain.base_entity.person.Person;
import adrianromanski.restschool.domain.base_entity.person.Student;
import adrianromanski.restschool.domain.base_entity.person.Teacher;
import adrianromanski.restschool.model.base_entity.person.GuardianDTO;
import adrianromanski.restschool.model.base_entity.person.StudentDTO;

import java.util.List;
import java.util.Map;
import java.util.TreeSet;

public interface GuardianService {

    List<GuardianDTO> getAllGuardians();

    GuardianDTO getGuardianByID(Long id);

    GuardianDTO getGuardianByFirstAndLastName(String firstName, String lastName);

    Map<Long, List<GuardianDTO>> getGuardiansByAge();

    List<StudentDTO> getAllStudentsForGuardian(Long id);

    GuardianDTO createNewGuardian(GuardianDTO legalGuardianDTO);

    GuardianDTO updateGuardian(GuardianDTO legalGuardianDTO, Long id);

    void deleteGuardianByID(Long id);

    // Ideas
    // getGuardians sorted by Students Map<String, List<Students>>
    // get youngest guardian Guardian
    // get oldest guardian Guardian
    // get guardian with highest number of students Map<Guardian, List<Students>>
}
