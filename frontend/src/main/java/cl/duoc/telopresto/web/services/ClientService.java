package cl.duoc.telopresto.web.services;

import cl.duoc.telopresto.web.apiclients.authboot.AuthbootUserClient;
import cl.duoc.telopresto.web.apiclients.client.ClientClient;
import cl.duoc.telopresto.web.apiclients.client.ClientSearchRequest;
import cl.duoc.telopresto.web.controller.client.EditClientForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class ClientService {
    private final ClientClient clientClient;
    private final AuthbootUserClient userClient;

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

    public List<Client> getAllClients() {
        return clientClient.getAllClients();
    }

    public void changeStatusToDisabled(String id) {
        Client client = clientClient.getClientById(id);
        if (client != null && client.getStatus().equals("Habilitado")) {
            try {
                userClient.statusDisabledByUsername(client.getUsername());
            } catch (Exception e) {
                log.warn("Error al deshabilitar el usuario: {}", e.getMessage());
            }
            clientClient.changeStatus(id, "Deshabilitado");
        }
    }

    public void changeStatusToEnabled(String id) {
        Client client = clientClient.getClientById(id);
        if (client != null && client.getStatus().equals("Deshabilitado")) {
            try {
                userClient.statusEnabledByUsername(client.getUsername());
            } catch (Exception e) {
                log.warn("Error al habilitar el usuario: {}", e.getMessage());
            }
            clientClient.changeStatus(id, "Habilitado");
        }
    }

    public void delete(String id) {
        Client client = clientClient.getClientById(id);
        try {
            userClient.deleteByUsername(client.getUsername());
            clientClient.deleteClient(id);
        } catch (Exception e) {
            log.warn("Error al eliminar el usuario: {}", e.getMessage());
        }
    }

    public Client getClientById(String id) {
        return clientClient.getClientById(id);
    }

    public void edit(String id, EditClientForm form) {
        Client client = new Client();
        client.setName(form.getName());
        client.setLastname(form.getLastname());
        client.setRut(form.getRut());
        client.setAddress(form.getAddress());
        client.setPhoneNumber(form.getPhone());
        client.setEmail(form.getEmail());
        clientClient.edit(id, client);
    }
}
