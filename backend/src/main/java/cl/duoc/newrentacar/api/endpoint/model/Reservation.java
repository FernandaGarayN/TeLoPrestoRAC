package cl.duoc.newrentacar.api.endpoint.model;

import lombok.*;

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
}
