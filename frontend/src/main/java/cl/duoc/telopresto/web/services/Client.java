package cl.duoc.telopresto.web.services;

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
}
