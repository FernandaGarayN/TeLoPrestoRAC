package cl.duoc.newrentacar.api.endpoint;

import cl.duoc.newrentacar.api.endpoint.model.Client;
import cl.duoc.newrentacar.api.endpoint.model.ClientSearchRequest;
import cl.duoc.newrentacar.api.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

  @PostMapping("/search")
  public ResponseEntity<List<Client>> getClientByRutOrName(@Validated @RequestBody ClientSearchRequest searchRequest) {
    List <Client> clients = clientService.findByRutOrName(searchRequest.getRut(), searchRequest.getName(), searchRequest.getLastName());
    if (clients != null) {
      return ResponseEntity.ok(clients);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping
  public ResponseEntity<Client> postClient(@RequestBody Client client) {
    Client savedClient = clientService.save(client);
    return ResponseEntity.ok(savedClient);
  }
}
