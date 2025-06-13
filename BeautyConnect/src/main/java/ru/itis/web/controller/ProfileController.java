package ru.itis.web.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itis.dto.ServiceOfferingDto;
import ru.itis.entity.BeautyService;
import ru.itis.entity.Master;
import ru.itis.entity.User;
import ru.itis.enums.UserRole;
import ru.itis.service.BeautyServiceService;
import ru.itis.service.MasterService;
import ru.itis.service.ServiceOfferingService;
import ru.itis.service.UserService;

import java.security.Principal;
import java.util.*;

@Controller
@RequestMapping("/profile")
@AllArgsConstructor
public class ProfileController {

    private final UserService userService;
    private final MasterService masterService;
    private final ServiceOfferingService serviceOfferingService;
    private final BeautyServiceService beautyServiceService;


    @GetMapping
    public String profile(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        if (user.getRole()== UserRole.MASTER) {
            Master master = masterService.findMasterByUserId(user.getId());
            model.addAttribute("master", master);
            List<ServiceOfferingDto> services = serviceOfferingService.getServicesByMasterId(master.getId());

            Map<String, BeautyService> beautyServiceMap = new HashMap<>();
            for (ServiceOfferingDto service : services) {
                BeautyService beautyService = beautyServiceService.getById(service.getBeautyServiceId());
                beautyServiceMap.put(service.getId().toString(), beautyService);
            }

            model.addAttribute("services", services);
            model.addAttribute("beautyServices", beautyServiceMap);
            model.addAttribute("masterId", master.getId());
        }
        return "profile";
    }

}
