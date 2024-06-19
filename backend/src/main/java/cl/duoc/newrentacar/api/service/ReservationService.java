package cl.duoc.newrentacar.api.service;

import cl.duoc.newrentacar.api.endpoint.model.Car;
import cl.duoc.newrentacar.api.endpoint.model.Reservation;
import cl.duoc.newrentacar.api.repository.*;
import cl.duoc.newrentacar.api.repository.model.CarEntity;
import cl.duoc.newrentacar.api.repository.model.ClientEntity;
import cl.duoc.newrentacar.api.repository.model.ReservationEntity;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationService {

  @Autowired
  ClientRepository clientRepository;

  @Autowired
  CarRepository carRepository;

  CarFirebaseRepository carFirebaseRepository;

  ReservationFirebaseRepository  reservationFirebaseRepository;;

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
}
