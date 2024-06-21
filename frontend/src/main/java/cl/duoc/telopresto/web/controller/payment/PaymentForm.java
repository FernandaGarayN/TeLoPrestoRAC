package cl.duoc.telopresto.web.controller.payment;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class PaymentForm {
    private Double amount;
    private String date;
    private String method;
}
