package cl.duoc.newrentacar.api.endpoint.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Client {
  private String name;
  private String lastname;
  private String email;
  private Integer phoneNumber;
  private String address;
  private String rut;
  private String username;
  private Integer giftPoints;
  private String id;
  private String status;
}
