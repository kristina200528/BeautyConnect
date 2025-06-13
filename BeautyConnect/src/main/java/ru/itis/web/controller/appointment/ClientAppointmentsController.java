package ru.itis.web.controller.appointment;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.itis.dto.AppointmentDto;
import ru.itis.entity.Appointment;
import ru.itis.entity.User;
import ru.itis.enums.AppointmentStatus;
import ru.itis.service.AppointmentService;
import ru.itis.service.UserService;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ClientAppointmentsController {
    private final UserService userService;
    private final AppointmentService appointmentService;

    @GetMapping("/appointments/my")
    public String getClientAppointments(Principal principal, Model model) {
        String username = principal.getName();
        User user = userService.findByUsername(username);
        Long userId = user.getId();
        List<AppointmentDto> appointments = appointmentService.getAppointmentsForClient(userId);
        model.addAttribute("appointments", appointments);
        return "client/appointments";
    }

    @PostMapping("/appointments/{appointmentId}/cancel")
    @PreAuthorize("@appointmentService.isUserOwnerAppointment(#principal.name, #appointmentId)")
    public String cancelAppointment(@PathVariable Long appointmentId, Principal principal, RedirectAttributes redirectAttributes, HttpServletRequest request, Model model) {
        boolean cancelled = appointmentService.cancelAppointment(appointmentId);
        CsrfToken csrf = (CsrfToken) request.getAttribute("_csrf");
        model.addAttribute("_csrf", csrf);
        if (cancelled) {
            redirectAttributes.addFlashAttribute("successMessage", "Запись успешно отменена");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Не удалось отменить запись");
        }
        return "redirect:/appointments/my";
    }

    @GetMapping("/appointments/{appointmentId}/edit")
    @PreAuthorize("@appointmentService.isUserOwnerAppointment(#principal.name, #appointmentId)")
    public String showEditForm(@PathVariable Long appointmentId, Model model, Principal principal) {
        Appointment appointment = appointmentService.findById(appointmentId);
        model.addAttribute("appointment", appointment);
        return "client/appointment-edit-form";
    }

    @PostMapping("/appointments/{appointmentId}/edit")
    @PreAuthorize("@appointmentService.isUserOwnerAppointment(#principal.name, #appointmentId)")
    public String updateAppointmentTime(@PathVariable Long appointmentId,
                                        @RequestParam("dateTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTime,
                                        Principal principal,
                                        RedirectAttributes redirectAttributes) {
        Appointment appointment = appointmentService.findById(appointmentId);
        appointment.setDateTime(dateTime);
        appointment.setStatus(AppointmentStatus.CREATED);
        appointmentService.save(appointment);
        redirectAttributes.addFlashAttribute("successMessage", "Дата и время записи успешно обновлены");
        return "redirect:/appointments/my";
    }


}
