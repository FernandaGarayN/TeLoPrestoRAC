package cl.duoc.newrentacar.api.endpoint.model;

import lombok.*;

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
  private String startAt;
  private String endAt;
  private List<Payment> payments;

  public void addPayment(Payment payment) {
    if (payments == null) {
      payments = new ArrayList<>();
    }
    payments.add(payment);
  }
}
