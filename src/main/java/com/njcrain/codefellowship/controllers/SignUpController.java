package com.njcrain.codefellowship.controllers;

import com.njcrain.codefellowship.ApplicationUser;
import com.njcrain.codefellowship.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class SignUpController {
    @Autowired
    private ApplicationUserRepository applicationUserRepo;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    //Shows the signup page
    @GetMapping("/signup")
    public String show() {
        return "signup";
    }


    //part of this comes from https://auth0.com/blog/implementing-jwt-authentication-on-spring-boot/
    //Omitting the @RequestBody came from https://stackoverflow.com/questions/33796218/content-type-application-x-www-form-urlencodedcharset-utf-8-not-supported-for
    //Takes the user information from the form, hashes the password, and saves the new user into the database
    @PostMapping("/signup")
    public RedirectView create(ApplicationUser user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return new RedirectView("/users/" + applicationUserRepo.save(user).getId());
    }
}
