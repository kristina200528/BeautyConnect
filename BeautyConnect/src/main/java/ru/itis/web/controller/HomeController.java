package ru.itis.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/home")
public class HomeController {
    private static final String ROLE_ADMIN = "ROLE_ADMIN";

    @GetMapping
    public String home(Model model, Principal principal) {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = authentication.getAuthorities().stream().anyMatch(grantedAuthority -> ROLE_ADMIN.equals(grantedAuthority.getAuthority()));
        model.addAttribute("isAdmin",isAdmin);
        model.addAttribute("userName", principal.getName());
        return "home";
    }


}
