package cl.duoc.telopresto.web.apiclients.authboot;

import cl.duoc.telopresto.web.apiclients.base.BaseResponse;
import cl.duoc.telopresto.web.config.feign.FeignAuthbootUserConfig;
import cl.duoc.telopresto.web.services.PasswordRecovery;
import cl.duoc.telopresto.web.services.Reservation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(
        name = "authboot-user-client",
        url = "${spring.properties.feign.auth}/users",
        configuration = FeignAuthbootUserConfig.class)
public interface AuthbootUserClient {

    @PostMapping("/password-recovery")
    List<Reservation> passwordRecovery (@RequestBody PasswordRecovery passwordRecovery);

    @PostMapping
    BaseResponse post(@RequestBody AuthbootNewUserRequest newUserApi);
}
