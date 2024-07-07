package cl.duoc.newrentacar.api.repository;

import cl.duoc.newrentacar.api.endpoint.model.Car;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.cloud.storage.*;
import com.google.cloud.storage.Blob;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.cloud.StorageClient;
import lombok.extern.slf4j.Slf4j;

import java.net.URL;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Slf4j
public class CarFirebaseRepository {
  public List<Car> getAllCars() {
    Firestore db = FirestoreClient.getFirestore();
    try {
      QuerySnapshot querySnapshot =
        db.collection("cars")
          .orderBy("plateCode")
          .get()
          .get();
      return querySnapshot.getDocuments()
        .stream()
        .map(CarFirebaseRepository::buildCar)
        .toList();
    } catch (Exception e) {
      log.error("Error al obtener los autos", e);
      return Collections.emptyList();
    }
  }

  public void save(Car aCar) {
    aCar.setStatus("Disponible");
    Firestore db = FirestoreClient.getFirestore();  // Save the car and get a reference to the saved document
    ApiFuture<DocumentReference> addedDocRef = db.collection("cars").add(aCar);
    DocumentReference docRef;
    try {
      docRef = addedDocRef.get();
      docRef.update("image", generateImageBlob(aCar, docRef).getName());
    } catch (InterruptedException | ExecutionException e) {
      log.error("Error al guardar el auto", e);
    }
  }

  public List<Car> findFirebaseCars(String brand, String model, String color, String type, Integer year, String subsidiary, Integer price) {
    List<Car> finalCars = new ArrayList<>();

    // Inicializar Firestore
    Firestore db = FirestoreClient.getFirestore();

    // Crear la consulta inicial
    Query query = db.collection("cars");
    query = query.whereEqualTo("status", "Disponible");

    // Agregar filtros a la consulta
    if (brand != null && !brand.isEmpty()) {
      query = query.whereEqualTo("brand", brand);
    }
    if (model != null && !model.isEmpty()) {
      query = query.whereEqualTo("model", model);
    }
    if (color != null && !color.isEmpty()) {
      query = query.whereEqualTo("color", color);
    }
    if (type != null && !type.isEmpty()) {
      query = query.whereEqualTo("type", type);
    }
    if (year != null) {
      query = query.whereEqualTo("year", year);
    }
    if (subsidiary != null && !subsidiary.isEmpty()) {
      query = query.whereEqualTo("subsidiary", subsidiary);
    }
    if (price != null) {
      query = query.whereLessThanOrEqualTo("dailyCost", price);
    }

    // Ejecutar la consulta y obtener los resultados
    ApiFuture<QuerySnapshot> querySnapshot = query.get();

    try {
      for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
        finalCars.add(buildCar(document));
      }
    } catch (InterruptedException | ExecutionException e) {
      log.error("Error al obtener los autos", e);
      return Collections.emptyList();
    }

    return finalCars;
  }

  private static Car buildCar(DocumentSnapshot document) {
    Car car = document.toObject(Car.class);
    assert car != null;
    car.setId(document.getId());
    car.setImageUrl(generateNewImageLink(car.getImage()));
    return car;
  }

  public Optional<Car> findCarById(String id) {
    Firestore db = FirestoreClient.getFirestore();
    DocumentReference docRef = db.collection("cars").document(id);
    ApiFuture<DocumentSnapshot> future = docRef.get();

    try {
      DocumentSnapshot document = future.get();
      if (document.exists()) {
        return Optional.of(buildCar(document));
      } else {
        return Optional.empty();
      }
    } catch (InterruptedException | ExecutionException e) {
      log.error("Error al obtener el auto", e);
      return Optional.empty();
    }
  }

  private static String generateNewImageLink(String blobName) {
    String newLink = "";
    if (blobName != null && !blobName.isEmpty()) {
      Bucket bucket = StorageClient.getInstance().bucket();
      Blob blob = bucket.get(blobName);
      URL signedUrl = blob.signUrl(30, TimeUnit.MINUTES);
      newLink = signedUrl.toString();
    }
    return newLink;
  }

  public void edit(String id, Car aCar, boolean newImage) {
    Firestore db = FirestoreClient.getFirestore();
    DocumentReference docRef = db.collection("cars").document(id);
    docRef.set(aCar);
    if(newImage){
      docRef.update("image", generateImageBlob(aCar, docRef).getName());
    }
  }

  private static Blob generateImageBlob(Car aCar, DocumentReference docRef) {
    byte[] imageBytes = Base64.getDecoder().decode(aCar.getImage());
    Bucket bucket = StorageClient.getInstance().bucket();
    Storage storage = bucket.getStorage();
    String blobName = "/images/cars/" + docRef.getId() + "." + aCar.getExtension();
    BlobId blobId = BlobId.of(bucket.getName(), blobName);
    BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(aCar.getMimeType()).build();
    return storage.create(blobInfo, imageBytes);
  }

  public Optional<Car> delete(String id) {
    Firestore db = FirestoreClient.getFirestore();
    DocumentReference docRef = db.collection("cars").document(id);
    ApiFuture<DocumentSnapshot> future = docRef.get();

    try {
      DocumentSnapshot document = future.get();
      if (document.exists()) {
        docRef.delete();
        return Optional.of(buildCar(document));
      } else {
        return Optional.empty();
      }
    } catch (InterruptedException | ExecutionException e) {
      log.error("Error al eliminar el auto", e);
      return Optional.empty();
    }
  }
  public void changeCarStatus(String id, String status) {
    Firestore db = FirestoreClient.getFirestore();
    DocumentReference docRef = db.collection("cars").document(id);
    docRef.update("status", status);
  }
}
