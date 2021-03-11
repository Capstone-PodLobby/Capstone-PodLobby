package com.podlobby.podlobby.controllers;

import com.podlobby.podlobby.repositories.PodcastRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FeedController {
    private final PodcastRepository podcastDao;

    public FeedController(PodcastRepository podcastDao){
        this.podcastDao = podcastDao;
    }


    @GetMapping("/feeds/global")
    public String showGlobalFeed(Model model){
        model.addAttribute("podcastsList", podcastDao.findAll());
        return"/feeds/global-feed";
    }




    @GetMapping("/feeds/filtered")
    public String showFilteredFeed(){
        return"/feeds/filtered-feed";
    }

}
