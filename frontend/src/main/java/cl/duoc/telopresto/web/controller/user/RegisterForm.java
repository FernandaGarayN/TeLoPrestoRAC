package cl.duoc.telopresto.web.controller.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterForm {
    @NotBlank(message = "El nombre es requerido")
    private String name;
    @NotBlank(message = "El apellido es requerido")
    private String lastname;
    @Email(message = "El email es requerido y debe ser válido")
    private String email;
    @NotBlank(message = "La contraseña es requerida")
    private String password;
    @NotBlank(message = "La confirmación de contraseña es requerida")
    private String passwordConfirmation;
    @NotBlank(message = "El RUT es requerido")
    private String rut;
    @NotBlank(message = "La dirección es requerida")
    private String address;
    @NotNull(message = "El teléfono es requerido")
    private Integer phone;
}
