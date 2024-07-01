package cl.duoc.telopresto.web.apiclients.authboot;

import cl.duoc.telopresto.web.config.feign.FeignAuthbootAuthConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "authboot-auth-client",
        url = "${spring.properties.feign.auth}/auth",
        configuration = FeignAuthbootAuthConfig.class)
public interface AuthbootAuthClient {

    @PostMapping
    AuthbootAuthResponse auth(@RequestBody AuthbootAuthRequest loginApi);
}
