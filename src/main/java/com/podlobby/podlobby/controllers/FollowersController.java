package com.podlobby.podlobby.controllers;

import com.podlobby.podlobby.model.Podcast;
import com.podlobby.podlobby.model.User;
import com.podlobby.podlobby.repositories.FollowRepository;
import com.podlobby.podlobby.repositories.PodcastRepository;
import com.podlobby.podlobby.repositories.UserRepository;
import com.podlobby.podlobby.services.UserService;
import com.podlobby.podlobby.util.IframeParser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class FollowersController {

    private final UserService userService;
    private final FollowRepository followDao;
    private final UserRepository userDao;
    private final PodcastRepository podcastDao;

    private final IframeParser iframeParser = new IframeParser();


    public FollowersController(FollowRepository followDao, UserRepository userDao, PodcastRepository podcastDao, UserService userService){
        this.followDao = followDao;
        this.userDao = userDao;
        this.podcastDao = podcastDao;
        this.userService = userService;
    }

    //Will need to ad ID for specific followers on page
    @GetMapping("/followers")
    public String showFollowers(Model model){
        model.addAttribute("followList", followDao.findAllByUserId(1L));
        User user = userService.getLoggedInUser();
        model.addAttribute("user", user);
        return"followers";
    }


    @GetMapping("/followers/{id}")
    public String viewFollowersProfile(Model model, @PathVariable(name = "id") long id){
        User follower = userDao.findById(id).get();
        model.addAttribute("follower", follower);

        User currUser = userService.getLoggedInUser();
        model.addAttribute("user", currUser);

        List<Podcast> createdPodcasts = podcastDao.findAllByUserId(id);
        for(Podcast p : createdPodcasts){
            String parsedEmbedLink = iframeParser.parseIframe(p.getEmbedLink());
            p.setEmbedLink(parsedEmbedLink);
        }
        model.addAttribute("followerPodcasts", createdPodcasts);
        return "othersProfile";
    }

}
