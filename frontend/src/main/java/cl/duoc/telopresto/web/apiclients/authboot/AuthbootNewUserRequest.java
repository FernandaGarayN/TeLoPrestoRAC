package cl.duoc.telopresto.web.apiclients.authboot;

import java.util.Set;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthbootNewUserRequest {
  @NotBlank(message = "El nombre de usuario es requerido")
  private String username;
  @NotBlank(message = "La contraseña es requerida")
  private String password;
  @Email(message = "El email es requerido")
  private String email;
  @Valid
  @NotEmpty(message = "Debe ingresar al menos un rol")
  private Set<@NotBlank(message = "El rol no debe ser vacío") String> roles;
}
