package ru.itis.web.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itis.entity.Master;
import ru.itis.entity.User;
import ru.itis.service.MasterService;
import ru.itis.service.UserService;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/profile")
@AllArgsConstructor
public class ProfileController {

    private final UserService userService;
    private final MasterService masterService;
    private static final String ROLE_ADMIN = "ROLE_ADMIN";


    @GetMapping
    public String profile(Model model, Principal principal) {
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = authentication.getAuthorities().stream().anyMatch(grantedAuthority -> ROLE_ADMIN.equals(grantedAuthority.getAuthority()));
        model.addAttribute("isAdmin",isAdmin);
        Optional<User> user = userService.findByUsername(principal.getName());

        if (user.isPresent()) {
            model.addAttribute("user", user.get());
            Optional<Master> master=masterService.findMasterByUserId(user.get().getId());
            master.ifPresent(value -> model.addAttribute("master", value));
        }

        return "profile";
    }

}
