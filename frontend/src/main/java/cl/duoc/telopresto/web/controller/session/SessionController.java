package cl.duoc.telopresto.web.controller.session;

import cl.duoc.telopresto.web.listener.SessionListener;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.time.Instant;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Controller
@RequiredArgsConstructor
@Slf4j
public class SessionController {
    private final SimpMessagingTemplate messagingTemplate;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    @PostConstruct
    public void startScheduler() {
        scheduler.scheduleAtFixedRate(this::checkAllSessions, 0, 30, TimeUnit.SECONDS);
    }

    public void checkAllSessions() {
        long now = Instant.now().toEpochMilli();

        for (HttpSession session : SessionListener.getSessions().values()) {
            long lastAccess = session.getLastAccessedTime();
            long inactiveInterval = session.getMaxInactiveInterval() * 1000L;
            long timeToExpire = lastAccess + inactiveInterval - now;

            if (timeToExpire < 60000L) { // Less than 1 minute
                long secondsToExpire = timeToExpire / 1000L;
                var username = (String) session.getAttribute("username");
                log.info("Session {} for {} will expire in {} seconds", session.getId(), username, secondsToExpire);// Replace with your actual username attribute
                messagingTemplate.convertAndSendToUser(username, "/queue/session-timeout", secondsToExpire);
            }
        }
    }
}
