package cl.duoc.newrentacar.api.service;

import cl.duoc.newrentacar.api.endpoint.model.Car;
import cl.duoc.newrentacar.api.endpoint.model.CarComment;
import cl.duoc.newrentacar.api.endpoint.model.Payment;
import cl.duoc.newrentacar.api.endpoint.model.Reservation;
import cl.duoc.newrentacar.api.repository.CarFirebaseRepository;
import cl.duoc.newrentacar.api.repository.ReservationFirebaseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class ReservationServiceTest {
  @Mock
  private CarFirebaseRepository  carFirebaseRepository;
  @Mock
  private ReservationFirebaseRepository reservationFirebaseRepository;
  @InjectMocks
  private ReservationService reservationService;

  @BeforeEach
  void setUp() {
    openMocks(this);
  }

  @Test
  void init() {
    assertDoesNotThrow(() -> reservationService.init());
  }

  @Test
  void findByUserName() {
    String userName = "user";
    String status = "status";
    when(reservationFirebaseRepository.findByUserName(userName, status)).thenReturn(listOfReservations());
    List<Reservation> reservations = reservationService.findByUserName(userName, status);
    assertEquals(3, reservations.size());
  }

  private List<Reservation> listOfReservations() {
    Reservation e1 = new Reservation();
    e1.setStartAt("2024-09-01");
    e1.setEndAt("2024-09-03");
    Reservation e2 = new Reservation();
    e2.setStartAt("2024-06-01");
    e2.setEndAt("2024-07-03");
    Reservation e3 = new Reservation();
    e3.setStartAt("2024-07-01");
    e3.setEndAt("2024-07-03");
    return List.of(
      e1,
      e2,
      e3
    );
  }

  @Test
  void save() {
    Reservation reservation = new Reservation();
    reservation.setCarId("carId");
    when(carFirebaseRepository.findCarById(reservation.getCarId())).thenReturn(Optional.of(new Car()));
    when(reservationFirebaseRepository.save(reservation)).thenReturn("id");
    Reservation savedReservation = reservationService.save(reservation);
    assertEquals("id", savedReservation.getId());
  }

  @Test
  void savePayment() {
    String id = "id";
    Payment payment = new Payment();
    Reservation reservation = new Reservation();
    when(reservationFirebaseRepository.findById(id)).thenReturn(Optional.of(reservation));
    Reservation savedReservation = reservationService.savePayment(id, payment);
    assertEquals("paid", savedReservation.getStatus());
  }

  @Test
  void findById() {
    String id = "id";
    Reservation reservation = new Reservation();
    when(reservationFirebaseRepository.findById(id)).thenReturn(Optional.of(reservation));
    Reservation foundReservation = reservationService.findById(id);
    assertNotNull(foundReservation);
  }

  @Test
  void update() {
    String id = "id";
    Reservation update = new Reservation();
    Reservation reservation = new Reservation();
    when(reservationFirebaseRepository.findById(id)).thenReturn(Optional.of(reservation));
    Reservation updatedReservation = reservationService.update(id, update);
    assertEquals(update.getStartAt(), updatedReservation.getStartAt());
    assertEquals(update.getEndAt(), updatedReservation.getEndAt());
  }

  @Test
  void confirm() {
    String id = "id";
    Reservation reservation = new Reservation();
    reservation.setStartAt("2021-09-01");
    reservation.setEndAt("2021-09-03");
    when(reservationFirebaseRepository.findById(id)).thenReturn(Optional.of(reservation));
    Reservation confirmedReservation = reservationService.confirm(id);
    assertEquals("released", confirmedReservation.getStatus());
  }

  @Test
  void confirmExtraPoints() {
    String id = "id";
    Reservation reservation = new Reservation();
    reservation.setStartAt("2024-08-01");
    reservation.setEndAt("2024-09-03");
    when(reservationFirebaseRepository.findById(id)).thenReturn(Optional.of(reservation));
    Reservation confirmedReservation = reservationService.confirm(id);
    assertEquals("released", confirmedReservation.getStatus());
  }

  @Test
  void cancel() {
    String id = "id";
    Reservation reservation = new Reservation();
    when(reservationFirebaseRepository.findById(id)).thenReturn(Optional.of(reservation));
    Reservation cancelledReservation = reservationService.cancel(id);
    assertEquals("cancelled", cancelledReservation.getStatus());
  }

  @Test
  void findByCarId() {
    String carId = "carId";
    when(reservationFirebaseRepository.findByCarId(carId)).thenReturn(listOfReservations());
    List<Reservation> reservations = reservationService.findByCarId(carId);
    assertEquals(3, reservations.size());
  }

  @Test
  void getTotalGiftPoints() {
    String userName = "user";
    String status = "status";
    when(reservationFirebaseRepository.findByUserName(userName, status)).thenReturn(listOfReservations());
    int giftPoints = reservationService.getTotalGiftPoints(userName);
    assertEquals(0, giftPoints);
  }

  @Test
  void findCurrentReservationsByCarId() {
    String carId = "carId";
    when(reservationFirebaseRepository.findByCarId(carId)).thenReturn(listOfReservations());
    List<Reservation> reservations = reservationService.findCurrentReservationsByCarId(carId);
    assertEquals(1, reservations.size());
  }

  @Test
  void comment() {
    String id = "id";
    String comment = "comment";
    CarComment carComment = new CarComment();
    carComment.setComment(comment);
    carComment.setRating(5);
    Reservation reservation = new Reservation();
    when(reservationFirebaseRepository.findById(id)).thenReturn(Optional.of(reservation));
    Reservation commentedReservation = reservationService.comment(id, carComment);
    assertEquals(carComment, commentedReservation.getComment());
  }
}
