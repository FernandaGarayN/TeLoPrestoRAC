package cl.duoc.newrentacar.api.repository;

import cl.duoc.newrentacar.api.endpoint.model.Car;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class CarFirebaseRepository {
  public List<Car> getAllCars() {
    Firestore db = FirestoreClient.getFirestore();
    try {
      QuerySnapshot querySnapshot = db.collection("cars").get().get();
      return querySnapshot.getDocuments().stream()
        .map(document -> {
          Car car = document.toObject(Car.class);
          car.setId(document.getId());
          return car;
        })
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

  public List<Car> findFirebaseCars(
    String brand, String model, String color, Integer year, String subsidiary, Integer price) {
    List<Car> finalCars = new ArrayList<>();

    // Inicializar Firestore
    Firestore db = FirestoreClient.getFirestore();

    // Crear la consulta inicial
    Query query = db.collection("cars");

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
        Car car = document.toObject(Car.class);
        assert car != null;
        car.setId(document.getId());
        finalCars.add(car);
      }
    } catch (InterruptedException | ExecutionException e) {
      throw new RuntimeException(e);
    }

    return finalCars;
  }

  public Optional<Car> findCarById(String id) {
    Firestore db = FirestoreClient.getFirestore();
    DocumentReference docRef = db.collection("cars").document(id);
    ApiFuture<DocumentSnapshot> future = docRef.get();

    try {
      DocumentSnapshot document = future.get();
      if (document.exists()) {
        Car car = document.toObject(Car.class);
        assert car != null;
        car.setId(document.getId());
        return Optional.of(car);
      } else {
        return Optional.empty();
      }
    } catch (InterruptedException | ExecutionException e) {
      return Optional.empty();
    }
  }

  public void edit(String id, Car aCar) {
    Firestore db = FirestoreClient.getFirestore();
    db.collection("cars").document(id).set(aCar);
  }

  public Optional<Car> delete(String id) {
    Firestore db = FirestoreClient.getFirestore();
    DocumentReference docRef = db.collection("cars").document(id);
    ApiFuture<DocumentSnapshot> future = docRef.get();

    try {
      DocumentSnapshot document = future.get();
      if (document.exists()) {
        Car car = document.toObject(Car.class);
        assert car != null;
        car.setId(document.getId());
        docRef.delete();
        return Optional.of(car);
      } else {
        return Optional.empty();
      }
    } catch (InterruptedException | ExecutionException e) {
      return Optional.empty();
    }
  }
}
