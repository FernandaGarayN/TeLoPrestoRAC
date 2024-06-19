package cl.duoc.telopresto.web.services;

import lombok.*;

import java.time.LocalDate;

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
    private String status;
    private Integer dailyCost;
    private LocalDate startAt;
    private LocalDate endAt;
    private long days;
    private long amount;

    public void calculateTotal() {
        if (startAt != null && endAt != null) {
            days = 1 + (endAt.toEpochDay() - startAt.toEpochDay());
            amount = dailyCost * days;
        }
    }
}
