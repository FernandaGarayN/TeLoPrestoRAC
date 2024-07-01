package cl.duoc.telopresto.web.controller.user;

import cl.duoc.telopresto.web.services.Client;
import cl.duoc.telopresto.web.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/recuperar-contrasenia")
    public String getPasswordRecovery(ModelMap model) {
        model.addAttribute("passwordRecoveryForm", PasswordRecoveryForm.builder().build());
        return "recuperar-contrasenia";
    }
    @PostMapping("/recuperar-contrasenia")
    public String postPasswordRecovery(
            ModelMap model,
            @Valid @ModelAttribute PasswordRecoveryForm form,
            BindingResult bindingResult) {
        String email = form.getEmail();
        userService.passwordRecovery(email);
        model.addAttribute("passwordRecoveryForm", form);
        return "recuperar-contrasenia";
    }

    @GetMapping("/registro")
    public String getRegister(ModelMap model) {
        model.addAttribute("registerForm", new RegisterForm());
        return "registro";
    }

    @PostMapping("/registro")
    public String postRegister(
            ModelMap model,
            @Valid @ModelAttribute RegisterForm form,
            BindingResult bindingResult) {

        if (!form.getPassword().equals(form.getPasswordConfirmation())) {
            bindingResult.rejectValue("password", "password", "Las contraseñas no coinciden");
        }
        if (bindingResult.hasErrors()) {
            return "registro";
        }

        Client client = userService.postUser(form);
        model.addAttribute("client", client);
        return "registro-exitoso";
    }
}
