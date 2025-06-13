package ru.itis.web.controller.appointment;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.itis.dto.AppointmentForm;
import ru.itis.entity.*;
import ru.itis.service.AppointmentService;
import ru.itis.service.MasterService;
import ru.itis.service.ServiceOfferingService;
import ru.itis.service.UserService;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping
public class MasterAppointmentsController {
    private final MasterService masterService;
    private final ServiceOfferingService serviceOfferingService;
    private final AppointmentService appointmentService;
    private final UserService userService;

    @GetMapping("/master/appointments")
    public String getMasterAppointments(Principal principal, Model model) {
        String username = principal.getName();
        User user = userService.findByUsername(username);
        Master master = masterService.findMasterByUserId(user.getId());

        List<Appointment> appointments = appointmentService.getAppointmentsForMaster(master.getId());
        model.addAttribute("appointments", appointments);
        model.addAttribute("master", master);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAuthenticated = authentication != null && authentication.isAuthenticated();
        model.addAttribute("isAuthenticated", isAuthenticated);

        return "master/appointments";
    }

    @GetMapping("/masters/{masterId}/appointments")
    @PreAuthorize("@masterService.isNotCurrentMaster(#principal.name, #masterId)")
    public String showAppointmentForm(@PathVariable Long masterId, @RequestParam Long serviceId, Model model, Principal principal) {
        Master master = masterService.findById(masterId);
        ServiceOffering service = serviceOfferingService.findById(serviceId);

        model.addAttribute("master", master);
        model.addAttribute("service", service);
        model.addAttribute("appointmentForm", new AppointmentForm());

        return "appointments-form";
    }

    @PostMapping("/masters/{masterId}/appointments")
    @PreAuthorize("@masterService.isNotCurrentMaster(#principal.name, #masterId)")
    public String createAppointment(@PathVariable Long masterId, @Valid @ModelAttribute("appointmentForm") AppointmentForm form, BindingResult bindingResult, Model model, Principal principal, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            Master master = masterService.findById(masterId);
            ServiceOffering service = serviceOfferingService.findById(form.getServiceId());
            model.addAttribute("master", master);
            model.addAttribute("service", service);
            return "appointments-form";
        }
        String username = principal.getName();
        Long userId = userService.findByUsername(username).getId();
        appointmentService.createAppointment(masterId, form, userId);

        redirectAttributes.addFlashAttribute("successMessage", "Вы успешно записались на услугу!");
        return "redirect:/masters/" + masterId;
    }

    @PostMapping("/master/appointments/{appointmentId}/cancel")
    @PreAuthorize("@appointmentService.isMasterOwnerAppointment(#principal.name, #appointmentId)")
    public String cancelMasterAppointment(@PathVariable Long appointmentId, Principal principal, RedirectAttributes redirectAttributes, HttpServletRequest request, Model model) {
        boolean cancelled = appointmentService.cancelAppointment(appointmentId);
        CsrfToken csrf = (CsrfToken) request.getAttribute("_csrf");
        model.addAttribute("_csrf", csrf);
        if (cancelled) {
            redirectAttributes.addFlashAttribute("successMessage", "Запись успешно отменена");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Не удалось отменить запись");
        }
        return "redirect:/master/appointments";
    }


}

