package cl.duoc.newrentacar.api.endpoint;

import cl.duoc.newrentacar.api.endpoint.model.Client;
import cl.duoc.newrentacar.api.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class ClientControllerTest {
  @Mock
  private ClientService clientService;

  @InjectMocks
  private ClientController clientController;

  @BeforeEach
  void setUp() {
    openMocks(this);
  }

  @Test
  void getClientByRut() {
    String rut = "12345678-9";
    when(clientService.findByRut(rut)).thenReturn(null);
    var response = clientController.getClientByRut(rut);
    assertNotNull(response);
    assertEquals(404, response.getStatusCode().value());
  }

  @Test
  void getClientByRutFound() {
    String rut = "12345678-9";
    var client = new Client();
    when(clientService.findByRut(rut)).thenReturn(client);
    var response = clientController.getClientByRut(rut);
    assertNotNull(response);
    assertEquals(200, response.getStatusCode().value());
    assertEquals(client, response.getBody());
  }
}
