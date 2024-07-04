package cl.duoc.telopresto.web.services;

import cl.duoc.telopresto.web.apiclients.client.ClientClient;
import cl.duoc.telopresto.web.apiclients.client.ClientSearchRequest;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ClientService {
    private final ClientClient clientClient;

    public List<Client> getByRutOrName(String rut, String name, String lastName) {
        List<Client> clients = null;
        try {
            ClientSearchRequest clientSearchRequest = new ClientSearchRequest();
            clientSearchRequest.setRut(rut);
            clientSearchRequest.setName(name);
            clientSearchRequest.setLastName(lastName);
            clients = clientClient.getClients(clientSearchRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clients;
    }
}
