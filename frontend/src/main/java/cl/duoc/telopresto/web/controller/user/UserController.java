package cl.duoc.telopresto.web.controller.user;

import cl.duoc.telopresto.web.exception.ClientAlreadyExistsException;
import cl.duoc.telopresto.web.services.Client;
import cl.duoc.telopresto.web.services.UserService;
import feign.FeignException;
import feign.RetryableException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.net.ConnectException;

@Slf4j
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

        try {
            Client client = userService.postUser(form);
            model.addAttribute("successMessage", String.format("Usuario %s con email %s registrado correctamente", client.getUsername(), client.getEmail()));
            return "login";
        } catch (Exception e) {
            log.error("Error al registrar usuario", e);
            if (e instanceof ClientAlreadyExistsException caee) {
                bindingResult.rejectValue(caee.getFieldName(), "registerForm", caee.getMessage());
                return "registro";
            } else {
                Throwable cause = e.getCause();
                if (cause instanceof RetryableException re) {
                    if (re.getCause() instanceof ConnectException ce) {
                        bindingResult.addError(new ObjectError("global", "No hay conexión con servicio de registro. Inténtelo más tarde"));
                    } else {
                        bindingResult.addError(new ObjectError("global", "Error al registrar usuario"));
                    }
                } else if (cause instanceof FeignException.NotFound nfe) {
                    bindingResult.addError(new ObjectError("global", "El servicio de registro no se ha encontrado"));
                } else if (cause instanceof FeignException.Conflict ce) {
                    bindingResult.addError(new ObjectError("global", "El usuario ya existe (username o email)"));
                } else {
                    bindingResult.addError(new ObjectError("global", "Error inesperado al registrar usuario"));
                }
            }

            return "registro";
        }
    }
}
