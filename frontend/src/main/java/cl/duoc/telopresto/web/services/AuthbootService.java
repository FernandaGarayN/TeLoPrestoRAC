package cl.duoc.telopresto.web.services;

import cl.duoc.telopresto.web.apiclients.authboot.AuthbootAuthClient;
import cl.duoc.telopresto.web.apiclients.authboot.AuthbootAuthRequest;
import cl.duoc.telopresto.web.apiclients.authboot.AuthbootAuthResponse;
import feign.RetryableException;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Slf4j
public class AuthbootService {
    private final AuthbootAuthClient loginApiClient;
    private final ScheduledExecutorService scheduler =
            Executors.newScheduledThreadPool(1);
    @Getter
    private String token;

    @Value("${spring.properties.authboot-credentials}")
    private String authbootCredentials;

    @PostConstruct
    public void init() {
        scheduler.scheduleAtFixedRate(this::updateToken, 0, 28, TimeUnit.MINUTES);
    }

    public AuthbootAuthResponse auth(AuthbootAuthRequest request) {
        try {
            return loginApiClient.auth(request);
        } catch (AuthenticationException e) {
            throw e;
        } catch (RetryableException e) {
            throw new AuthenticationServiceException("Servicio de autenticación no disponible", e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void updateToken() {
        log.info("Actualizando token de autenticación");
        String[] split = authbootCredentials.split(":");
        log.debug("Credenciales: {}", split[0]);
        AuthbootAuthRequest request = AuthbootAuthRequest.builder()
                .username(split[0])
                .password(split[1])
                .build();
        log.debug("Request: {}", request);
        AuthbootAuthResponse response = auth(request);
        log.debug("Response: {}", response);
        token = response.getData().getToken();
    }
}
