package adrianromanski.restschool.controllers.event;

import adrianromanski.restschool.model.base_entity.event.PaymentDTO;
import adrianromanski.restschool.model.base_entity.event.PaymentListDTO;
import adrianromanski.restschool.services.event.payment.PaymentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Api("Controller for Payments")
@RestController
@RequestMapping("/payments/")
public class PaymentController {

    private PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @ApiOperation("Returns a PaymentListDTO Object that contains all Payments")
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public PaymentListDTO getAllPayments() {
        return new PaymentListDTO(paymentService.getAllPayments());
    }

    @ApiOperation("Returns an Payment Object based on ID or else throw ResourceNotFoundException")
    @GetMapping("id-{ID}")
    @ResponseStatus(HttpStatus.OK)
    public PaymentDTO getPaymentById(@PathVariable String ID) {
        return paymentService.getPaymentById(Long.valueOf(ID));
    }

    @ApiOperation("Returns an Payment Object based on name or else throw ResourceNotFoundException")
    @GetMapping("name-{name}")
    @ResponseStatus(HttpStatus.OK)
    public PaymentDTO getPaymentByName(@PathVariable String name) {
        return paymentService.getPaymentByName(name);
    }

    @ApiOperation("Create and save new Payment based on PaymentDTO body")
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentDTO createNewPayment(@RequestBody PaymentDTO paymentDTO) {
        return paymentService.createNewPayment(paymentDTO);
    }

    @ApiOperation("Update an existing Payment with matching ID or create a new one")
    @PutMapping("{ID}")
    @ResponseStatus(HttpStatus.OK)
    public PaymentDTO updatePayment(@PathVariable String ID, @RequestBody PaymentDTO paymentDTO) {
        return paymentService.updatePayment(Long.valueOf(ID), paymentDTO);
    }


    @ApiOperation("Delete an Payment based on ID")
    @DeleteMapping("{ID}")
    @ResponseStatus(HttpStatus.OK)
    public void deletePaymentById(@PathVariable String ID) {
        paymentService.deletePaymentById(Long.valueOf(ID));
    }
}
