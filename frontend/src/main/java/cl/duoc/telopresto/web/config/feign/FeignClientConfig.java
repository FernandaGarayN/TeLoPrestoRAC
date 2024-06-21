package cl.duoc.telopresto.web.config.feign;

import cl.duoc.telopresto.web.apiclients.client.ClientErrorDecoder;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfig {
    @Bean
    public ErrorDecoder feignClientErrorDecoder() {
        return new ClientErrorDecoder();
    }
}
