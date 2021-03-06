package adrianromanski.restschool.mapper.event;

import adrianromanski.restschool.domain.event.Payment;
import adrianromanski.restschool.model.event.PaymentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PaymentMapper {

    PaymentMapper INSTANCE = Mappers.getMapper(PaymentMapper.class);

    @Mapping(target = "teacherDTO", source = "teacher")
    PaymentDTO paymentToPaymentDTO(Payment payment);

    @Mapping(target = "teacher", source = "teacherDTO")
    Payment paymentDTOToPayment(PaymentDTO paymentDTO);
}
