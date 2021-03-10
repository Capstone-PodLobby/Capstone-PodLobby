package com.podlobby.podlobby.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class PodcastPostController {

    /////////////////
    //    DISPLAY  //
    /////////////////
    @GetMapping("/podcasts/create")
        public String showPodcastCreate(){
            return"/podcasts/create";
        }


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
    //DELETE OPTION//
    /////////////////
//    @PostMapping("/posts/{id}/delete")
//    public String deletePodcast(@PathVariable long id){
//        System.out.println("Deleting podcast...");
//        podcastDao.deleteById(id);
//        return "redirect:/posts";
//    }

}
