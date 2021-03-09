package com.podlobby.podlobby.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PodcastPostController {


    @GetMapping("/posts/create")
        public String showPostCreate(){
            return"/posts/create";
        }

}
