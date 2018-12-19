package com.njcrain.codefellowship.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String show(Model m) {
        m.addAttribute("title", "Login");
        return "login";
    }
}
