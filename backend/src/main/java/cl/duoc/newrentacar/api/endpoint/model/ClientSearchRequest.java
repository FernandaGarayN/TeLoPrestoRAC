package cl.duoc.newrentacar.api.endpoint.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientSearchRequest {
  private String rut;
  private String name;
  private String lastName;

}
