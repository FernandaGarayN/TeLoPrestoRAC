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

  public List<Car> getAllCars() {
    List<Car> finalCars = new ArrayList<>();
    List<CarEntity> cars = (List<CarEntity>) carRepository.findAll();
    for (CarEntity entity : cars) {
      finalCars.add(getCar(entity));
    }
    return finalCars;
  }

  public Car findCarById(int id) {
    Optional<CarEntity> foundCar = carRepository.findById(id);
    boolean isFound = foundCar.isPresent();
    if (isFound) {
      CarEntity dbCar = foundCar.get();
      return getCar(dbCar);
    }
    return null;
  }

  public Car deleteCarById(int id) {
    Optional<CarEntity> foundCar = carRepository.findById(id);
    boolean isFound = foundCar.isPresent();
    if (isFound) {
      CarEntity dbCar = foundCar.get();
      carRepository.delete(dbCar);
      return getCar(dbCar);
    }
    return null;
  }

  private static Car getCar(CarEntity dbCar) {
    Car car = new Car();
    //car.setId(dbCar.getId());
    car.setPlateCode(dbCar.getPlateCode());
    car.setBrand(dbCar.getBrand());
    car.setModel(dbCar.getModel());
    car.setSubsidiary(dbCar.getSubsidiary().getName());
    car.setColor(dbCar.getColor());
    car.setYear(dbCar.getYear());
    car.setCapacity(dbCar.getCapacity());
    car.setDailyCost(dbCar.getDailyCost());
    car.setType(dbCar.getType());
    car.setImage(dbCar.getImage());

    List<CarComment> comments = new ArrayList<>();

    dbCar.getReservations().forEach(reservations ->
      reservations.getComments().forEach(commentEntity ->
        comments.add(
          new CarComment(
            commentEntity.getId(),
            commentEntity.getDescription(),
            commentEntity.getRate()
          )
        )
      )
    );

    car.setComments(comments);

    return car;
  }

  public boolean addCar(Car aCar) {
    carRepository.save(getCarEntity(aCar));
    return true;
  }

  public boolean addFirebaseCar(Car aCar) {
    carFirebaseRepository.save(aCar);
    return true;
  }

  private static CarEntity getCarEntity(Car aCar) {
    CarEntity newCar = new CarEntity();
    newCar.setBrand(aCar.getBrand());
    newCar.setModel(aCar.getModel());
    //newCar.setSubsidiary(aCar.getSubsidiary());
    newCar.setColor(aCar.getColor());
    newCar.setPlateCode(aCar.getPlateCode());
    newCar.setType(aCar.getType());
    newCar.setCapacity(aCar.getCapacity());
    newCar.setYear(aCar.getYear());
    newCar.setDailyCost(aCar.getDailyCost());
    return newCar;
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

  public List<Car> search(
    String brand, String model, String color, Integer year, String subsidiary, Integer price) {
    List<CarEntity> found = carRepository.findCars(brand, model, color, year, subsidiary, price);
    List<Car> finalCars = new ArrayList<>();
    for (CarEntity entity : found) {
      finalCars.add(getCar(entity));
    }
    return finalCars;
  }

  public List<Car> searchFirebase(
    String brand, String model, String color, Integer year, String subsidiary, Integer price) {
    return carFirebaseRepository.findFirebaseCars(brand, model, color, year, subsidiary, price);
  }

  public Car findCarFirebaseById(String id) {
    return carFirebaseRepository.findCarById(id).orElseThrow();
  }

  public Car deleteCarFirebaseById(String id) {
    return carFirebaseRepository.delete(id).orElseThrow();
  }
}
