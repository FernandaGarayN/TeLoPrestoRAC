package cl.duoc.telopresto.web.services;

import cl.duoc.telopresto.web.apiclients.authboot.AuthbootNewUserRequest;
import cl.duoc.telopresto.web.apiclients.authboot.AuthbootUserClient;
import cl.duoc.telopresto.web.apiclients.client.ClientClient;
import cl.duoc.telopresto.web.controller.user.RegisterForm;
import cl.duoc.telopresto.web.exception.ClientAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

@Slf4j
@RequiredArgsConstructor
public class UserService {
    public static final Set<String> DEFAULT_ROLES = Set.of("RAC_CLIENT");
    private final AuthbootUserClient authbootUserClient;
    private final ClientClient clientClient;

    public void passwordRecovery(String email) {
        authbootUserClient.passwordRecovery(PasswordRecovery.builder().email(email).build());
    }

    public Client postUser(RegisterForm form) {
        validateNewClient(form);
        String username = createUsername(form.getName(), form.getLastname());
        postNewUser(form, username);
        return postNewClient(form, username);
    }

    private Client postNewClient(RegisterForm form, String username) {
        Client build = Client.builder()
                .name(form.getName())
                .lastname(form.getLastname())
                .email(form.getEmail())
                .address(form.getAddress())
                .rut(form.getRut())
                .phoneNumber(form.getPhone())
                .username(username)
                .build();

        clientClient.postClient(build);

        return build;
    }

    private void postNewUser(RegisterForm form, String username) {
        try {
            authbootUserClient.post(
                    AuthbootNewUserRequest.builder()
                            .username(username)
                            .email(form.getEmail())
                            .password(form.getPassword())
                            .roles(DEFAULT_ROLES).build()
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void validateNewClient(RegisterForm form) {
        try {
            clientClient.getClient(form.getRut());
            throw new ClientAlreadyExistsException(form.getRut(), "rut");
        } catch (Exception e) {
            if (e instanceof ClientAlreadyExistsException caee) {
                throw caee;
            }
            log.info("Client by {} not found, creating new client", form.getRut());
        }
    }

    private String createUsername(String name, String lastname) {
        return name.toLowerCase().charAt(0) + lastname.toLowerCase();
    }
}
