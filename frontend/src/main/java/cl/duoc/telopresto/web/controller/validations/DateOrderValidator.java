package cl.duoc.telopresto.web.controller.validations;

import cl.duoc.telopresto.web.controller.reservation.ReservationForm;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DateOrderValidator implements ConstraintValidator<ValidDateOrder, ReservationForm> {
    @Override
    public void initialize(ValidDateOrder constraintAnnotation) {
    }

    @Override
    public boolean isValid(ReservationForm form, ConstraintValidatorContext context) {
        if (form.getStartAt() == null || form.getEndAt() == null) {
            return true; // Consider using @NotNull on individual fields instead
        }
        return !form.getStartAt().isAfter(form.getEndAt());
    }
}
