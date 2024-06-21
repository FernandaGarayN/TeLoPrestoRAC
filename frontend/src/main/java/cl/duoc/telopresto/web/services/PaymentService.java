package cl.duoc.telopresto.web.services;

import cl.duoc.telopresto.web.apiclients.payment.PaymentClient;
import cl.duoc.telopresto.web.controller.payment.PaymentForm;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class PaymentService {
    private final PaymentClient paymentClient;

    public List<Reservation> findByUsername(String username, String status) {
        return paymentClient.findByUsername(username, status);
    }

    public void savePayment(String reservationId, PaymentForm form) {
        paymentClient.savePayment(reservationId, form);
    }
}
