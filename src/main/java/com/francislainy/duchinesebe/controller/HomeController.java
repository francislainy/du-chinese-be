package com.francislainy.duchinesebe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @GetMapping({ "", "/"})
    public String login(Model model) {
        return "login";
    }

    @GetMapping({"admin", "admin/"})
    public String admin(Model model) {
        return "admin";
    }
}