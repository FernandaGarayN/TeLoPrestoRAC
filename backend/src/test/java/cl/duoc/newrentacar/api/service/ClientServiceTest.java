package cl.duoc.newrentacar.api.service;

import cl.duoc.newrentacar.api.repository.ClientFirebaseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.MockitoAnnotations.openMocks;

class ClientServiceTest {
  @Mock
  private ClientFirebaseRepository clientFirebaseRepository;

  @InjectMocks
  private ClientService clientService;

  @BeforeEach
  void setUp() {
    openMocks(this);
  }

  @Test
  void init() {
    assertDoesNotThrow(() -> clientService.init());
  }

  @Test
  void findByRut() {
    String rut = "1";
    var client = clientService.findByRut(rut);
    assertNull(client);
  }
}
