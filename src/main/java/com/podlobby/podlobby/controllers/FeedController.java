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
//        loop through podcasts and parse out the iframe
//        might need to create a new list and add podcast to a new list
        model.addAttribute("podcasts", podcasts);
        return"/feeds/global-feed";
    }




    @GetMapping("/feeds/filtered")
    public String showFilteredFeed(){
        return"/feeds/filtered-feed";
    }


//    public String parseEmbedLink(Podcast podcast){
//
//            String embedLink = podcast.getEmbedLink().substring(13,);
//
//        // take the current Embed link that is passed in and manipulate it and return it
//        //String parsedVersion =  currentEmbedLink.substring() or something
//        //return parsedVersion;
//    }



    public static void main(String[] args){

    }

}
