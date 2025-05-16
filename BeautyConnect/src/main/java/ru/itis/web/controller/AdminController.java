package ru.itis.web.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itis.entity.User;
import ru.itis.service.UserService;

import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {
    private final UserService userService;

    @GetMapping
    public String getAllUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "admin";
    }

    @PostMapping("/block/{userId}")
    @ResponseBody
    public Map<String, String> blockUser(@PathVariable Long userId) {
        userService.blockUser(userId);
        return ResponseEntity.ok(Map.of("status", "BLOCKED")).getBody();
    }

    @PostMapping("/unblock/{userId}")
    @ResponseBody
    public Map<String, String> unblockUser(@PathVariable Long userId) {
        userService.unblockUser(userId);
        return Map.of("status","ACTIVE");
    }


}
