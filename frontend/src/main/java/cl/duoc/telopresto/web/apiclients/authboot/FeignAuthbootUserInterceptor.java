package cl.duoc.telopresto.web.apiclients.authboot;

import cl.duoc.telopresto.web.services.AuthbootService;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class FeignAuthbootInterceptor implements RequestInterceptor {
    private static final String AUTHORIZATION_HEADER="Authorization";
    private static final String TOKEN_TYPE = "Bearer";
    private final AuthbootService authbootService;

    @Override
    public void apply(RequestTemplate template) {
        String token = authbootService.getToken();
        if (token != null) {
            log.info("Aplicando token de autenticación al request");
            template.header(AUTHORIZATION_HEADER, String.format("%s %s", TOKEN_TYPE, token));
        } else {
            log.warn("No se pudo obtener token de autenticación");
        }
    }
}
