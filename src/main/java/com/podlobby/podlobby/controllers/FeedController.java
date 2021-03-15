package com.podlobby.podlobby.controllers;

import com.podlobby.podlobby.model.Podcast;
import com.podlobby.podlobby.model.User;
import com.podlobby.podlobby.repositories.CategoryRepository;
import com.podlobby.podlobby.repositories.FollowRepository;
import com.podlobby.podlobby.repositories.PodcastRepository;
import com.podlobby.podlobby.repositories.UserRepository;
import com.podlobby.podlobby.services.UserService;
import com.podlobby.podlobby.util.IframeParser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class FeedController {

    private final UserService userService;
    private final PodcastRepository podcastDao;
    private final UserRepository usersDao;
    private final FollowRepository followDao;

    public FeedController(PodcastRepository podcastDao, UserRepository usersDao, FollowRepository followDao, UserService userService){
        this.podcastDao = podcastDao;
        this.usersDao = usersDao;
        this.followDao = followDao;
        this.userService = userService;
    }

    @GetMapping("/feeds/global")
    public String showGlobalFeed(Model model, HttpServletRequest request){
        List<Podcast> podcasts = podcastDao.findAll();
        model.addAttribute("podcasts", podcasts);
        model.addAttribute("currentUrl", request.getRequestURI());
        return"/feeds/global-feed";
    }


    @GetMapping("/feeds/filtered")
    public String showFilteredFeed(Model model, HttpServletRequest request){
        model.addAttribute("currentUrl", request.getRequestURI());
        List<Podcast> allPodcast = podcastDao.findAll();
        List<Podcast> selectPodcast = new ArrayList<>();
//        for(Podcast podcast : allPodcast) {
//            if(podcast.getId() == followDao)
//        }
        return"/feeds/filtered-feed";
    }



}
