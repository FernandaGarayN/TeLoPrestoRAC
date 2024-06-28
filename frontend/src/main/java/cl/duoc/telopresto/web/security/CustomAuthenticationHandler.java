package cl.duoc.telopresto.web.security;

import cl.duoc.telopresto.web.apiclients.authboot.AuthbootAuthUser;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class CustomAuthenticationHandler implements AuthenticationSuccessHandler {
    private final RequestCache requestCache;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        var user = (AuthbootAuthUser) authentication.getPrincipal();
        request.getSession().setAttribute("username", user.getUsername());
        log.info("User {} has logged in", user.getUsername());

        SavedRequest savedRequest = requestCache.getRequest(request, response);

        if (savedRequest == null) {
            response.sendRedirect(request.getContextPath() + "/");
        } else {
            response.sendRedirect(savedRequest.getRedirectUrl());
        }
    }
}
