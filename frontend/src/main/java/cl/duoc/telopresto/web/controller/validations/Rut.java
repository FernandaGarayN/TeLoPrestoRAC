package cl.duoc.telopresto.web.controller.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RutValidator.class)
@Documented
public @interface Rut {
    String message() default "El RUT es inválido";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
