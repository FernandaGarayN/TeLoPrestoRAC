package cl.duoc.telopresto.web.config;

import cl.duoc.telopresto.web.apiclients.authboot.AuthbootClient;
import cl.duoc.telopresto.web.apiclients.car.CarClient;
import cl.duoc.telopresto.web.apiclients.client.ClientClient;
import cl.duoc.telopresto.web.apiclients.payment.PaymentClient;
import cl.duoc.telopresto.web.apiclients.reservation.ReservationClient;
import cl.duoc.telopresto.web.apiclients.subsidiary.SubsidiaryClient;
import cl.duoc.telopresto.web.apiclients.user.UserClient;
import cl.duoc.telopresto.web.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;

@Configuration
@RequiredArgsConstructor
public class ServiceConfig {
    private final AuthbootClient authbootClient;
    private final UserClient userClient;
    private final CarClient carClient;
    private final SubsidiaryClient subsidiaryClient;
    private final ReservationClient reservationClient;
    private final PaymentClient paymentClient;
    private final ClientClient clientClient;

    @Bean
    public RequestCache requestCache() {
        return new HttpSessionRequestCache();
    }

    @Bean
    public AuthbootService authbootService() {
        return new AuthbootService(authbootClient);
    }

    @Bean
    CarService carService() {
        return new CarService(carClient);
    }

    @Bean
    SubsidiaryService subsidiaryService() {
        return new SubsidiaryService(subsidiaryClient);
    }

    @Bean
    ReservationService reservationService() {
        return new ReservationService(reservationClient);
    }

    @Bean
    public ClientService clientService() {
        return new ClientService(clientClient);
    }

    @Bean
    PaymentService paymentService() {
        return new PaymentService(paymentClient);
    }

    @Bean
    public UserService userService() {
        return new UserService(userClient);
    }
}
