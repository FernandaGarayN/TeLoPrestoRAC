package cl.duoc.telopresto.web.controller.payment;

import cl.duoc.telopresto.web.apiclients.authboot.AuthbootAuthUser;
import cl.duoc.telopresto.web.services.*;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    private final ReservationService reservationService;
    private final CarService carService;
    private List<Map<String, String>> listOfBrands;

    @PostConstruct
    public void init() {
        listOfBrands = carService.getListOfBrands();
    }

    @GetMapping("/pagos")
    public String getPayments(ModelMap model, Authentication authentication) {
        AuthbootAuthUser user = (AuthbootAuthUser) authentication.getPrincipal();
        String username = user.getUsername();
        List<Reservation> paid = paymentService.findByUsername(username, "paid");
        List<Reservation> released = paymentService.findByUsername(username, "released");
        List<Reservation> cancelled = paymentService.findByUsername(username, "cancelled");

        List<Reservation> byUsername = new ArrayList<>();
        byUsername.addAll(paid);
        byUsername.addAll(released);
        byUsername.addAll(cancelled);

        byUsername.forEach(Reservation::calculateTotal);

        byUsername.forEach(
                reservation -> listOfBrands.stream()
                        .filter(brand -> brand.get("id").equals(reservation.getBrand()))
                        .findFirst()
                        .ifPresent(
                                brand -> reservation.setBrand(brand.get("name"))
                        ));

        List<Payment> payments = byUsername
                .stream()
                .map(reservation -> {
                    List<Payment> list = reservation.getPayments();
                    list.forEach(payment -> payment.setReservation(reservation.getName()));
                    list.forEach(payment -> payment.setStatus(reservation.getStatus()));
                    return list;
                })
                .flatMap(List::stream)
                .toList();

        model.addAttribute("results", payments);
        List<Reservation> byUsernameAndPending = reservationService.findByUsernameAndPending(username);
        byUsernameAndPending.forEach(Reservation::calculateTotal);
        byUsernameAndPending.forEach(
                reservation -> listOfBrands.stream()
                        .filter(brand -> brand.get("id").equals(reservation.getBrand()))
                        .findFirst()
                        .ifPresent(
                                brand -> reservation.setBrand(brand.get("name"))
                        ));
        model.addAttribute("reservations", byUsernameAndPending);
        return "pagos";
    }

    @GetMapping("/ingresar-pago/{reservationId}")
    public String getPaymentPage(@PathVariable String reservationId, ModelMap model) {
        PaymentForm paymentForm = new PaymentForm();
        reservationService.findById(reservationId)
                .ifPresent(reservation -> {
                    paymentForm.setAmount(
                            reservation.getAmount() -
                                    Optional.ofNullable(reservation.getPayments())
                                            .map(payments -> payments
                                                    .stream()
                                                    .mapToDouble(p -> Double.parseDouble(p.getAmount()))
                                                    .sum()
                                            )
                                            .orElse(0.0)
                    );

                    listOfBrands.stream()
                            .filter(brand -> brand.get("id").equals(reservation.getBrand()))
                            .findFirst()
                            .ifPresent(
                                    brand -> reservation.setBrand(brand.get("name"))
                            );
                    model.addAttribute("reservation", reservation);
                });
        model.addAttribute("paymentForm", paymentForm);
        model.addAttribute("reservationId", reservationId);
        return "ingresar-pago";
    }

    @PostMapping("/ingresar-pago/{reservationId}")
    public String postPaymentPage(@PathVariable String reservationId,
                                  ModelMap model,
                                  @Valid @ModelAttribute("paymentForm") PaymentForm form,
                                  BindingResult bindingResult
            , RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            reservationService.findById(reservationId).ifPresent(reservation -> {
                listOfBrands.stream()
                        .filter(brand -> brand.get("id").equals(reservation.getBrand()))
                        .findFirst()
                        .ifPresent(
                                brand -> reservation.setBrand(brand.get("name"))
                        );
                model.addAttribute("reservation", reservation);
            });
            model.addAttribute("paymentForm", form);
            model.addAttribute("reservationId", reservationId);
            return "ingresar-pago";
        }
        paymentService.savePayment(reservationId, form);
        redirectAttributes.addFlashAttribute("successMessage", "El pago ha sido ingresado exitosamente.");
        return "redirect:/reservas-clientes";
    }
}
