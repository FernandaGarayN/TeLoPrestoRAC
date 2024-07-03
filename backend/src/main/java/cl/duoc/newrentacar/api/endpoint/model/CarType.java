package cl.duoc.newrentacar.api.endpoint.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarType {
  private String id;
  private String name;
  private String description;
  private String image;
}
