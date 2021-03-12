package com.podlobby.podlobby.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("/profile")
    public String profilePage(){
//        get the current user
        return "users/profile";
    }

}
