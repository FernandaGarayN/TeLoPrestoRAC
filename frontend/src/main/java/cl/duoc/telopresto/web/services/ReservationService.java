package cl.duoc.telopresto.web.services;

import cl.duoc.telopresto.web.apiclients.reservation.ReservationClient;
import cl.duoc.telopresto.web.controller.reservation.CommentForm;
import cl.duoc.telopresto.web.controller.reservation.ReservationForm;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class ReservationService {
    private final ReservationClient reservationClient;

    public List<Reservation> findByUsername(String username) {
        List<Reservation> byUsername = reservationClient.findByUsername(username, null);
        byUsername.forEach(Reservation::calculateTotal);
        return byUsername;
    }

    public Reservation save(ReservationForm form, String username) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return reservationClient.save(Reservation.builder()
                .username(username)
                .carId(form.getCarId())
                .startAt(LocalDate.parse(form.getStartAt(), formatter))
                .endAt(LocalDate.parse(form.getEndAt(), formatter))
                .build());
    }

    public List<Reservation> findByUsernameAndPending(String username) {
        List<Reservation> pending = reservationClient.findByUsername(username, "pending");
        pending.forEach(Reservation::calculateTotal);
        return pending;
    }

    public Optional<Reservation> findById(String reservationId) {
        Reservation byId = reservationClient.findById(reservationId);
        if (byId == null) {
            return Optional.empty();
        }
        byId.calculateTotal();
        return Optional.of(byId);
    }

    public Reservation update(ReservationForm form, String id) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return reservationClient.update(id, Reservation.builder()
                .id(id)
                .carId(form.getCarId())
                .startAt(LocalDate.parse(form.getStartAt(), formatter))
                .endAt(LocalDate.parse(form.getEndAt(), formatter))
                .build());
    }

    public void confirm(String id) {
        reservationClient.confirm(id);
    }

    public void cancel(String id) {
        reservationClient.cancel(id);
    }

    public void comment(String id, CommentForm form) {
        reservationClient.comment(id, form);
    }
}
