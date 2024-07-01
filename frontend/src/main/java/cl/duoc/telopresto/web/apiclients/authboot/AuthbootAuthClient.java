package cl.duoc.telopresto.web.apiclients.authboot;

import cl.duoc.telopresto.web.apiclients.base.BaseResponse;
import cl.duoc.telopresto.web.config.feign.FeignAuthbootConfig;
import cl.duoc.telopresto.web.services.PasswordRecovery;
import cl.duoc.telopresto.web.services.Reservation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(
        name = "authboot-client",
        url = "${spring.properties.feign.auth}",
        configuration = FeignAuthbootConfig.class)
public interface AuthbootAuthClient {

    @PostMapping("/auth")
    AuthbootAuthResponse auth(@RequestBody AuthbootAuthRequest loginApi);

    @PostMapping("/users//password-recovery")
    List<Reservation> passwordRecovery (@RequestBody PasswordRecovery passwordRecovery);

    @PostMapping("/users")
    BaseResponse post(@RequestBody AuthbootNewUserRequest newUserApi);
}
