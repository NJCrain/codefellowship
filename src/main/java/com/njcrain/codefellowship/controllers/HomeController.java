package com.njcrain.codefellowship.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String show(Model m) {
        m.addAttribute("title", "CodeFellowShip");
        return "index";
    }
}
