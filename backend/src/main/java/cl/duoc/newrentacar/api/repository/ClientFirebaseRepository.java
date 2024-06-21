package cl.duoc.newrentacar.api.repository;

import cl.duoc.newrentacar.api.endpoint.model.Client;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;

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

    DocumentSnapshot document = documents.get(0);
    Client object = document.toObject(Client.class);
    return Optional.of(object);
  }
}
