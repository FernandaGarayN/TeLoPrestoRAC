package  cl.duoc.telopresto.web.controller.reservation;

import cl.duoc.telopresto.web.controller.validations.ValidDateOrder;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@ValidDateOrder
public class ReservationForm {
    private String id;
    private String carId;
    private String car;
    @NotNull(message = "El campo 'inicio' es requerido")
    private String startAt;
    @NotNull(message = "El campo 'fin' es requerido")
    private String endAt;
}
