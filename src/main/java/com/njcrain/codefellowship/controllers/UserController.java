package com.njcrain.codefellowship.controllers;

import com.njcrain.codefellowship.ApplicationUser;
import com.njcrain.codefellowship.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class UserController {
    @Autowired
    private ApplicationUserRepository applicationUserRepo;

    @GetMapping("/users/{id}")
    public String show(Model m, @PathVariable long id) {
        ApplicationUser user = applicationUserRepo.findById(id).get();
        m.addAttribute("title", user.getUsername());
        m.addAttribute("user", user);
        return "user";
    }
}