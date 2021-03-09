package com.podlobby.podlobby.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FollowersController {

    //Will need to ad ID for specific followers on page
    @GetMapping("/followers")
    public String showFollowers(){
        return"followers";
    }

}
