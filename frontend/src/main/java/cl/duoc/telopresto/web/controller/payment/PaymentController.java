package cl.duoc.telopresto.web.controller.payment;

import cl.duoc.telopresto.web.services.CarService;
import cl.duoc.telopresto.web.services.PaymentService;
import cl.duoc.telopresto.web.services.Reservation;
import cl.duoc.telopresto.web.services.ReservationService;
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
        String username = (String) authentication.getPrincipal();
        model.addAttribute("results", paymentService.findByUsername(username));
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
        PaymentForm paymentForm = PaymentForm.builder().build();
        reservationService.findById(reservationId).ifPresent(reservation -> {
            paymentForm.setAmount(
                    reservation.getAmount() - Optional.ofNullable(reservation.getPayments())
                            .map(payments -> payments
                                    .stream()
                                    .mapToDouble(p -> Double.parseDouble(p.getAmount()))
                                    .sum()
                            )
                            .orElse(0.0)
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
        model.addAttribute("paymentForm", form);
        model.addAttribute("reservationId", reservationId);
        if (bindingResult.hasErrors()) {
            return "ingresar-pago";
        }
        paymentService.savePayment(reservationId, form);
        redirectAttributes.addFlashAttribute("successMessage", "El pago ha sido ingresado exitosamente.");
        return "redirect:/reservas-clientes";
    }
}
