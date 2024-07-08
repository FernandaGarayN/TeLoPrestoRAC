package cl.duoc.telopresto.web.services;

import cl.duoc.telopresto.web.apiclients.authboot.AuthbootAuthClient;
import cl.duoc.telopresto.web.apiclients.authboot.AuthbootAuthRequest;
import cl.duoc.telopresto.web.apiclients.authboot.AuthbootAuthResponse;
import cl.duoc.telopresto.web.apiclients.authboot.AuthbootAuthUser;
import feign.RetryableException;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;

import java.util.Set;
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
        scheduler.scheduleAtFixedRate(this::updateToken, 0, 1, TimeUnit.MINUTES);
    }

    public AuthbootAuthResponse auth(AuthbootAuthRequest request) {
        try {
            AuthbootAuthResponse auth = loginApiClient.auth(request);
            if (auth == null) {
                throw new AuthenticationServiceException("No se pudo autenticar");
            }
            AuthbootAuthUser authUser = auth.getData();
            if (authUser == null) {
                throw new AuthenticationServiceException("No se pudo autenticar");
            }
            if (authUser.getToken() == null) {
                throw new AuthenticationServiceException("No se pudo autenticar");
            }

            Set<String> roles = authUser.getRoles();
            if (roles == null) {
                throw new AuthenticationServiceException("No se pudo autenticar");
            }

            if (roles.isEmpty()) {
                throw new AuthenticationServiceException("No se pudo autenticar");
            }

            if (roles.stream().noneMatch(role -> role.startsWith("RAC_"))) {
                throw new AuthenticationServiceException("Usuario no autorizado");
            }

            Set<String> authorities = authUser.getAuthorities();
            if (authorities == null) {
                throw new AuthenticationServiceException("No se pudo autenticar");
            }

            if (authorities.isEmpty()) {
                throw new AuthenticationServiceException("No se pudo autenticar");
            }

            return auth;
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
        AuthbootAuthResponse response = null;
        try {
            response = auth(request);
        } catch (Exception e) {
           log.error("No se pudo actualizar el token de autenticación", e);
        }
        log.debug("Response: {}", response);
        token = response.getData().getToken();
        log.info("Token actualizado con éxito");
    }
}
