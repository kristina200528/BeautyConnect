package ru.itis.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.itis.dto.MasterProfileDto;
import ru.itis.dto.ServiceOfferingDto;
import ru.itis.entity.BeautyService;
import ru.itis.entity.Category;
import ru.itis.entity.Master;
import ru.itis.exception.BeautyServiceNotFoundException;
import ru.itis.exception.UserNotFoundException;
import ru.itis.service.BeautyServiceService;
import ru.itis.service.MasterService;
import ru.itis.service.ServiceOfferingService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/masters/{masterId}/services/new")
@RequiredArgsConstructor
public class ServiceOfferingController {

    private final ServiceOfferingService serviceOfferingService;
    private final BeautyServiceService beautyServiceService;
    private final MasterService masterService;

    @GetMapping
    @PreAuthorize("@masterService.isMasterOwner(#principal.name, #masterId)")
    public String showCreateServiceForm(@PathVariable Long masterId, Model model, Principal principal) {

        if (!model.containsAttribute("newService")) {
            model.addAttribute("newService", new ServiceOfferingDto());
        }
        model.addAttribute("masterId", masterId);


        MasterProfileDto master = masterService.getMasterById(masterId);
        List<Category> masterCategories = master.getSpecialization();

        List<BeautyService> filteredBeautyServices = beautyServiceService.getAllByCategories(masterCategories);

        model.addAttribute("beautyServices", filteredBeautyServices);

        return "create-service";
    }

    @PostMapping
    @PreAuthorize("@masterService.isMasterOwner(#principal.name, #masterId)")
    public String addServiceForMaster(@PathVariable Long masterId,
                                      @Valid @ModelAttribute("newService") ServiceOfferingDto newService,
                                      BindingResult bindingResult,
                                      RedirectAttributes redirectAttributes, Principal principal) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.newService", bindingResult);
            redirectAttributes.addFlashAttribute("newService", newService);
            return "redirect:/masters/" + masterId + "/services/new";
        }

        try {

            BeautyService beautyService = beautyServiceService.getById(newService.getBeautyServiceId());
            serviceOfferingService.addService(masterId, newService, principal.getName());
            redirectAttributes.addFlashAttribute("successMessage", "Услуга '" + beautyService.getName() + "' успешно добавлена.");
        } catch (UserNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Произошла ошибка при добавлении услуги.");
        }

        return "redirect:/profile";
    }

}
