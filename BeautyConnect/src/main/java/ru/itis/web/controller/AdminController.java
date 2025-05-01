package ru.itis.web.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itis.entity.User;
import ru.itis.service.UserService;

import java.util.List;


@Controller
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {
    private final UserService userService;

    @GetMapping
    public String getAllUsers(Model model) {
        List<User> users=userService.getAllUsers();
        model.addAttribute("users", users);
        return "admin";
    }

    @PostMapping("/block/{userId}")
    public String blockUser(@PathVariable Long userId){
        userService.blockUser(userId);
        return "redirect:/admin";
    }
    @PostMapping("/unblock/{userId}")
    public String unblockUser(@PathVariable Long userId){
        userService.unblockUser(userId);
        return "redirect:/admin";
    }


}
