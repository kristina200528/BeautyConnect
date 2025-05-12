package ru.itis.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itis.dto.RegistrationForm;
import ru.itis.exception.UserAlreadyExistException;
import ru.itis.service.RegistrationService;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/register")
public class RegistrationController {

    private final RegistrationService service;

    @GetMapping
    public String getPage(Model model) {
        log.info("Registration page requested");
        model.addAttribute("registrationForm", new RegistrationForm());
        return "registration";
    }

    @PostMapping
    public String createUser(Model model,
            @Valid RegistrationForm registrationForm, BindingResult bindingResult) {
        if (!registrationForm.getPassword().equals(registrationForm.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword", null, "Пароли не совпадают");
        }
        if (bindingResult.hasErrors()) {
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
