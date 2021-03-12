package com.podlobby.podlobby.controllers;

import com.podlobby.podlobby.model.Podcast;
import com.podlobby.podlobby.repositories.CategoryRepository;
import com.podlobby.podlobby.repositories.FollowRepository;
import com.podlobby.podlobby.repositories.PodcastRepository;
import com.podlobby.podlobby.repositories.UserRepository;
import com.podlobby.podlobby.util.IframeParser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class FeedController {
    private final PodcastRepository podcastDao;
    private final UserRepository usersDao;
    private final IframeParser iframeParser = new IframeParser();
    private final FollowRepository followDao;

    public FeedController(PodcastRepository podcastDao, UserRepository usersDao, FollowRepository followDao ){
        this.podcastDao = podcastDao;
        this.usersDao = usersDao;
        this.followDao = followDao;
    }

    @GetMapping("/feeds/global")
    public String showGlobalFeed(Model model){
        List<Podcast> podcasts = podcastDao.findAll();
        for(Podcast p : podcasts){
            String parsedEmbed = iframeParser.parseIframe(p.getEmbedLink());
            p.setEmbedLink(parsedEmbed);
        }
        model.addAttribute("podcasts", podcasts);
        return"/feeds/global-feed";
    }


    @GetMapping("/feeds/filtered")
    public String showFilteredFeed(Model model){
        List<Podcast> allPodcast = podcastDao.findAll();
        List<Podcast> selectPodcast = new ArrayList<>();
//        for(Podcast podcast : allPodcast) {
//            if(podcast.getId() ==)
//        }
        return"/feeds/filtered-feed";
    }



}
