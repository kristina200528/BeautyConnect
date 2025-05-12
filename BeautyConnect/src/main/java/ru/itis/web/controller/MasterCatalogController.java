package ru.itis.web.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itis.dto.MasterProfileDto;
import ru.itis.entity.Master;
import ru.itis.service.MasterService;

import java.util.List;

@AllArgsConstructor
@Controller
@RequestMapping("/masters")
public class MasterCatalogController {
    private final MasterService masterService;

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
        return "master/profile";
    }

}
