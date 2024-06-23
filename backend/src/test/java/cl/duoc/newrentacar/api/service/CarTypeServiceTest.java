package cl.duoc.newrentacar.api.service;

import cl.duoc.newrentacar.api.repository.CarTypeFirebaseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.MockitoAnnotations.openMocks;

class CarTypeServiceTest {
  @Mock
  private CarTypeFirebaseRepository carTypeFirebaseRepository;
  @InjectMocks
  private CarTypeService carTypeService;

  @BeforeEach
  void setUp() {
    openMocks(this);
  }

  @Test
  void init() {
    assertDoesNotThrow(() -> carTypeService.init());
  }

  @Test
  void getAllCarTypes() {
    assertDoesNotThrow(() -> carTypeService.getAllCarTypes());
  }
}
