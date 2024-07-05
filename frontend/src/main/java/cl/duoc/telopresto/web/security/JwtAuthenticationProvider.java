package cl.duoc.telopresto.web.security;

import cl.duoc.telopresto.web.apiclients.authboot.AuthbootAuthRequest;
import cl.duoc.telopresto.web.apiclients.authboot.AuthbootAuthUser;

import java.util.Collection;

import cl.duoc.telopresto.web.services.AuthbootService;
import cl.duoc.telopresto.web.services.ClientService;
import cl.duoc.telopresto.web.services.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {
  private final AuthbootService authbootService;
  private final ReservationService reservationService;
  private final ClientService clientService;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    var response = authbootService.auth(createRequest(authentication));
    var user = response.getData();
    var username = authentication.getName();
    clientService.getByUsername(username);
    var totalGiftPoints = reservationService.getTotalGiftPoints(username);
    user.setTotalGiftPoints(totalGiftPoints);
    return new UsernamePasswordAuthenticationToken(user, user.getToken(), generateAuthorities(user));
  }

  private AuthbootAuthRequest createRequest(Authentication authentication) {
    var username = authentication.getName();
    var password = authentication.getCredentials().toString();
    return AuthbootAuthRequest.builder().username(username).password(password).build();
  }

  private Collection<? extends GrantedAuthority> generateAuthorities(AuthbootAuthUser user) {
    return user.getAuthorities().stream().map(SimpleGrantedAuthority::new).toList();
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.equals(UsernamePasswordAuthenticationToken.class);
  }
}
