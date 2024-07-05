package cl.duoc.newrentacar.api.service;

import cl.duoc.newrentacar.api.endpoint.model.Client;
import cl.duoc.newrentacar.api.repository.ClientFirebaseRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {
  private ClientFirebaseRepository clientFirebaseRepository;

  @PostConstruct
  public void init() {
    clientFirebaseRepository = new ClientFirebaseRepository();
  }

  public Client findByRut(String rut) {
    return clientFirebaseRepository.getClientByRut(rut).orElse(null);
  }

  public List<Client> findByRutOrName(String rut, String name, String lastName) {
    return clientFirebaseRepository.getClientByRutOrName(rut, name, lastName);
  }

  public Client save(Client client) {
    return clientFirebaseRepository.save(client);
  }

  public List<Client> findAll() {
    return clientFirebaseRepository.getAllClients();
  }

  public boolean changeStatus(String id, String toStatus) {
    return clientFirebaseRepository.changeStatus(id, toStatus);
  }

  public void delete(String id) {
    clientFirebaseRepository.deleteClient(id);
  }

  public Client findById(String id) {
    return clientFirebaseRepository.getClientById(id).orElse(null);
  }

  public Client update(String id, Client client) {
    Client bdClient = clientFirebaseRepository.getClientById(id).orElse(null);
    if (bdClient != null) {
      bdClient.setId(id);
      bdClient.setName(client.getName());
      bdClient.setLastname(client.getLastname());
      bdClient.setRut(client.getRut());
      bdClient.setAddress(client.getAddress());
      bdClient.setPhoneNumber(client.getPhoneNumber());
      bdClient.setEmail(client.getEmail());
      clientFirebaseRepository.edit(bdClient);
    }
    return bdClient;
  }
}
