package cl.duoc.newrentacar.api.repository;

import cl.duoc.newrentacar.api.endpoint.model.Client;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public class ClientFirebaseRepository {
  public Optional<Client> getClientByRut(String rut) {
    Firestore dbFirestore = FirestoreClient.getFirestore();

    ApiFuture<QuerySnapshot> future = dbFirestore
      .collection("clients")
      .whereEqualTo("rut", rut)
      .get();

    List<QueryDocumentSnapshot> documents;
    try {
      documents = future.get().getDocuments();
    } catch (InterruptedException | ExecutionException e) {
      return Optional.empty();
    }

    if (documents.isEmpty()) {
      return Optional.empty();
    }

    DocumentSnapshot document = documents.get(0);
    Client object = document.toObject(Client.class);
    return Optional.of(object);
  }

  public List<Client> getClientByRutOrName(String rut, String name, String lastName) {
    Firestore dbFirestore = FirestoreClient.getFirestore();

    Query query = dbFirestore.collection("clients");
    if (rut != null && !rut.trim().isEmpty()) {
      query = query.whereEqualTo("rut", rut);
    }

    if (name != null && !name.trim().isEmpty()) {
      query = query.whereEqualTo("name", name);
    }

    if (lastName != null && !lastName.trim().isEmpty()) {
      query = query.whereEqualTo("lastname", lastName);
    }

    List<QueryDocumentSnapshot> documents;
    try {
      documents = query.get().get().getDocuments();
    } catch (InterruptedException | ExecutionException e) {
      return new ArrayList<>();
    }

    if (documents.isEmpty()) {
      return new ArrayList<>();
    }
    List<Client> clients = new ArrayList<>();

    for (QueryDocumentSnapshot document : documents) {
      Client client = document.toObject(Client.class);
      client.setId(document.getId());
      clients.add(client);
    }
    return clients;
  }

    public Client save(Client client) {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        dbFirestore.collection("clients").add(client);
        return client;
    }
}
