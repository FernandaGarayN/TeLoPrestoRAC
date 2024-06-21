package cl.duoc.telopresto.web.apiclients.payment;

import cl.duoc.telopresto.web.config.feign.FeignPaymentConfig;
import cl.duoc.telopresto.web.controller.payment.PaymentForm;
import cl.duoc.telopresto.web.services.Payment;
import cl.duoc.telopresto.web.services.Reservation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(
        name = "payment-client",
        url = "${spring.properties.feign.payment}",
        configuration = FeignPaymentConfig.class)
public interface PaymentClient {
    @GetMapping("/by-username/{username}")
    List<Reservation> findByUsername(@PathVariable("username") String username);

    @PostMapping("/{id}/payments")
    void savePayment(@PathVariable("id") String id, @RequestBody PaymentForm form);
}
