package cl.duoc.newrentacar.api.service;

import cl.duoc.newrentacar.api.endpoint.model.Car;
import cl.duoc.newrentacar.api.endpoint.model.Payment;
import cl.duoc.newrentacar.api.endpoint.model.Reservation;
import cl.duoc.newrentacar.api.repository.*;
import cl.duoc.newrentacar.api.repository.model.CarEntity;
import cl.duoc.newrentacar.api.repository.model.ClientEntity;
import cl.duoc.newrentacar.api.repository.model.ReservationEntity;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationService {

  CarFirebaseRepository carFirebaseRepository;

  ReservationFirebaseRepository reservationFirebaseRepository;
  ;

  @PostConstruct
  public void init() {
    carFirebaseRepository = new CarFirebaseRepository();
    reservationFirebaseRepository = new ReservationFirebaseRepository();
  }

  public List<Reservation> findByUserName(String userName, String status) {
    return reservationFirebaseRepository.findByUserName(userName, status);
  }

  public Reservation save(Reservation reservation) {
    Car carEntity = carFirebaseRepository.findCarById(reservation.getCarId()).orElseThrow();

    reservation.setDailyCost(carEntity.getDailyCost());
    reservation.setStatus("pending");
    reservation.setBrand(carEntity.getBrand());
    reservation.setModel(carEntity.getModel());

    String id = reservationFirebaseRepository.save(reservation);

    reservation.setId(id);
    return reservation;
  }

  public Reservation savePayment(String id, Payment payment) {
    Reservation reservation = reservationFirebaseRepository.findById(id).orElseThrow();
    reservation.addPayment(payment);
    reservation.setStatus("paid");
    reservationFirebaseRepository.edit(reservation);
    return reservation;
  }

  public Reservation findById(String id) {
    return reservationFirebaseRepository.findById(id).orElseThrow();
  }

  public Reservation update(String id, Reservation update) {
    Reservation reservation = reservationFirebaseRepository.findById(id).orElseThrow();
    reservation.setStartAt(update.getStartAt());
    reservation.setEndAt(update.getEndAt());
    reservationFirebaseRepository.edit(reservation);
    return reservation;
  }

  public Reservation confirm(String id) {
    Reservation reservation = reservationFirebaseRepository.findById(id).orElseThrow();
    reservation.setStatus("released");

    // Calcular y asignar los puntos de regalo
    int giftPoints = calculateGiftPoints(reservation);
    reservation.setGiftPoints(giftPoints);

    reservationFirebaseRepository.edit(reservation);
    return reservation;
  }

  public Reservation cancel(String id) {
    Reservation reservation = reservationFirebaseRepository.findById(id).orElseThrow();
    reservation.setStatus("cancelled");
    reservationFirebaseRepository.edit(reservation);
    return reservation;
  }

  public List<Reservation> findByCarId(String carId) {
    return reservationFirebaseRepository.findByCarId(carId);
  }
  public int calculateGiftPoints(Reservation reservation) {
    int points = 0;

    // Puntos por Reservas Anticipadas
    LocalDate startDate = LocalDate.parse(reservation.getStartAt());
    LocalDate endDate = LocalDate.parse(reservation.getEndAt());
    long daysInAdvance = ChronoUnit.DAYS.between(LocalDate.now(), startDate);
    if (daysInAdvance >= 30) {
      points += 50;
    }

    // Puntos por Alquileres Frecuentes
    long rentalDays = ChronoUnit.DAYS.between(startDate, endDate);
    points += rentalDays * 50;

    // Bonus por alquilar más de 10 días en un mes
    if (rentalDays > 10) {
      points += 100;
    }

    return points;
  }
  public int getTotalGiftPoints(String username) {
    List<Reservation> reservations = findByUserName(username, null);
    return reservations.stream()
      .mapToInt(Reservation::getGiftPoints)
      .sum();
  }
}
