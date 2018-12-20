package com.njcrain.codefellowship.controllers;

import com.njcrain.codefellowship.ApplicationUser;
import com.njcrain.codefellowship.ApplicationUserRepository;
import com.njcrain.codefellowship.Post;
import com.njcrain.codefellowship.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.GeneratedValue;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class FeedController {
    @Autowired
    ApplicationUserRepository appUserRepo;
    @Autowired
    PostRepository postRepo;

    @GetMapping("/feed")
    public String show(Principal p, Model m) {
        ApplicationUser user = ApplicationUser.convertPrincipal(p, appUserRepo);
        List<Post> posts = new ArrayList<>();
        for (ApplicationUser followee : user.followedUsers) {
            posts.addAll(followee.getPosts());
        }
        m.addAttribute("posts" ,posts);
        return "feed";
    }
}
