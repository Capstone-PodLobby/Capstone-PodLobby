package com.podlobby.podlobby.controllers;

import com.podlobby.podlobby.model.Podcast;
import com.podlobby.podlobby.model.User;
import com.podlobby.podlobby.repositories.FollowRepository;
import com.podlobby.podlobby.services.UserService;
import com.podlobby.podlobby.util.IframeParser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class UserController {

    private final UserService userService;
    private final FollowRepository followDao;
    private final IframeParser iframeParser = new IframeParser();

    public UserController(UserService userService, FollowRepository followDao){
        this.userService = userService;
        this.followDao = followDao;
    }

    @GetMapping("/profile")
    public String profilePage(Model model){
//        get the current user
        User user = userService.getLoggedInUser();
        int followerCount = followDao.findAllByUserId(user.getId()).size();
        List<Podcast> createdPodcasts = user.getPodcasts();
        for(Podcast p : createdPodcasts){
            String parsedEmbedLink = iframeParser.parseIframe(p.getEmbedLink());
            p.setEmbedLink(parsedEmbedLink);
        }
        model.addAttribute("user", user);
        model.addAttribute("followerCount", followerCount);
        model.addAttribute("userPodcasts", createdPodcasts);
        return "users/profile";
    }

}
