package cl.duoc.telopresto.web.services;

import cl.duoc.telopresto.web.apiclients.authboot.AuthbootAuthClient;
import cl.duoc.telopresto.web.apiclients.authboot.AuthbootAuthRequest;
import cl.duoc.telopresto.web.apiclients.authboot.AuthbootAuthResponse;
import cl.duoc.telopresto.web.apiclients.authboot.AuthbootAuthUser;
import feign.RetryableException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;

import java.util.Set;

@RequiredArgsConstructor
@Slf4j
public class AuthbootService {
    private final AuthbootAuthClient loginApiClient;

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
}
