package cl.duoc.telopresto.web.services;

import cl.duoc.telopresto.web.apiclients.client.ClientClient;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ClientService {
    private final ClientClient clientClient;

    public Client getByRut(String rut) {
        return clientClient.getClient(rut);
    }
}
