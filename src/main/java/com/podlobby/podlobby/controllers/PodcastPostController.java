package com.podlobby.podlobby.controllers;

import com.podlobby.podlobby.model.Podcast;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class PodcastPostController {

    /////////////////
    //EDIT   OPTION//
    /////////////////
    //Will need to add ID to path for specific post
    @GetMapping("/podcasts/edit")
    public String viewEditPodcastForm() {
//        model.addAttribute("post", postsDao.getOne(id));
        return "podcasts/edit";
    }


    /////////////////
    //    DISPLAY  //
    /////////////////
    @GetMapping("/podcasts/create")
    public String showPodcastCreate(Model model){
        model.addAttribute("podcast", new Podcast());
        return"/podcasts/create";
    }



    @PostMapping("/podcasts/create")
    public String createPodcast(@ModelAttribute Podcast podcast){
        if(podcast.getTitle().isEmpty()){
            return "redirect:/podcasts/create?title";
        } else if(podcast.getDescription().isEmpty()){
            return "redirect:/podcasts/create?description";
        } else if(podcast.getEmbedLink().isEmpty()){
            return "redirect:/podcasts/create?embed";
        } else if(podcast.getCategories().size() < 1){
            return "redirect:/podcasts/create?categories";
        }

        System.out.println(podcast.getTitle());
        System.out.println(podcast.getDescription());
        System.out.println(podcast.getEmbedLink());
        System.out.println(podcast.getImage());
        System.out.println(podcast.getCategories());
        return "users/profile";
    }



}
