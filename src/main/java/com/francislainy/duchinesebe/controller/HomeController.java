package com.francislainy.duchinesebe.controller;

import com.francislainy.duchinesebe.model.User;
import com.francislainy.duchinesebe.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final UserServiceImpl userService;

    @GetMapping({"", "/"})
    public String login(Model model) {
        return "loginPage";
    }

    @GetMapping({"admin", "admin/"})
    public String getAdminDashboard(Model model) {
        List<User> users = userService.getUsers();
        model.addAttribute("users", users);
        return "adminPage";
    }

    @GetMapping({"user/{userId}", "user/{userId}"})
    public String getUserDetailPage(Model model, @PathVariable UUID userId) {
        User user = userService.getUser(userId);
        model.addAttribute("user", user);
        return "userDetailPage";
    }
}