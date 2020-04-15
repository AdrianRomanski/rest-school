package adrianromanski.restschool.model.base_entity.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class PaymentListDTO {

    private List<PaymentDTO> paymentDTOList;
}
