package cl.duoc.telopresto.web.controller.reservation;

import cl.duoc.telopresto.web.apiclients.authboot.AuthbootAuthUser;
import cl.duoc.telopresto.web.services.*;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;
    private final CarService carService;
    private final ClientService clientService;
    private List<Map<String, String>> listOfBrands;

    @PostConstruct
    public void init() {
        listOfBrands = carService.getListOfBrands();
    }

    @GetMapping("/mis-reservas")
    public String getReservations(
            ModelMap model,
            @RequestParam(value = "highlight", required = false) String highlight,
            Authentication authentication) {
        AuthbootAuthUser user = (AuthbootAuthUser) authentication.getPrincipal();
        List<Reservation> reservations = reservationService.findByUsername(user.getUsername());
        reservations.forEach(
                reservation -> listOfBrands.stream()
                        .filter(brand -> brand.get("id").equals(reservation.getBrand()))
                        .findFirst()
                        .ifPresent(
                                brand -> reservation.setBrand(brand.get("name"))
                        ));
        model.addAttribute("results", reservations);
        model.addAttribute("highlight", highlight);
        return "mis-reservas";
    }

    @GetMapping("/reservas-clientes")
    public String getReservationsClient(ModelMap model) {
        model.addAttribute("reservationSearchForm", ReservationSearchForm.builder().build());
        return "reservas-clientes";
    }

    @PostMapping("/reservas-clientes")
    public String postReservationsClient(
            ModelMap model,
            @Valid @ModelAttribute ReservationSearchForm form,
            BindingResult bindingResult) {
        String rut = form.getRut();
        String name = form.getName();
        String lastName = form.getLastname();
        List <Client> clients = clientService.getByRutOrName(rut,name,lastName);
        if (clients == null  || clients.isEmpty()) {
            ObjectError error = new ObjectError("global", "No se encontraron clientes para la búsqueda");
            bindingResult.addError(error);
        } else {
            Map<String, List<Reservation>> reservationMap = new HashMap<>();

            clients.forEach(client -> {
                List<Reservation> reservations = reservationService.findByUsername(client.getUsername());
                reservations.forEach(Reservation::calculateTotal);
                reservations.forEach(
                        reservation -> listOfBrands.stream()
                                .filter(brand -> brand.get("id").equals(reservation.getBrand()))
                                .findFirst()
                                .ifPresent(
                                        brand -> reservation.setBrand(brand.get("name"))
                                ));
                reservationMap.put(client.getUsername(), reservations);
            });

            model.addAttribute("reservationMap", reservationMap);
        }
        model.addAttribute("reservationSearchForm", form);
        return "reservas-clientes";
    }

    @GetMapping("/reservas-clientes/{id}/cancelar")
    public String cancelReservationClient(
            @PathVariable("id") String id,
            RedirectAttributes redirectAttributes) {
        reservationService.cancel(id);
        redirectAttributes.addFlashAttribute("successMessage", "Reserva cancelada correctamente");
        return "redirect:/reservas-clientes?highlight=".concat(id);
    }

    @GetMapping("/nueva-reserva")
    public String getNewReservation(ModelMap model, @RequestParam("idVehiculo") String id) {
        Car car = carService.findById(id);
        List<Reservation> reservations = reservationService.findByCarId(id);

        listOfBrands.stream()
                .filter(brand -> brand.get("id").equals(car.getBrand()))
                .findFirst()
                .ifPresent(
                        brand -> car.setBrand(brand.get("name"))
                );

        ReservationForm reservationForm =
                ReservationForm.builder()
                        .carId(car.getId())
                        .car(car.getBrand().concat(" - ").concat(car.getModel()))
                        .build();
        model.addAttribute("reservationForm", reservationForm);
        model.addAttribute("reservations", reservations);
        model.addAttribute("id", id);
        return "nueva-reserva";
    }

    @PostMapping("/nueva-reserva")
    public String postNewReservation(
            ModelMap model,
            @RequestParam("idVehiculo") String id,
            @Valid @ModelAttribute("reservationForm") ReservationForm form,
            BindingResult bindingResult,
            Authentication authentication) {
        model.addAttribute("reservationForm", form);
        if (bindingResult.hasErrors()) {
            List<Reservation> reservations = reservationService.findByCarId(id);
            model.addAttribute("reservations", reservations);
            model.addAttribute("id", id);
            return "nueva-reserva";
        }
        AuthbootAuthUser user = (AuthbootAuthUser) authentication.getPrincipal();
        Reservation reservation = reservationService.save(form, user.getUsername());
        return String.format("redirect:/mis-reservas?highlight=%s", reservation.getId());
    }

    @GetMapping("/mis-reservas/{id}/editar")
    public String getEditReservation(ModelMap model, @PathVariable("id") String id) {
        Optional<Reservation> reservation = reservationService.findById(id);
        if (reservation.isEmpty()) {
            return "redirect:/mis-reservas";
        }

        reservation.ifPresent(r -> {
            listOfBrands.stream()
                    .filter(brand -> brand.get("id").equals(r.getBrand()))
                    .findFirst()
                    .ifPresent(
                            brand -> r.setBrand(brand.get("name"))
                    );

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            ReservationForm reservationForm = ReservationForm.builder()
                    .id(r.getId())
                    .carId(r.getCarId())
                    .car(r.getBrand().concat(" - ").concat(r.getModel()))
                    .startAt(r.getStartAt().format(formatter))
                    .endAt(r.getEndAt().format(formatter))
                    .build();

            model.addAttribute("reservationForm", reservationForm);
        });
        model.addAttribute("reservationId", id);
        return "editar-reserva";
    }

    @PostMapping("/mis-reservas/{id}/editar")
    public String postEditReservation(
            ModelMap model,
            @Valid @ModelAttribute("reservationForm") ReservationForm form,
            BindingResult bindingResult,
            @PathVariable("id") String id,
            RedirectAttributes redirectAttributes) {
        model.addAttribute("reservationForm", form);
        model.addAttribute("reservationId", id);
        if (bindingResult.hasErrors()) {
            return "editar-reserva";
        }
        Reservation reservation = reservationService.update(form, id);
        redirectAttributes.addFlashAttribute("successMessage", "Reserva actualizada correctamente");
        return String.format("redirect:/mis-reservas?highlight=%s", reservation.getId());
    }

    @GetMapping("/mis-reservas/{id}/confirmar")
    public String confirmReservation(
            @PathVariable("id") String id,
            RedirectAttributes redirectAttributes) {
        reservationService.confirm(id);
        redirectAttributes.addFlashAttribute("successMessage", "Reserva confirmada correctamente");
        return "redirect:/mis-reservas?highlight=".concat(id);
    }

    @GetMapping("/mis-reservas/{id}/cancelar")
    public String cancelReservation(
            @PathVariable("id") String id,
            RedirectAttributes redirectAttributes) {
        reservationService.cancel(id);
        redirectAttributes.addFlashAttribute("successMessage", "Reserva cancelada correctamente");
        return "redirect:/mis-reservas?highlight=".concat(id);
    }

    @GetMapping("/mis-reservas/{id}/comentar")
    public String getCommentReservation(ModelMap model, @PathVariable("id") String id) {
        Optional<Reservation> reservation = reservationService.findById(id);
        if (reservation.isEmpty()) {
            return "redirect:/mis-reservas";
        }

        reservation.ifPresent(r -> {
            listOfBrands.stream()
                    .filter(brand -> brand.get("id").equals(r.getBrand()))
                    .findFirst()
                    .ifPresent(
                            brand -> r.setBrand(brand.get("name"))
                    );
            model.addAttribute("reservation", r);
        });
        model.addAttribute("commentForm", new CommentForm());
        model.addAttribute("reservationId", id);
        return "nuevo-comentario";
    }

    @PostMapping("/mis-reservas/{id}/comentar")
    public String postCommentReservation(
            ModelMap model,
            @Valid @ModelAttribute("commentForm") CommentForm form,
            BindingResult bindingResult,
            @PathVariable("id") String id,
            RedirectAttributes redirectAttributes) {
        model.addAttribute("commentForm",form);
        model.addAttribute("reservationId", id);
        if (bindingResult.hasErrors()) {
            Optional<Reservation> reservation = reservationService.findById(id);
            reservation.ifPresent(r -> {
                listOfBrands.stream()
                        .filter(brand -> brand.get("id").equals(r.getBrand()))
                        .findFirst()
                        .ifPresent(
                                brand -> r.setBrand(brand.get("name"))
                        );
                model.addAttribute("reservation", r);
            });
            return "nuevo-comentario";
        }
        reservationService.comment(id, form);
        redirectAttributes.addFlashAttribute("successMessage", "Comentario agregado correctamente");
        return String.format("redirect:/mis-reservas?highlight=%s", id);
    }

}
