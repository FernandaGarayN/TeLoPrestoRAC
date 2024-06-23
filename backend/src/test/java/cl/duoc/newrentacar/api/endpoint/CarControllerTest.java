package cl.duoc.newrentacar.api.endpoint;

import cl.duoc.newrentacar.api.endpoint.model.Car;
import cl.duoc.newrentacar.api.endpoint.model.CarBrand;
import cl.duoc.newrentacar.api.endpoint.model.CarType;
import cl.duoc.newrentacar.api.service.CarBrandService;
import cl.duoc.newrentacar.api.service.CarService;
import cl.duoc.newrentacar.api.service.CarTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class CarControllerTest {
  @Mock
  private CarService carService;

  @Mock
  private CarTypeService carTypeService;

  @Mock
  private CarBrandService carBrandService;

  @InjectMocks
  private CarController carController;

  @BeforeEach
  void setUp() {
    openMocks(this);
  }

  @Test
  void get() {
    when(carService.getAllFirebaseCars()).thenReturn(listOfCars());
    var response = carController.get();
    assertNotNull(response);
    assertEquals(200, response.getStatusCode().value());
    assertNotNull(response.getBody());
    assertEquals(3, response.getBody().getCars().size());
  }

  private List<Car> listOfCars() {
    List<Car> cars = new ArrayList<>();
    Car oneCar = new Car();
    oneCar.setYear(2020);
    cars.add(oneCar);
    Car twoCar = new Car();
    twoCar.setYear(2021);
    cars.add(twoCar);
    Car threeCar = new Car();
    threeCar.setYear(2021);
    cars.add(threeCar);
    return cars;
  }

  @Test
  void getYears() {
    when(carService.getAllFirebaseCars()).thenReturn(listOfCars());
    var response = carController.getYears();
    assertNotNull(response);
    assertEquals(200, response.getStatusCode().value());
    assertNotNull(response.getBody());
    assertEquals(2, response.getBody().size());
  }

  @Test
  void searching() {
    when(carService.searchFirebase("brand", "model", "color", "type", 2021, "subsidiary", 1000)).thenReturn(listOfCars());
    var response = carController.searching("brand", "model", "color", "type", 2021, "subsidiary", 1000);
    assertNotNull(response);
    assertEquals(200, response.getStatusCode().value());
    assertNotNull(response.getBody());
    assertEquals(3, response.getBody().size());
  }

  @Test
  void getBrands() {
    when(carBrandService.getAllBrands()).thenReturn(listOfBrands());
    var response = carController.getBrands();
    assertNotNull(response);
    assertEquals(200, response.getStatusCode().value());
    assertNotNull(response.getBody());
    assertEquals(3, response.getBody().size());
  }

  private List<CarBrand> listOfBrands() {
    List<CarBrand> brands = new ArrayList<>();
    CarBrand oneBrand = new CarBrand();
    oneBrand.setName("brand1");
    brands.add(oneBrand);
    CarBrand twoBrand = new CarBrand();
    twoBrand.setName("brand2");
    brands.add(twoBrand);
    CarBrand threeBrand = new CarBrand();
    threeBrand.setName("brand3");
    brands.add(threeBrand);
    return brands;
  }

  @Test
  void testGet() {
    Car expectedCar = new Car();
    expectedCar.setId("id");
    when(carService.findCarFirebaseById("id")).thenReturn(expectedCar);
    when(carService.findCarFirebaseById("din")).thenReturn(null);
    var response200 = carController.get("id");
    assertNotNull(response200);
    assertEquals(200, response200.getStatusCode().value());
    assertNotNull(response200.getBody());
    Car car = response200.getBody().getCar();
    assertNotNull(car);
    assertEquals("id", car.getId());

    var response404 = carController.get("din");
    assertNotNull(response404);
    assertEquals(404, response404.getStatusCode().value());
    assertNull(response404.getBody());
  }

  @Test
  void delete() {
    Car expectedCar = new Car();
    expectedCar.setId("id");
    when(carService.deleteCarFirebaseById("id")).thenReturn(expectedCar);
    when(carService.deleteCarFirebaseById("din")).thenReturn(null);
    var response200 = carController.delete("id");
    assertNotNull(response200);
    assertEquals(200, response200.getStatusCode().value());
    assertNotNull(response200.getBody());
    Car car = response200.getBody().getCar();
    assertNotNull(car);
    assertEquals("id", car.getId());

    var response404 = carController.delete("din");
    assertNotNull(response404);
    assertEquals(404, response404.getStatusCode().value());
    assertNull(response404.getBody());
  }

  @Test
  void post() {
    Car aCar = new Car();
    when(carService.addFirebaseCar(aCar)).thenReturn(true);
    var response201 = carController.post(aCar);
    assertNotNull(response201);
    assertEquals(201, response201.getStatusCode().value());
    assertNull(response201.getBody());

    Car aCar1 = new Car();
    when(carService.addFirebaseCar(aCar1)).thenReturn(false);
    var response400 = carController.post(aCar1);
    assertNotNull(response400);
    assertEquals(400, response400.getStatusCode().value());
    assertNull(response400.getBody());
  }

  @Test
  void put() {
    Car aCar = new Car();
    aCar.setId("id");
    when(carService.updateCarById("id", aCar)).thenReturn(aCar);
    when(carService.updateCarById("din", aCar)).thenReturn(null);
    var response200 = carController.put("id", aCar);
    assertNotNull(response200);
    assertEquals(200, response200.getStatusCode().value());
    assertNotNull(response200.getBody());
    Car car = response200.getBody().getCar();
    assertNotNull(car);
    assertEquals("id", car.getId());

    var response400 = carController.put("din", aCar);
    assertNotNull(response400);
    assertEquals(400, response400.getStatusCode().value());
    assertNull(response400.getBody());
  }

  @Test
  void getTypes() {
    when(carTypeService.getAllCarTypes()).thenReturn(listOfTypes());
    var response = carController.getTypes();
    assertNotNull(response);
    assertEquals(200, response.getStatusCode().value());
    assertNotNull(response.getBody());
    assertEquals(3, response.getBody().size());
  }

  private List<CarType> listOfTypes() {
    List<CarType> types = new ArrayList<>();
    CarType oneType = new CarType();
    oneType.setName("type1");
    types.add(oneType);
    CarType twoType = new CarType();
    twoType.setName("type2");
    types.add(twoType);
    CarType threeType = new CarType();
    threeType.setName("type3");
    types.add(threeType);
    return types;
  }
}
