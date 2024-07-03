package cl.duoc.telopresto.web.config.feign;

import cl.duoc.telopresto.web.apiclients.authboot.AuthbootUserErrorDecoder;
import cl.duoc.telopresto.web.apiclients.authboot.FeignAuthbootUserInterceptor;
import cl.duoc.telopresto.web.services.AuthbootService;
import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FeignAuthbootUserConfig {
  private final AuthbootService authService;
  @Bean
  public RequestInterceptor feignInterceptor() {
    return new FeignAuthbootUserInterceptor(authService);
  }

  @Bean
  public ErrorDecoder feignAuthbootUserErrorDecoder() {
    return new AuthbootUserErrorDecoder();
  }
}
