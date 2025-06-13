package ru.itis.web.controller.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import ru.itis.dto.RegistrationForm;
import ru.itis.entity.Category;
import ru.itis.exception.UserAlreadyExistException;
import ru.itis.service.CategoryService;
import ru.itis.service.RegistrationService;
import ru.itis.service.api.CitySearchService;

import java.security.Principal;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/register")
public class RegistrationController {

    private final RegistrationService registrationService;
    private final CategoryService categoryService;


    @GetMapping
    public String getPage(Model model) {
        CsrfToken token = (CsrfToken) ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest().getAttribute(CsrfToken.class.getName());
        model.addAttribute("_csrf", token);
        model.addAttribute("registrationForm", new RegistrationForm());
        List<Category> specializations=categoryService.getAll();
        model.addAttribute("specializations", specializations);
        return "registration";
    }

    @PostMapping
    public String createUser(Model model, @Valid @ModelAttribute("registrationForm") RegistrationForm registrationForm, BindingResult bindingResult, HttpServletRequest request) {
        CsrfToken token = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        model.addAttribute("_csrf", token);
        if (!registrationForm.getPassword().equals(registrationForm.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword", null, "Пароли не совпадают");
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("specializations", categoryService.getAll());
            model.addAttribute("bindingResult", bindingResult);
            return "registration";
        }
        try {
            registrationService.createUser(registrationForm);
        } catch (UserAlreadyExistException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "registration";
        }

        return "redirect:/login";
    }


}
