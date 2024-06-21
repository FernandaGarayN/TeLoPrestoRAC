package cl.duoc.telopresto.web.services;

import cl.duoc.telopresto.web.apiclients.payment.PaymentClient;
import cl.duoc.telopresto.web.controller.payment.PaymentForm;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class PaymentService {
    private final PaymentClient paymentClient;

    public List<Payment> findByUsername(String username) {
        List<Reservation> byUsername = paymentClient.findByUsername(username);
        return byUsername
                .stream()
                .filter(r -> r.getStatus().equalsIgnoreCase("paid"))
                .map(r -> {
                    r.getPayments().forEach(p -> p.setReservation(r.getName()));
                    return r.getPayments();
                        }
                )
                .flatMap(List::stream)
                .toList();
    }

    public void savePayment(String reservationId, PaymentForm form) {
        paymentClient.savePayment(reservationId, form);
    }
}
