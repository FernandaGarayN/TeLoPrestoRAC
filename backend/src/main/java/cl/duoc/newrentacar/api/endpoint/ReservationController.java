package cl.duoc.newrentacar.api.endpoint;

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
}

