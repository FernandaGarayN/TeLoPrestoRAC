package cl.duoc.telopresto.web.controller.reservation;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentForm {
    @NotBlank(message = "El comentario no puede estar vacío")
    @Size(max = 5000, message = "El comentario no puede tener más de {max} caracteres")
    private String comment;

    @Min(value = 0, message = "La calificación no puede ser menor a {value}")
    @Max(value = 5, message = "La calificación no puede ser mayor a {value}")
    private Integer rating;
}
