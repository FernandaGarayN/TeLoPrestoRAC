package cl.duoc.newrentacar.api.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.core.io.ClassPathResource;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FirebaseInitializer {
  public void initialize() {
    try {
      InputStream serviceAccount = new ClassPathResource("serviceAccountKey.json").getInputStream();
      FirebaseOptions options = new FirebaseOptions.Builder()
        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
        .setDatabaseUrl("https://telopresto-rent-a-car.firebaseio.com")
        .build();

      FirebaseApp.initializeApp(options);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
