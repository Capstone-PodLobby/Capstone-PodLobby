package com.podlobby.podlobby.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FeedController {

    @GetMapping("/feeds/global")
    public String showGlobalFeed(){
        return"/feeds/global-feed";
    }

    @GetMapping("/feeds/filtered")
    public String showFilteredFeed(){
        return"/feeds/filtered-feed";
    }

}
