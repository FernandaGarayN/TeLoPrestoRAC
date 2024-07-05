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

  @GetMapping("/by-id/{id}")
  public ResponseEntity<Client> getClientById(@PathVariable String id) {
    Client client = clientService.findById(id);
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

  @GetMapping
  public ResponseEntity<List<Client>> getClients() {
    List<Client> clients = clientService.findAll();
    return ResponseEntity.ok(clients);
  }

  @PutMapping("/{id}/change-status/{to-status}")
  public ResponseEntity<Void> changeStatus(@PathVariable String id, @PathVariable("to-status") String toStatus) {
    if (clientService.changeStatus(id, toStatus)) {
      return ResponseEntity.ok().build();
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteClient(@PathVariable String id) {
    clientService.delete(id);
    return ResponseEntity.ok().build();
  }

  @PatchMapping("/{id}")
  public ResponseEntity<Client> patchClient(@PathVariable String id, @RequestBody Client client) {
    Client updatedClient = clientService.update(id, client);
    return ResponseEntity.ok(updatedClient);
  }
}
