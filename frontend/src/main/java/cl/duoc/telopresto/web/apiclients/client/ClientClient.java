package cl.duoc.telopresto.web.apiclients.client;

import cl.duoc.telopresto.web.config.feign.FeignClientConfig;
import cl.duoc.telopresto.web.services.Client;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(
        name = "client-client",
        url = "${spring.properties.feign.clients}",
        configuration = FeignClientConfig.class)
public interface ClientClient {

    @GetMapping("/{rut}")
    Client getClient(@PathVariable("rut") String rut);

    @PostMapping("/search")
    List<Client> getClients(@Valid @RequestBody ClientSearchRequest clientSearchRequest);

    @PostMapping
    void postClient(@RequestBody Client client);
}
