package com.podlobby.podlobby.controllers;

import com.podlobby.podlobby.model.Podcast;
import com.podlobby.podlobby.repositories.PodcastRepository;
import com.podlobby.podlobby.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class FeedController {
    private final PodcastRepository podcastDao;
    private final UserRepository usersDao;

    public FeedController(PodcastRepository podcastDao, UserRepository usersDao ){
        this.podcastDao = podcastDao;
        this.usersDao = usersDao;
    }

    @GetMapping("/feeds/global")
    public String showGlobalFeed(Model model){
        List<Podcast> podcasts = podcastDao.findAll();
        model.addAttribute("podcasts", podcasts);
        return"/feeds/global-feed";
    }


    @GetMapping("/feeds/filtered")
    public String showFilteredFeed(){
        return"/feeds/filtered-feed";
    }



}
