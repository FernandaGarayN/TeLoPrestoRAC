package cl.duoc.telopresto.web.controller.validations;

import cl.duoc.telopresto.web.controller.reservation.ReservationForm;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateOrderValidator implements ConstraintValidator<ValidDateOrder, ReservationForm> {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    @Override
    public boolean isValid(ReservationForm form, ConstraintValidatorContext context) {
        if (form.getStartAt() == null || form.getEndAt() == null) {
            return true;
        }
        LocalDate startDate = formatDate(form.getStartAt());
        LocalDate endDate = formatDate(form.getEndAt());
        return startDate != null && endDate != null && !startDate.isAfter(endDate);
    }

    private static LocalDate formatDate(String date) {
        try {
            return LocalDate.parse(date, formatter);
        } catch (DateTimeParseException e) {
            return null;
        }
    }
}
