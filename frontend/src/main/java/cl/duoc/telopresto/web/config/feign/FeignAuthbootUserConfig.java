package cl.duoc.telopresto.web.config.feign;

import cl.duoc.telopresto.web.apiclients.authboot.AuthbootErrorDecoder;
import cl.duoc.telopresto.web.apiclients.authboot.FeignAuthbootInterceptor;
import cl.duoc.telopresto.web.services.AuthbootService;
import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FeignAuthbootConfig {
  private final AuthbootService authService;
  @Bean
  public RequestInterceptor feignInterceptor() {
    return new FeignAuthbootInterceptor(authService);
  }

  @Bean
  public ErrorDecoder feignAuthbootErrorDecoder() {
    return new AuthbootErrorDecoder();
  }
}
