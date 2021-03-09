package com.podlobby.podlobby.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class User {

    @GetMapping("/profile")
    public String profilePage(){
        return "users/profile";
    }

}
