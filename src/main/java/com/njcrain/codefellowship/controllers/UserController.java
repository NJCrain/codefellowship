package com.njcrain.codefellowship.controllers;

import com.njcrain.codefellowship.ApplicationUser;
import com.njcrain.codefellowship.ApplicationUserRepository;
import com.njcrain.codefellowship.Post;
import com.njcrain.codefellowship.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.Date;

@Controller
public class UserController {
    @Autowired
    private ApplicationUserRepository applicationUserRepo;
    @Autowired
    private PostRepository postRepo;

    @GetMapping("/users/{id}")
    public String show(Model m, @PathVariable long id) {
        ApplicationUser user = applicationUserRepo.findById(id).get();
        m.addAttribute("title", user.getUsername());
        m.addAttribute("user", user);
        return "user";
    }

    @GetMapping("/myprofile")
    public String showProfile(Principal p, Model m) {
        ApplicationUser user = (ApplicationUser) (((UsernamePasswordAuthenticationToken) p).getPrincipal());
        m.addAttribute("user", applicationUserRepo.findById(user.getId()).get());
        return "user";
    }

    @PostMapping("/users/{id}/posts")
    public RedirectView createPost(@PathVariable long id, @RequestParam String body) {
        Post newPost = new Post();
        newPost.setBody(body);
        newPost.setCreatedAt(new Date());
        newPost.setPostedBy(applicationUserRepo.findById(id).get());
        postRepo.save(newPost);

        return new RedirectView("/myprofile");
    }
}
