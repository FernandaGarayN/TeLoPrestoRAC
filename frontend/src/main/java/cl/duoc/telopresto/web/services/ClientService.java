package cl.duoc.telopresto.web.services;

import cl.duoc.telopresto.web.apiclients.client.ClientClient;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ClientService {
    private final ClientClient clientClient;

    public Client getByRut(String rut) {
        Client client = null;
        try {
            client = clientClient.getClient(rut);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return client;
    }
}
