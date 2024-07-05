package cl.duoc.telopresto.web.config;

import cl.duoc.telopresto.web.apiclients.authboot.AuthbootAuthClient;
import cl.duoc.telopresto.web.apiclients.authboot.AuthbootUserClient;
import cl.duoc.telopresto.web.apiclients.car.CarClient;
import cl.duoc.telopresto.web.apiclients.client.ClientClient;
import cl.duoc.telopresto.web.apiclients.payment.PaymentClient;
import cl.duoc.telopresto.web.apiclients.reservation.ReservationClient;
import cl.duoc.telopresto.web.apiclients.subsidiary.SubsidiaryClient;
import cl.duoc.telopresto.web.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;

@Configuration
@RequiredArgsConstructor
public class ServiceConfig {
    private final AuthbootUserClient authbootUserClient;
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
        return new ClientService(clientClient, authbootUserClient);
    }

    @Bean
    PaymentService paymentService() {
        return new PaymentService(paymentClient);
    }

    @Bean
    public UserService userService() {
        return new UserService(authbootUserClient, clientClient);
    }
}
