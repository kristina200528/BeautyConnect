package ru.itis.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itis.dto.RegistrationForm;
import ru.itis.exception.UserAlreadyExistException;
import ru.itis.service.RegistrationService;

import java.security.Principal;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/register")
public class RegistrationController {

    private final RegistrationService service;

    @GetMapping
    public String getPage(Model model, Principal principal) {
        if (principal != null) {
            return "redirect:/home";
        }
        model.addAttribute("registrationForm", new RegistrationForm());
        return "registration";
    }

    @PostMapping
    public String createUser(Model model,
                             @Valid @ModelAttribute("registrationForm") RegistrationForm registrationForm, BindingResult bindingResult) {
        if (!registrationForm.getPassword().equals(registrationForm.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword", null, "Пароли не совпадают");
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("bindingResult", bindingResult);
            return "registration";
        }
        try {
            service.createUser(registrationForm);
        } catch (UserAlreadyExistException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "registration";
        }

        return "redirect:/login";
    }


}
