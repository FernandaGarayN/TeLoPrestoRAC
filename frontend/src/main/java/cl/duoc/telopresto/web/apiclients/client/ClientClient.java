package cl.duoc.telopresto.web.apiclients.client;

import cl.duoc.telopresto.web.config.feign.FeignClientConfig;
import cl.duoc.telopresto.web.services.Client;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(
        name = "client-client",
        url = "${spring.properties.feign.clients}",
        configuration = FeignClientConfig.class)
public interface ClientClient {

    @GetMapping("/{rut}")
    Client getClient(@PathVariable("rut") String rut);

    @GetMapping("/by-id/{id}")
    Client getClientById(@PathVariable String id);

    @PostMapping("/search")
    List<Client> getClients(@Valid @RequestBody ClientSearchRequest clientSearchRequest);

    @PostMapping
    void postClient(@RequestBody Client client);

    @GetMapping
    List<Client> getAllClients();

    @PutMapping("/{id}/change-status/{to-status}")
    void changeStatus(@PathVariable("id") String id, @PathVariable("to-status")  String toStatus);

    @DeleteMapping("/{id}")
    void deleteClient(@PathVariable String id);

    @PatchMapping("/{id}")
    void edit(@PathVariable String id, @RequestBody Client client);
}
