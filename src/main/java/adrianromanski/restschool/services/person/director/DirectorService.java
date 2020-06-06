package adrianromanski.restschool.services.person.director;

import adrianromanski.restschool.model.event.PaymentDTO;
import adrianromanski.restschool.model.person.DirectorDTO;

public interface DirectorService {

    //GET
    DirectorDTO getDirector();


    //POST
    PaymentDTO addPaymentToTeacher(Long teacherID, PaymentDTO paymentDTO);


    //PUT
    DirectorDTO updateDirector(Long directorID, DirectorDTO directorDTO);


    //DELETE
    void deleteDirectorByID(Long directorID);

}
