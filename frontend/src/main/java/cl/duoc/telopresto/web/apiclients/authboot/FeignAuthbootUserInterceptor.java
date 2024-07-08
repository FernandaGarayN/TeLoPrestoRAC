package cl.duoc.telopresto.web.apiclients.authboot;

import cl.duoc.telopresto.web.services.AuthbootService;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@RequiredArgsConstructor
@Slf4j
public class FeignAuthbootUserInterceptor implements RequestInterceptor {
    private static final String AUTHORIZATION_HEADER="Authorization";
    private static final String TOKEN_TYPE = "Bearer";
    private final AuthbootService authbootService;

    @Override
    public void apply(RequestTemplate template) {

        String token = getTokenFromSecurityContext();

        if (token == null) {
            log.info("Usando credenciales generales");
            token = authbootService.getToken();
        }

        if (token != null) {
            log.info("Aplicando token de autenticación al request");
            template.header(AUTHORIZATION_HEADER, String.format("%s %s", TOKEN_TYPE, token));
        } else {
            log.warn("No se pudo obtener token de autenticación");
        }
    }

    /**
     * Intenta obtener el token de autenticación del contexto de seguridad.
     * @return El token de autenticación si está disponible, de lo contrario null.
     */
    private String getTokenFromSecurityContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof AuthbootAuthUser user) {
            return user.getToken();
        }
        return null;
    }
}
