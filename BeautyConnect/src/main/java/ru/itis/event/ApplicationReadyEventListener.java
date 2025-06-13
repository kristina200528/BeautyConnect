package ru.itis.event;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.itis.enums.UserRole;
import ru.itis.enums.UserStatus;
import ru.itis.repository.UserRepository;
import ru.itis.entity.User;


@Component
@Profile("dev")
@RequiredArgsConstructor
public class ApplicationReadyEventListener {

    private static final String ADMIN_USERNAME = "kris";
    private static final String ADMIN_EMAIL = "kristina2005m@gmail.com";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        if (userRepository.findByUsername(ADMIN_USERNAME).isEmpty()) {
            User user = new User()
                    .setUsername(ADMIN_USERNAME)
                    .setEmail(ADMIN_EMAIL)
                    .setHashPassword(passwordEncoder.encode("123"))
                    .setRole(UserRole.ADMIN)
                    .setStatus(UserStatus.ACTIVE);

            userRepository.save(user);
        }
    }

}
