package ru.itis.web.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itis.entity.BeautyService;
import ru.itis.entity.Master;
import ru.itis.entity.User;
import ru.itis.enums.UserRole;
import ru.itis.exception.MasterNotFoundException;
import ru.itis.exception.UserNotFoundException;
import ru.itis.service.AppointmentService;
import ru.itis.service.MasterService;
import ru.itis.service.UserService;

import javax.swing.text.html.Option;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/home")
@AllArgsConstructor
public class HomeController {
    private final UserService userService;
    private final AppointmentService appointmentService;

    @GetMapping
    public String home(Model model, Principal principal) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAuthenticated = authentication != null && !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
        if (isAuthenticated) {
            String userName = principal.getName();
            User user = userService.findByUsername(userName);
            model.addAttribute("user", user);
        }
        List<BeautyService> popularServices = appointmentService.getMostPopularServices();
        model.addAttribute("popularServices", popularServices);
        model.addAttribute("isAuthenticated", isAuthenticated);
        return "home";
    }


}
