package adrianromanski.restschool.model.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class PaymentListDTO {
    private final List<PaymentDTO> paymentDTOList;
}
