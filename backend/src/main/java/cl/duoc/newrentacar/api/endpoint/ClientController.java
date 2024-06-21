package cl.duoc.newrentacar.api.endpoint;

import cl.duoc.newrentacar.api.endpoint.model.Client;
import cl.duoc.newrentacar.api.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clients")
public class ClientController {
  @Autowired
  private ClientService clientService;

  @GetMapping("/{rut}")
  public ResponseEntity<Client> getClientByRut(@PathVariable String rut) {
    Client client = clientService.findByRut(rut);
    if (client != null) {
      return ResponseEntity.ok(client);
    } else {
      return ResponseEntity.notFound().build();
    }
  }
}
