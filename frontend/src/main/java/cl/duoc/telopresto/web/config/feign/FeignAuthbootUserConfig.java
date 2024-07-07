package cl.duoc.telopresto.web.config.feign;

import cl.duoc.telopresto.web.apiclients.authboot.AuthbootUserErrorDecoder;
import cl.duoc.telopresto.web.apiclients.authboot.FeignAuthbootUserInterceptor;

import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FeignAuthbootUserConfig {
  @Bean
  public FeignAuthbootUserInterceptor feignInterceptor() {
    return new FeignAuthbootUserInterceptor();
  }

  @Bean
  public ErrorDecoder feignAuthbootUserErrorDecoder() {
    return new AuthbootUserErrorDecoder();
  }
}
