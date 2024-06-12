package cl.duoc.newrentacar.api.repository;

import cl.duoc.newrentacar.api.endpoint.model.Subsidiary;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SubsidiaryFirebaseRepository {
  public List<Subsidiary> getAllSubsidiaries() {
    Firestore db = FirestoreClient.getFirestore();
    ApiFuture<QuerySnapshot> query = db.collection("subsidiaries").get();
    QuerySnapshot querySnapshot = null;
    try {
      querySnapshot = query.get();
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }
    List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
    List<Subsidiary> subsidiaries = new ArrayList<>();
    for (QueryDocumentSnapshot document : documents) {
      Subsidiary subsidiary = document.toObject(Subsidiary.class);
      subsidiary.setId(document.getId());
      subsidiaries.add(subsidiary);
    }
    return subsidiaries;
  }
}
