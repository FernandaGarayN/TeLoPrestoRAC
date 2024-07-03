package cl.duoc.telopresto.web.config;

import cl.duoc.telopresto.web.apiclients.authboot.AuthbootAuthClient;
import cl.duoc.telopresto.web.services.AuthbootService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AuthbootConfig {
    private final AuthbootAuthClient authbootAuthClient;

    @Bean
    public AuthbootService authbootService() {
        return new AuthbootService(authbootAuthClient);
    }
}
