package cl.duoc.telopresto.web.apiclients.reservation;


import cl.duoc.telopresto.web.config.feign.FeignReservationConfig;
import cl.duoc.telopresto.web.services.Reservation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@FeignClient(
        name = "reservation-client",
        url = "${spring.properties.feign.reservations}",
        configuration = FeignReservationConfig.class)
public interface ReservationClient {
    @GetMapping("/by-username/{username}")
    List<Reservation> findByUsername(@PathVariable("username") String username
            , @RequestParam(value = "status",required = false) String status);

    @PostMapping
    Reservation save(Reservation reservation);

    @GetMapping("/{reservationId}")
    Reservation findById(@PathVariable String reservationId);

    @PutMapping("/{reservationId}")
    Reservation update(@PathVariable String reservationId, Reservation build);

    @PutMapping("/{reservationId}/confirm")
    void confirm(@PathVariable String reservationId);

    @PutMapping("/{reservationId}/cancel")
    void cancel(@PathVariable String reservationId);
}
