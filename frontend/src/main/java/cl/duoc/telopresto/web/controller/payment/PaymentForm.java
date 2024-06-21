package cl.duoc.telopresto.web.controller.payment;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentForm {
    @Min(message = "El monto debe ser mayor a 0", value = 1)
    private Double amount;
    @NotBlank(message = "La fecha no puede estar vacía")
    private String date;
    @NotBlank(message = "El método de pago no puede estar vacío")
    private String method;
}
