package cl.duoc.newrentacar.api.repository;

import cl.duoc.newrentacar.api.endpoint.model.CarBrand;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class CarBrandFirebaseRepository {
  public List<CarBrand> getAllBrands() {
    Firestore db = FirestoreClient.getFirestore();
    ApiFuture<QuerySnapshot> query = db.collection("car_brands").get();
    QuerySnapshot querySnapshot = null;
    try {
      querySnapshot = query.get();
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }
    List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
    List<CarBrand> carBrands = new ArrayList<>();
    for (QueryDocumentSnapshot document : documents) {
      CarBrand carBrand = document.toObject(CarBrand.class);
      carBrand.setId(document.getId());
      carBrands.add(carBrand);
    }
    return carBrands;
  }
}
