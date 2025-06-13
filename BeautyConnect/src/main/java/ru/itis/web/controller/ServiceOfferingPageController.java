package ru.itis.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itis.dto.MasterProfileDto;
import ru.itis.dto.ServiceOfferingDto;
import ru.itis.entity.BeautyService;
import ru.itis.entity.Category;
import ru.itis.mapper.ServiceOfferingMapper;
import ru.itis.service.BeautyServiceService;
import ru.itis.service.MasterService;
import ru.itis.service.ServiceOfferingService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/masters/{masterId}/services")
@RequiredArgsConstructor
public class ServiceOfferingPageController {

    private final BeautyServiceService beautyServiceService;
    private final MasterService masterService;
    private final ServiceOfferingService serviceOfferingService;
    private final ServiceOfferingMapper serviceOfferingMapper;

    @GetMapping("/new")
    @PreAuthorize("@masterService.isMasterOwner(#principal.name, #masterId)")
    public String showNewServicePage(@PathVariable Long masterId, Model model, Principal principal) {
        MasterProfileDto master = masterService.getMasterById(masterId);
        List<Category> masterCategories = master.getSpecialization();
        List<BeautyService> filteredBeautyServices = beautyServiceService.getAllByCategories(masterCategories);

        model.addAttribute("beautyServices", filteredBeautyServices);
        model.addAttribute("masterId", masterId);
        return "create-service";
    }

    @GetMapping("/{serviceId}/edit")
    @PreAuthorize("@masterService.isMasterOwner(principal.username, #masterId)")
    public String editServiceForm(@PathVariable Long masterId, @PathVariable Long serviceId, Model model) {
        ServiceOfferingDto serviceDto = serviceOfferingMapper.mapToDto(serviceOfferingService.findById(serviceId));
        model.addAttribute("service", serviceDto);
        model.addAttribute("beautyServices", beautyServiceService.getAllBeautyService());
        model.addAttribute("masterId", masterId);
        return "edit-service";
    }
}

