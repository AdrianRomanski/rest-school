package adrianromanski.restschool.model.base_entity;


import adrianromanski.restschool.model.base_entity.person.StudentDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class ContactDTO extends BaseEntityDTO {

    private String telephoneNumber;
    private String email;
    private AddressDTO addressDTO;
    private StudentDTO studentDTO;

    @Builder
    public ContactDTO(String telephoneNumber, String email, AddressDTO addressDTO, StudentDTO studentDTO) {
        this.telephoneNumber = telephoneNumber;
        this.email = email;
        this.addressDTO = addressDTO;
        this.studentDTO = studentDTO;
    }
}
