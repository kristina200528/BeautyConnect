package ru.itis.web.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itis.dto.MasterProfileDto;
import ru.itis.dto.ServiceOfferingDto;
import ru.itis.entity.BeautyService;
import ru.itis.entity.Master;
import ru.itis.service.BeautyServiceService;
import ru.itis.service.MasterService;
import ru.itis.service.ServiceOfferingService;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Controller
@RequestMapping("/masters")
public class MasterCatalogController {

    private final MasterService masterService;
    private final ServiceOfferingService serviceOfferingService;
    BeautyServiceService beautyServiceService;


    @GetMapping
    public String getAllMasters(Model model) {
        List<Master> masters = masterService.getAllMasters();
        model.addAttribute("masters", masters);
        return "master/catalog";
    }

    @GetMapping("{id}")
    public String getMasterProfile(@PathVariable Long id, Model model) {
        MasterProfileDto master = masterService.getMasterById(id);
        model.addAttribute("master", master);

        try {
            List<ServiceOfferingDto> services = serviceOfferingService.getServicesByMasterId(master.getId());

            Map<String, BeautyService> beautyServiceMap = new HashMap<>();
            for (ServiceOfferingDto service : services) {
                BeautyService beautyService = beautyServiceService.getById(service.getBeautyServiceId());
                beautyServiceMap.put(service.getId().toString(), beautyService);
            }

            model.addAttribute("services", services);
            model.addAttribute("beautyServices", beautyServiceMap);
            model.addAttribute("masterId", master.getId());
        } catch (Exception e) {
            model.addAttribute("services", Collections.emptyList());
        }
        return "master/profile";
    }

}
