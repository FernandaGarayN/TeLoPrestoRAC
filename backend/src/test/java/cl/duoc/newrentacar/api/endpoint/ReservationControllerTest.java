package cl.duoc.newrentacar.api.endpoint;

import cl.duoc.newrentacar.api.endpoint.model.Reservation;
import cl.duoc.newrentacar.api.service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class ReservationControllerTest {
  @Mock
  private ReservationService reservationService;

  @InjectMocks
  private ReservationController reservationController;

  @BeforeEach
  void setUp() {
    openMocks(this);
  }

  @Test
  void getByUsername() {
    String username = "username";
    when(reservationService.findByUserName(username, null)).thenReturn(null);
    var response = reservationController.getByUsername(username, null);
    assertEquals(200, response.getStatusCode().value());
    assertNull(response.getBody());
  }

  @Test
  void save() {
    Reservation reservation = new Reservation();
    when(reservationService.save(reservation)).thenReturn(reservation);;
    var response = reservationController.save(reservation);
    assertEquals(200, response.getStatusCode().value());
    assertNotNull(response.getBody());
    assertEquals(reservation, response.getBody());
  }

  @Test
  void getById() {
    String id = "id";
    Reservation reservation = new Reservation();
    when(reservationService.findById(id)).thenReturn(reservation);
    var response = reservationController.getById(id);
    assertEquals(200, response.getStatusCode().value());
    assertNotNull(response.getBody());
    assertEquals(reservation, response.getBody());
  }

  @Test
  void update() {
    String id = "id";
    Reservation reservation = new Reservation();
    when(reservationService.update(id, reservation)).thenReturn(reservation);
    var response = reservationController.update(id, reservation);
    assertEquals(200, response.getStatusCode().value());
    assertNotNull(response.getBody());
    assertEquals(reservation, response.getBody());
  }

  @Test
  void savePayment() {
    String id = "id";
    Reservation reservation = new Reservation();
    when(reservationService.savePayment(id, null)).thenReturn(reservation);
    var response = reservationController.savePayment(id, null);
    assertEquals(200, response.getStatusCode().value());
    assertNotNull(response.getBody());
    assertEquals(reservation, response.getBody());
  }

  @Test
  void confirm() {
    String id = "id";
    Reservation reservation = new Reservation();
    when(reservationService.confirm(id)).thenReturn(reservation);
    var response = reservationController.confirm(id);
    assertEquals(200, response.getStatusCode().value());
    assertNotNull(response.getBody());
    assertEquals(reservation, response.getBody());
  }

  @Test
  void cancel() {
    String id = "id";
    Reservation reservation = new Reservation();
    when(reservationService.cancel(id)).thenReturn(reservation);
    var response = reservationController.cancel(id);
    assertEquals(200, response.getStatusCode().value());
    assertNotNull(response.getBody());
    assertEquals(reservation, response.getBody());
  }

  @Test
  void findByCarId() {
    String carId = "carId";
    when(reservationService.findByCarId(carId)).thenReturn(null);
    var response = reservationController.findByCarId(carId);
    assertEquals(200, response.getStatusCode().value());
    assertNull(response.getBody());
  }

  @Test
  void getTotalGiftPoints() {
    String username = "username";
    when(reservationService.getTotalGiftPoints(username)).thenReturn(0);
    var response = reservationController.getTotalGiftPoints(username);
    assertEquals(200, response.getStatusCode().value());
    assertEquals(0, response.getBody());
  }

  @Test
  void getCurrentByCarId() {
    String carId = "carId";
    when(reservationService.findCurrentReservationsByCarId(carId)).thenReturn(null);
    var response = reservationController.getCurrentByCarId(carId);
    assertEquals(200, response.getStatusCode().value());
    assertNull(response.getBody());
  }

  @Test
  void comment() {
    String id = "id";
    Reservation reservation = new Reservation();
    when(reservationService.comment(id, null)).thenReturn(reservation);
    var response = reservationController.comment(id, null);
    assertEquals(200, response.getStatusCode().value());
    assertNotNull(response.getBody());
    assertEquals(reservation, response.getBody());
  }
}
