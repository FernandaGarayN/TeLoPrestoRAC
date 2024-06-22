package cl.duoc.newrentacar.api.service;

import cl.duoc.newrentacar.api.endpoint.model.Car;
import cl.duoc.newrentacar.api.endpoint.model.CarComment;
import cl.duoc.newrentacar.api.repository.CarFirebaseRepository;
import cl.duoc.newrentacar.api.repository.CarRepository;
import cl.duoc.newrentacar.api.repository.model.CarEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CarService {
  @Autowired
  private CarRepository carRepository;
  private CarFirebaseRepository carFirebaseRepository;

  @PostConstruct
  public void init() {
    carFirebaseRepository = new CarFirebaseRepository();
  }

  public List<Car> getAllFirebaseCars() {
    return carFirebaseRepository.getAllCars();
  }

  public boolean addFirebaseCar(Car aCar) {
    carFirebaseRepository.save(aCar);
    return true;
  }

  public Car updateCarById(String id, Car aCar) {
    Optional<Car> foundCar = carFirebaseRepository.findCarById(id);
    boolean isFound = foundCar.isPresent();
    if (isFound) {
      Car existingCar = foundCar.get();
      if (aCar.getBrand() != null && !aCar.getBrand().equals(existingCar.getBrand())) {
        existingCar.setBrand(aCar.getBrand());
      }
      if (aCar.getModel() != null && !aCar.getModel().equals(existingCar.getModel())) {
        existingCar.setModel(aCar.getModel());
      }
      if (aCar.getSubsidiary() != null && !aCar.getSubsidiary().equals(existingCar.getSubsidiary())) {
        existingCar.setSubsidiary(aCar.getSubsidiary());
      }
      if (aCar.getColor() != null && !aCar.getColor().equals(existingCar.getColor())) {
        existingCar.setColor(aCar.getColor());
      }
      if (aCar.getPlateCode() != null && !aCar.getPlateCode().equals(existingCar.getPlateCode())) {
        existingCar.setPlateCode(aCar.getPlateCode());
      }
      if (aCar.getType() != null && !aCar.getType().equals(existingCar.getType())) {
        existingCar.setType(aCar.getType());
      }
      if (aCar.getCapacity() != null && !aCar.getCapacity().equals(existingCar.getCapacity())) {
        existingCar.setCapacity(aCar.getCapacity());
      }
      if (aCar.getYear() != null && !aCar.getYear().equals(existingCar.getYear())) {
        existingCar.setYear(aCar.getYear());
      }
      if (aCar.getDailyCost() != null && !aCar.getDailyCost().equals(existingCar.getDailyCost())) {
        existingCar.setDailyCost(aCar.getDailyCost());
      }
      boolean newImage = false;
      if (aCar.getImage() != null && !aCar.getImage().equals(existingCar.getImage())) {
        newImage = true;
        existingCar.setImage(aCar.getImage());
        existingCar.setExtension(aCar.getExtension());
        existingCar.setMimeType(aCar.getMimeType());
      }
      carFirebaseRepository.edit(id, existingCar, newImage);
      return aCar;
    }
    return null;
  }

  public List<Car> searchFirebase(
    String brand, String model, String color, String type, Integer year, String subsidiary, Integer price) {
    return carFirebaseRepository.findFirebaseCars(brand, model, color, type, year, subsidiary, price);
  }

  public Car findCarFirebaseById(String id) {
    return carFirebaseRepository.findCarById(id).orElseThrow();
  }

  public Car deleteCarFirebaseById(String id) {
    return carFirebaseRepository.delete(id).orElseThrow();
  }
}
