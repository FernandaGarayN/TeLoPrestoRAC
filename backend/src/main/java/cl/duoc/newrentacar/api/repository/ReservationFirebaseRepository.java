package cl.duoc.newrentacar.api.repository;

import cl.duoc.newrentacar.api.endpoint.model.Reservation;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public class ReservationFirebaseRepository {

  public String save(Reservation reservation) {
    Firestore db = FirestoreClient.getFirestore();  // Save the car and get a reference to the saved document
    ApiFuture<DocumentReference> addedDocRef = db.collection("reservations").add(reservation);
    DocumentReference docRef = null;
    try {
      docRef = addedDocRef.get();
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }

    return docRef.getId();
  }

  public List<Reservation> findByUserName(String userName, String status) {
    Firestore db = FirestoreClient.getFirestore();
    var query = db.collection("reservations").whereEqualTo("username", userName);

    if (status != null && !status.isEmpty()) {
      query = query.whereEqualTo("status", status);
    }

    ApiFuture<QuerySnapshot> querySnapshot = query.get();

    List<Reservation> reservations = new ArrayList<>();
    try {
      for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
        Reservation object = document.toObject(Reservation.class);
        assert object != null;
        object.setId(document.getId());
        reservations.add(object);
      }
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }

    return reservations;
  }

  public Optional<Reservation> findById(String id) {
    Firestore db = FirestoreClient.getFirestore();
    DocumentReference docRef = db.collection("reservations").document(id);
    ApiFuture<DocumentSnapshot> future = docRef.get();
    DocumentSnapshot document;
    try {
      document = future.get();
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
      return Optional.empty();
    }

    assert document != null;
    Reservation object = document.toObject(Reservation.class);
    assert object != null;
    object.setId(document.getId());
    return Optional.of(object);
  }

  public void edit(Reservation reservation) {
    Firestore db = FirestoreClient.getFirestore();
    db.collection("reservations").document(reservation.getId()).set(reservation);
  }
  public List<Reservation> findByCarId(String carId) {
    Firestore db = FirestoreClient.getFirestore();
    var query = db.collection("reservations").whereEqualTo("carId", carId);

    ApiFuture<QuerySnapshot> querySnapshot = query.get();

    List<Reservation> reservations = new ArrayList<>();
    try {
      for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
        Reservation object = document.toObject(Reservation.class);
        assert object != null;
        object.setId(document.getId());
        reservations.add(object);
      }
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }

    return reservations;
  }
}
