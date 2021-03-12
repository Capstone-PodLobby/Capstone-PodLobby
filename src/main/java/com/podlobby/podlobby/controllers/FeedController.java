package com.podlobby.podlobby.controllers;

import com.podlobby.podlobby.model.Podcast;
import com.podlobby.podlobby.model.User;
import com.podlobby.podlobby.repositories.PodcastRepository;
import com.podlobby.podlobby.repositories.UserRepository;
import com.podlobby.podlobby.services.UserService;
import com.podlobby.podlobby.util.IframeParser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class FeedController {

    private final UserService userService;
    private final PodcastRepository podcastDao;
    private final UserRepository usersDao;
    private final IframeParser iframeParser = new IframeParser();

    public FeedController(PodcastRepository podcastDao, UserRepository usersDao, UserService userService){
        this.podcastDao = podcastDao;
        this.usersDao = usersDao;
        this.userService = userService;
    }

    @GetMapping("/feeds/global")
    public String showGlobalFeed(Model model){
        List<Podcast> podcasts = podcastDao.findAll();
        for(Podcast p : podcasts){
            String parsedEmbed = iframeParser.parseIframe(p.getEmbedLink());
            p.setEmbedLink(parsedEmbed);
        }
        model.addAttribute("podcasts", podcasts);
        User user = userService.getLoggedInUser();
        model.addAttribute("user", user);
        return"/feeds/global-feed";
    }


    @GetMapping("/feeds/filtered")
    public String showFilteredFeed(Model model){
        User user = userService.getLoggedInUser();
        model.addAttribute("user", user);
        return"/feeds/filtered-feed";
    }



}
