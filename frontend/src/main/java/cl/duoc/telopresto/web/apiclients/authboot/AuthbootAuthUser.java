package cl.duoc.telopresto.web.apiclients.authboot;

import java.util.Set;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
public class AuthbootAuthUser {
    private String username;
    private String email;
    private Set<String> roles;
    private Set<String> authorities;
    private String token;
    @Setter
    private Integer totalGiftPoints;
}
