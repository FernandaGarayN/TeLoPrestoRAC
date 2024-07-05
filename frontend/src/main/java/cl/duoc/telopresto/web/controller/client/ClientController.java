package cl.duoc.telopresto.web.controller.client;

import cl.duoc.telopresto.web.controller.user.RegisterForm;
import cl.duoc.telopresto.web.services.Car;
import cl.duoc.telopresto.web.services.Client;
import cl.duoc.telopresto.web.services.ClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;

    @GetMapping("/gestion-clientes")
    public String getAllClients(ModelMap model) {
        model.addAttribute("clients", clientService.getAllClients());
        return "gestion-clientes";
    }

    @GetMapping("/deshabilitar-cliente/{id}")
    public String changeToDisabled(@PathVariable("id") String id, RedirectAttributes redirectAttributes, ModelMap model) {
        clientService.changeStatusToDisabled(id);
        redirectAttributes.addFlashAttribute("successMessage", "El cliente ha sido cambiado a estado Deshabilitado.");
        return "redirect:/gestion-clientes";
    }
    @GetMapping("/habilitar-cliente/{id}")
    public String changeToAvailable(@PathVariable("id") String id, RedirectAttributes redirectAttributes, ModelMap model) {
        clientService.changeStatusToEnabled(id);
        redirectAttributes.addFlashAttribute("successMessage", "El cliente ha sido cambiado a estado Habilitado.");
        return "redirect:/gestion-clientes";
    }

    @GetMapping("/eliminar-cliente/{id}")
    public String deleteCar(@PathVariable("id") String id, RedirectAttributes redirectAttributes, ModelMap model) {
        clientService.delete(id);
        redirectAttributes.addFlashAttribute("successMessage", "El cliente ha sido eliminado exitosamente.");
        return "redirect:/gestion-clientes";
    }

    @GetMapping("/editar-cliente/{id}")
    public String edit(@PathVariable String id, ModelMap model) {
        Client client = clientService.getClientById(id);
        EditClientForm form = new EditClientForm();
        form.setName(client.getName());
        form.setLastname(client.getLastname());
        form.setRut(client.getRut());
        form.setAddress(client.getAddress());
        form.setPhone(client.getPhoneNumber());
        form.setEmail(client.getEmail());
        model.addAttribute("form", form);
        model.addAttribute("id", id);
        return "editar-cliente";
    }

    @PostMapping("/editar-cliente/{id}")
    public String edit(@PathVariable String id, @ModelAttribute("form") EditClientForm form,
                       RedirectAttributes redirectAttributes,
                       BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "editar-cliente";
        }

        clientService.edit(id, form);
        redirectAttributes.addFlashAttribute("successMessage", "El cliente ha sido editado exitosamente.");
        return "redirect:/gestion-clientes";
    }
}
