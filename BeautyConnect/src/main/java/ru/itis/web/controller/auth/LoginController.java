package ru.itis.web.controller.auth;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequestMapping("login")
public class LoginController {

    @GetMapping
    public String loginPage(Model model, Principal principal, @RequestParam(required = false) String error, @RequestParam(required = false) String blocked) {
        if (principal != null) {
            return "redirect:/home";
        }
        if (error != null) {
            model.addAttribute("errorMessage", "Неверное имя пользователя или пароль.");
        }
        if (blocked != null) {
            model.addAttribute("blockMessage", "Ваш аккаунт заблокирован.");
        }
        return "login";
    }
}
