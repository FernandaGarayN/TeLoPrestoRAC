package cl.duoc.newrentacar.api.service;

import cl.duoc.newrentacar.api.endpoint.model.CarBrand;
import cl.duoc.newrentacar.api.repository.CarBrandFirebaseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class CarBrandServiceTest {
  @Mock
  private CarBrandFirebaseRepository carBrandFirebaseRepository;
  @InjectMocks
  private CarBrandService carBrandService;

  @BeforeEach
  void setUp() {
    openMocks(this);
  }

  @Test
  void init() {
    assertDoesNotThrow(() -> carBrandService.init());
  }

  @Test
  void getAllBrands() {
    List<CarBrand> carBrands = List.of();
    when(carBrandFirebaseRepository.getAllBrands()).thenReturn(carBrands);
    assertEquals(carBrands, carBrandService.getAllBrands());
  }
}
