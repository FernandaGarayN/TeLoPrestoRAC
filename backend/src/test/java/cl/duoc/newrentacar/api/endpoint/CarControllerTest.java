package cl.duoc.newrentacar.api.endpoint;

import cl.duoc.newrentacar.api.service.CarBrandService;
import cl.duoc.newrentacar.api.service.CarService;
import cl.duoc.newrentacar.api.service.CarTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
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
  }

  @Test
  void getYears() {
  }

  @Test
  void searching() {
  }

  @Test
  void getBrands() {
  }

  @Test
  void testGet() {
  }

  @Test
  void delete() {
  }

  @Test
  void post() {
  }

  @Test
  void put() {
  }

  @Test
  void getTypes() {
  }
}
