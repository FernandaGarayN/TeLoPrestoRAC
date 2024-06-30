package cl.duoc.telopresto.web.config.websocket;

import cl.duoc.telopresto.web.apiclients.authboot.AuthbootAuthUser;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;
import java.util.UUID;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/session-timeout").setHandshakeHandler(new DefaultHandshakeHandler() {
            @Override
            public Principal determineUser(@NotNull ServerHttpRequest request, @NotNull WebSocketHandler wsHandler, @NotNull Map<String, Object> attributes) {
                // Get the authenticated user's username
                var auth = SecurityContextHolder.getContext().getAuthentication();
                var principal = (AuthbootAuthUser)auth.getPrincipal();
                String username = principal != null ? principal.getUsername() : "anonymous-".concat(UUID.randomUUID().toString());
                return new StompPrincipal(username);
            }
        }).withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic", "/queue");
        registry.setApplicationDestinationPrefixes("/app");
    }
}

