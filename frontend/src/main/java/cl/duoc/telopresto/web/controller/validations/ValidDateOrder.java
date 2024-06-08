package cl.duoc.telopresto.web.controller.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateOrderValidator.class)
public @interface ValidDateOrder {
    String message() default "La fecha de inicio debe ser antes o igual a la fecha de fin";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
