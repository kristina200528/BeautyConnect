package ru.itis.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.itis.enums.UserStatus;
import ru.itis.entity.User;
import ru.itis.exception.UserNotFoundException;
import ru.itis.repository.UserRepository;

import java.io.IOException;
import java.util.Optional;

@Component
@AllArgsConstructor
public class BlockedUserFilter extends OncePerRequestFilter {
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {
            String username=auth.getName();
            User user = userRepository.findByUsername(username).orElseThrow(()-> new UserNotFoundException("User not found"));
            if (user.getStatus()== UserStatus.BLOCKED){
                new SecurityContextLogoutHandler().logout(request, response, auth);
                response.sendRedirect("/login?blocked");
                return;
            }
        }
        filterChain.doFilter(request,response);
    }
}
