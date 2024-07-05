package cl.duoc.telopresto.web.config.feign;

import cl.duoc.telopresto.web.apiclients.authboot.AuthbootAuthErrorDecoder;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FeignAuthbootAuthConfig {
  private final ObjectMapper objectMapper;

  @Bean
  public ErrorDecoder feignAuthbootAuthErrorDecoder() {
    return new AuthbootAuthErrorDecoder(objectMapper);
  }
}
