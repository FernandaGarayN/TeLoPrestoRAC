package cl.duoc.newrentacar.api.endpoint;

import cl.duoc.newrentacar.api.endpoint.model.CarComment;
import cl.duoc.newrentacar.api.endpoint.model.Payment;
import cl.duoc.newrentacar.api.endpoint.model.Reservation;
import cl.duoc.newrentacar.api.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
  @Autowired
  private ReservationService reservationService;

  @GetMapping("/by-username/{username}")
  public ResponseEntity<List<Reservation>> getByUsername(@PathVariable("username") String username
    , @RequestParam(value = "status", required = false) String status) {
    return ResponseEntity.ok(reservationService.findByUserName(username, status));
  }

  @PostMapping("")
  public ResponseEntity<Reservation> save(@RequestBody Reservation reservation) {
    return ResponseEntity.ok(reservationService.save(reservation));
  }

  @GetMapping("/{id}")
  public ResponseEntity<Reservation> getById(@PathVariable String id) {
    return ResponseEntity.ok(reservationService.findById(id));
  }

  @PutMapping("/{id}")
  public ResponseEntity<Reservation> update(@PathVariable String id, @RequestBody Reservation reservation) {
    return ResponseEntity.ok(reservationService.update(id, reservation));
  }

  @PostMapping("/{id}/payments")
  public ResponseEntity<Reservation> savePayment(@PathVariable String id, @RequestBody Payment payment) {
    return ResponseEntity.ok(reservationService.savePayment(id, payment));
  }

  @PutMapping("/{id}/confirm")
  public ResponseEntity<Reservation> confirm(@PathVariable String id) {
    return ResponseEntity.ok(reservationService.confirm(id));
  }

  @PutMapping("/{id}/cancel")
  public ResponseEntity<Reservation> cancel(@PathVariable String id) {
    return ResponseEntity.ok(reservationService.cancel(id));
  }
  @GetMapping("/by-car/{carId}")
  public ResponseEntity<List<Reservation>> findByCarId(@PathVariable("carId") String carId) {
    List<Reservation> reservations = reservationService.findByCarId(carId);
    return ResponseEntity.ok(reservations);
  }
  @GetMapping("/total-gift-points/{username}")
  public ResponseEntity<Integer> getTotalGiftPoints(@PathVariable String username) {
    int totalGiftPoints = reservationService.getTotalGiftPoints(username);
    return ResponseEntity.ok(totalGiftPoints);
  }
  @GetMapping("/current-by-car/{carId}")
  public ResponseEntity<List<Reservation>> getCurrentByCarId(@PathVariable("carId") String carId) {
    List<Reservation> reservations = reservationService.findCurrentReservationsByCarId(carId);
    return ResponseEntity.ok(reservations);
  }

  @PutMapping("/{id}/comment")
  public ResponseEntity<Reservation> comment(@PathVariable String id, @RequestBody CarComment comment) {
    return ResponseEntity.ok(reservationService.comment(id, comment));
  }
}

