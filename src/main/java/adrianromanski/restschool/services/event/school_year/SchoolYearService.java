package adrianromanski.restschool.services.event.school_year;

import adrianromanski.restschool.domain.event.SchoolYear;
import adrianromanski.restschool.model.event.SchoolYearDTO;

public interface SchoolYearService {

    //GET
    SchoolYearDTO getSchoolYearByID(Long id);

    //POST
    SchoolYearDTO createSchoolYear(SchoolYearDTO schoolYearDTO);

    //PUT
    SchoolYearDTO updateSchoolYear(Long id, SchoolYearDTO schoolYearDTO);

    //DELETE
    void deleteSchoolYear(Long id);
}
