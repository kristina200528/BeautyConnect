package ru.itis.web.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itis.dto.MasterProfileDto;
import ru.itis.dto.ServiceOfferingDto;
import ru.itis.entity.BeautyService;
import ru.itis.entity.Master;
import ru.itis.entity.User;
import ru.itis.enums.UserRole;
import ru.itis.security.details.UserDetailsImpl;
import ru.itis.service.BeautyServiceService;
import ru.itis.service.MasterService;
import ru.itis.service.ServiceOfferingService;
import ru.itis.service.UserService;

import java.security.Principal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@Controller
@RequestMapping("/masters")
public class MasterCatalogController {

    private final MasterService masterService;
    private final ServiceOfferingService serviceOfferingService;
    private final BeautyServiceService beautyServiceService;
    private final UserService userService;


    @GetMapping
    public String getAllMasters(Model model, Principal principal) {
        String username=principal.getName();
        User user=userService.findByUsername(username);
        List<Master> masters = masterService.getAllMasters();

        if (user.getRole()== UserRole.MASTER){
            Long currentMasterId=masterService.findMasterByUserId(user.getId()).getId();
            if (currentMasterId != null) {
                masters = masters.stream()
                        .filter(master -> !master.getUser().getId().equals(currentMasterId))
                        .collect(Collectors.toList());
            }
        }

        model.addAttribute("masters", masters);

        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        boolean isAuthenticated = authentication != null && authentication.isAuthenticated();
        model.addAttribute("isAuthenticated",isAuthenticated);

        return "master/catalog";
    }

    @GetMapping("{id}")
    @PreAuthorize("@masterService.isNotCurrentMaster(#principal.name, #id)")
    public String getMasterProfile(@PathVariable("id") Long id, Model model, Principal principal) {
        MasterProfileDto master = masterService.getMasterById(id);
        model.addAttribute("master", master);

        List<ServiceOfferingDto> services = serviceOfferingService.getServicesByMasterId(master.getId());

        Map<String, BeautyService> beautyServiceMap = new HashMap<>();
        for (ServiceOfferingDto service : services) {
            BeautyService beautyService = beautyServiceService.getById(service.getBeautyServiceId());
            beautyServiceMap.put(service.getId().toString(), beautyService);
        }
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        boolean isAuthenticated = authentication != null && authentication.isAuthenticated();
        model.addAttribute("isAuthenticated",isAuthenticated);

        model.addAttribute("services", services);
        model.addAttribute("beautyServices", beautyServiceMap);
        model.addAttribute("masterId", master.getId());

        return "master/profile";
    }

}
