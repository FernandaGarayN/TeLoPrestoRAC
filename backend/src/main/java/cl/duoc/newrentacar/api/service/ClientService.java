package cl.duoc.newrentacar.api.service;

import cl.duoc.newrentacar.api.endpoint.model.Client;
import cl.duoc.newrentacar.api.repository.ClientFirebaseRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

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
}
