package cl.duoc.telopresto.web.services;

import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {
    private String id;
    private String username;
    private String carId;
    private String brand;
    private String model;
    private Integer dailyCost;
    private String status;
    private LocalDate startAt;
    private LocalDate endAt;
    @Builder.Default
    private List<Payment> payments = new ArrayList<>();
    private CarComment comment;
    private long days;
    private long amount;

    public void calculateTotal() {
        if (startAt != null && endAt != null) {
            days = 1 + (endAt.toEpochDay() - startAt.toEpochDay());
            amount = dailyCost * days;
        }
    }

    public String getName() {
        return brand + " " + model + " ("+ startAt +" al "+ endAt +")";
    }
}
