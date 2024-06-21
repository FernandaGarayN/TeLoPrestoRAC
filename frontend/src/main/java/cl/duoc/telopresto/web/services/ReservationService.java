package cl.duoc.telopresto.web.services;

import cl.duoc.telopresto.web.apiclients.reservation.ReservationClient;
import cl.duoc.telopresto.web.controller.reservation.ReservationForm;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class ReservationService {
    private final ReservationClient reservationClient;

    public List<Reservation> findByUsername(String username){
        List<Reservation> byUsername = reservationClient.findByUsername(username, null);
        byUsername.forEach(Reservation::calculateTotal);
        return byUsername;
    }

    public Reservation save(ReservationForm form, String username) {
        return reservationClient.save(Reservation.builder()
                .username(username)
                .carId(form.getCarId())
                .startAt(form.getStartAt())
                .endAt(form.getEndAt())
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
}
