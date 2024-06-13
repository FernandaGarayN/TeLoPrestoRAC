package cl.duoc.newrentacar.api.repository;

import cl.duoc.newrentacar.api.endpoint.model.CarType;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class CarTypeFirebaseRepository {

  public List<CarType> getAllCarTypes() {
    Firestore db = FirestoreClient.getFirestore();
    ApiFuture<QuerySnapshot> query = db.collection("car_types").get();
    QuerySnapshot querySnapshot = null;
    try {
      querySnapshot = query.get();
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }
    List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
    List<CarType> carTypes = new ArrayList<>();
    for (QueryDocumentSnapshot document : documents) {
      CarType carType = document.toObject(CarType.class);
      carType.setId(document.getId());
      carTypes.add(carType);
    }
    return carTypes;
  }

}
