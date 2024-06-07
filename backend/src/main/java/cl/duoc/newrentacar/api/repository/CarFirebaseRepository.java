package cl.duoc.newrentacar.api.repository;

import cl.duoc.newrentacar.api.endpoint.model.Car;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CarFirebaseRepository {
  public List<Car> getAllCars() {
    Firestore db = FirestoreClient.getFirestore();
    try {
      QuerySnapshot querySnapshot = db.collection("cars").get().get();
      return querySnapshot.getDocuments().stream()
        .map(document -> document.toObject(Car.class))
        .collect(Collectors.toList());
    } catch (Exception e) {
      e.printStackTrace();
      return Collections.emptyList();
    }
  }

  public void save(Car aCar) {
    Firestore db = FirestoreClient.getFirestore();
    db.collection("cars").add(aCar);
  }
}
