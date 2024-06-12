package cl.duoc.newrentacar.api.endpoint;

import cl.duoc.newrentacar.api.endpoint.model.Car;
import cl.duoc.newrentacar.api.service.CarService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cars")
public class CarController {
  @Autowired
  private CarService carService;

  @GetMapping
  public ResponseEntity<GetCarsResponse> get() {
    GetCarsResponse response = new GetCarsResponse();
    response.setCars(carService.getAllFirebaseCars());
    return ResponseEntity.ok(response);
  }

  @GetMapping("/years")
  public ResponseEntity<List<Integer>> getYears() {
    List<Car> cars = carService.getAllFirebaseCars();
    List<Integer> years = new ArrayList<>();
    for (Car car : cars) {
      if (!years.contains(car.getYear())) {
        years.add(car.getYear());
      }
    }
    return ResponseEntity.ok(years);
  }

  @GetMapping("/searching")
  public ResponseEntity<List<Car>> searching(
    @RequestParam(value = "brand", required = false) String brand,
    @RequestParam(value = "model", required = false) String model,
    @RequestParam(value = "color", required = false) String color,
    @RequestParam(value = "year", required = false) Integer year,
    @RequestParam(value = "subsidiary", required = false) String subsidiary,
    @RequestParam(value = "price", required = false) Integer price
  ) {
    return ResponseEntity.ok(carService.searchFirebase(brand, model, color, year, subsidiary, price));
  }

  @GetMapping("/brands")
  public ResponseEntity<List<String>> getBrands() {
    List<Car> cars = carService.getAllFirebaseCars();
    List<String> brands = new ArrayList<>();
    for (Car car : cars) {
      if (!brands.contains(car.getBrand())) {
        brands.add(car.getBrand());
      }
    }
    return ResponseEntity.ok(brands);
  }

  @GetMapping("/{id}")
  public ResponseEntity<GetCarResponse> get(@PathVariable String id) {
    Car foundCar = carService.findCarFirebaseById(id);
    if (foundCar != null) {
      GetCarResponse response = new GetCarResponse();
      response.setCar(foundCar);
      return ResponseEntity.ok(response);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<GetCarResponse> delete(@PathVariable String id) {
    Car deletedCar = carService.deleteCarFirebaseById(id);
    if (deletedCar != null) {
      GetCarResponse response = new GetCarResponse();
      response.setCar(deletedCar);
      return ResponseEntity.ok(response);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping
  public ResponseEntity<GetCarResponse> post(@RequestBody Car aCar) {
    boolean added = carService.addFirebaseCar(aCar);
    if (added) {
      return ResponseEntity.status(HttpStatus.CREATED).build();
    } else {
      return ResponseEntity.badRequest().build();
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<GetCarResponse> put(@PathVariable("id") String id, @RequestBody Car aCar) {
    Car updated = carService.updateCarById(id, aCar);
    if (updated != null) {
      GetCarResponse response = new GetCarResponse();
      response.setCar(updated);
      return ResponseEntity.ok(response);
    } else {
      return ResponseEntity.badRequest().build();
    }
  }

  //aqui la nueva funcion
}
