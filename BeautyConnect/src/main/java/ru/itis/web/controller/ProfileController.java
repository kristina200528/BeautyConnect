package ru.itis.web.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itis.dto.ServiceOfferingDto;
import ru.itis.entity.BeautyService;
import ru.itis.entity.Master;
import ru.itis.entity.User;
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
        Optional<User> user = userService.findByUsername(principal.getName());

        if (user.isPresent()) {
            model.addAttribute("user", user.get());
            Optional<Master> master=masterService.findMasterByUserId(user.get().getId());
            master.ifPresent(value -> model.addAttribute("master", value));

            try {
                List<ServiceOfferingDto> services = serviceOfferingService.getServicesByMasterId(master.get().getId());

                Map<String, BeautyService> beautyServiceMap = new HashMap<>();
                for (ServiceOfferingDto service : services) {
                    BeautyService beautyService = beautyServiceService.getById(service.getBeautyServiceId());
                    beautyServiceMap.put(service.getId().toString(), beautyService);
                }

                model.addAttribute("services", services);
                model.addAttribute("beautyServices", beautyServiceMap);
                model.addAttribute("masterId", master.get().getId());
            } catch (Exception e) {
                model.addAttribute("services", Collections.emptyList());
            }
        }

        return "profile";
    }

}
