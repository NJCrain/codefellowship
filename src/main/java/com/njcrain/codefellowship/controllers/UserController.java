package com.njcrain.codefellowship.controllers;

import com.njcrain.codefellowship.ApplicationUser;
import com.njcrain.codefellowship.ApplicationUserRepository;
import com.njcrain.codefellowship.Post;
import com.njcrain.codefellowship.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String show(Model m, @PathVariable long id, Principal p) {
        ApplicationUser user = applicationUserRepo.findById(id).get();
        ApplicationUser viewer = ApplicationUser.convertPrincipal(p, applicationUserRepo);
        m.addAttribute("viewer", viewer);
        m.addAttribute("title", user.getUsername());
        m.addAttribute("user", user);
        m.addAttribute("myProfile", false);

        return "user";
    }

    @GetMapping("/myprofile")
    public String showProfile(Principal p, Model m) {
        ApplicationUser user = ApplicationUser.convertPrincipal(p, applicationUserRepo);
        m.addAttribute("user", applicationUserRepo.findById(user.getId()).get());
        m.addAttribute("myProfile", true);
        System.out.println(user.followedUsers);
        return "user";
    }

    @PostMapping("/users/posts")
    public RedirectView createPost(Principal p, @RequestParam String body) {
        ApplicationUser user = ApplicationUser.convertPrincipal(p, applicationUserRepo);
        Post newPost = new Post();
        newPost.setBody(body);
        newPost.setCreatedAt(new Date());
        newPost.setPostedBy(applicationUserRepo.findById(user.getId()).get());
        postRepo.save(newPost);

        return new RedirectView("/myprofile");
    }

    @PostMapping("/users/{id}/follow")
    public RedirectView addFollow(Principal p, @PathVariable long id) {
        ApplicationUser user = ApplicationUser.convertPrincipal(p, applicationUserRepo);
        user.followedUsers.add(applicationUserRepo.findById(id).get());
        applicationUserRepo.save(user);
        return new RedirectView("/feed");
    }
}
