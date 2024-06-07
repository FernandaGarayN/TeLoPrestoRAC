package cl.duoc.newrentacar.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FirebaseConfig {
  @Bean
  public FirebaseInitializer firebaseInitializer() {
    FirebaseInitializer initializer = new FirebaseInitializer();
    initializer.initialize();
    return initializer;
  }
}
