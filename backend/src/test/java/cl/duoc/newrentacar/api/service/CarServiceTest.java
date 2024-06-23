package cl.duoc.newrentacar.api.service;

import cl.duoc.newrentacar.api.endpoint.model.Car;
import cl.duoc.newrentacar.api.repository.CarFirebaseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class CarServiceTest {
  @Mock
  private CarFirebaseRepository carFirebaseRepository;
  @InjectMocks
  private CarService carService;

  @BeforeEach
  void setUp() {
    openMocks(this);
  }

  @Test
  void init() {
    assertDoesNotThrow(() -> carService.init());
  }

  @Test
  void getAllFirebaseCars() {
    assertDoesNotThrow(() -> carService.getAllFirebaseCars());
  }

  @Test
  void addFirebaseCar() {
    assertDoesNotThrow(() -> carService.addFirebaseCar(new Car()));
  }

  @Test
  void updateCarById() {
    String number = "1";
    Car edited = new Car();
    edited.setId(number);
    Car stored = new Car();
    stored.setId(number);
    when(carFirebaseRepository.findCarById(number)).thenReturn(Optional.of(stored));
    var returned = carService.updateCarById(number, edited);
    assertNotNull(returned);
    assertEquals(number, returned.getId());
  }

  @Test
  void updateCarByIdCompleteEdit() {
    String number = "1";
    Car edited = new Car();
    edited.setId(number);
    edited.setBrand("brand");
    edited.setModel("model");
    edited.setSubsidiary("subsidiary");
    edited.setColor("color");
    edited.setPlateCode("plateCode");
    edited.setType("type");
    edited.setCapacity(0);
    edited.setYear(0);
    edited.setDailyCost(0);
    edited.setImage("image");
    edited.setExtension("extension");
    edited.setMimeType("mimeType");

    Car stored = new Car();
    stored.setId(number);
    when(carFirebaseRepository.findCarById(number)).thenReturn(Optional.of(stored));
    var returned = carService.updateCarById(number, edited);
    assertNotNull(returned);
    assertEquals(number, returned.getId());
  }

  @Test
  void updateCarByIdCompleteEdit0() {
    String number = "1";
    Car edited = new Car();
    edited.setId(number);
    edited.setBrand("brand");
    edited.setModel("model");
    edited.setSubsidiary("subsidiary");
    edited.setColor("color");
    edited.setPlateCode("plateCode");
    edited.setType("type");
    edited.setCapacity(0);
    edited.setYear(0);
    edited.setDailyCost(0);
    edited.setImage("image");
    edited.setExtension("extension");
    edited.setMimeType("mimeType");

    Car stored = new Car();
    stored.setId(number);
    stored.setBrand("brand");
    stored.setModel("model");
    stored.setSubsidiary("subsidiary");
    stored.setColor("color");
    stored.setPlateCode("plateCode");
    stored.setType("type");
    stored.setCapacity(0);
    stored.setYear(0);
    stored.setDailyCost(0);
    stored.setImage("image");
    stored.setExtension("extension");
    stored.setMimeType("mimeType");
    when(carFirebaseRepository.findCarById(number)).thenReturn(Optional.of(stored));
    var returned = carService.updateCarById(number, edited);
    assertNotNull(returned);
    assertEquals(number, returned.getId());
  }

  @Test
  void updateCarByIdNotFound() {
    String number = "1";
    Car edited = new Car();
    edited.setId(number);
    when(carFirebaseRepository.findCarById(number)).thenReturn(Optional.empty());
    var returned = carService.updateCarById(number, edited);
    assertNull(returned);
  }

  @Test
  void searchFirebase() {
    when(carFirebaseRepository.findFirebaseCars("brand", "model", "color", "type", 0, "subsidiary", 0)).thenReturn(new ArrayList<>());
    var returned = carService.searchFirebase("brand", "model", "color", "type", 0, "subsidiary", 0);
    assertNotNull(returned);
  }

  @Test
  void findCarFirebaseById() {
    String number = "1";
    Car stored = new Car();
    stored.setId(number);
    when(carFirebaseRepository.findCarById(number)).thenReturn(Optional.of(stored));
    var returned = carService.findCarFirebaseById(number);
    assertNotNull(returned);
    assertEquals(number, returned.getId());
  }

  @Test
  void deleteCarFirebaseById() {
    String number = "1";
    Car stored = new Car();
    stored.setId(number);
    when(carFirebaseRepository.delete(number)).thenReturn(Optional.of(stored));
    var returned = carService.deleteCarFirebaseById(number);
    assertNotNull(returned);
    assertEquals(number, returned.getId());
  }
}
